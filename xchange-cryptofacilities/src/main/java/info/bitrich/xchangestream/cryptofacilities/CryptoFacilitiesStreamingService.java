package info.bitrich.xchangestream.cryptofacilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesBalance;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesOrder;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesOrderBook;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesOrderBookDelta;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesPosition;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTicker;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTickerLite;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTrade;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Observable;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CryptoFacilitiesStreamingService extends JsonNettyStreamingService {

    private final String apiKey;

    private final String secretKey;

    private String challenge;

    private String signedChallenge;

    private CountDownLatch loginLatch;

    public CryptoFacilitiesStreamingService(String uri, String apiKey, String secretKey) {
        super(uri);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public static String signChallenge(String challenge, String secretKey) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(challenge.getBytes());
            byte[] secretKeyBase64 = Base64.getDecoder().decode(secretKey.getBytes());
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBase64, "HmacSHA512");
            Mac mac512 = Mac.getInstance("HmacSHA512");
            mac512.init(secretKeySpec);
            mac512.update(sha256.digest());
            return Base64.getEncoder().encodeToString(mac512.doFinal()).trim();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void resubscribeChannels() {
        // do nothing
    }

    @Override
    protected void handleMessage(JsonNode message) {
        JsonNode eventNode = message.get("event");
        if (eventNode == null) {
            super.handleMessage(message);
        } else {
            switch (eventNode.asText()) {
                case "info":
                    if (apiKey != null) {
                        loginLatch = new CountDownLatch(1);
                        ObjectNode jsonNode = objectMapper.createObjectNode();
                        jsonNode.put("event", "challenge");
                        jsonNode.put("api_key", apiKey);
                        sendObjectMessage(jsonNode);
                    }
                    break;

                case "challenge":
                    challenge = message.get("message").asText();
                    signedChallenge = signChallenge(challenge, secretKey);
                    loginLatch.countDown();
                    super.resubscribeChannels();
                    break;
            }
        }
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) {
        String channelName = message.get("feed").asText();
        switch (channelName) {
            case "open_orders_verbose_snapshot":
                channelName = "open_orders_verbose";
                break;
            case "book_snapshot":
                channelName = "book";
                break;
            case "trade_snapshot":
                channelName = "trade";
                break;
        }
        JsonNode productIdNode = message.get("product_id");
        if (productIdNode != null) {
            channelName += ":" + productIdNode.asText();
        }

        return channelName;
    }

    private ObjectNode createSubscribeMessage(String event, String channelName) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("event", event);
        jsonObject.put("feed", channelName);
        return jsonObject;
    }

    private ObjectNode createSubscribeProductMessage(String event, String channelName, String productId) {
        ObjectNode jsonObject = createSubscribeMessage(event, channelName);
        jsonObject.putArray("product_ids").add(productId);
        return jsonObject;
    }

    private ObjectNode createPrivateSubscribeMessage(String event, String channelName) throws IOException {
        if (loginLatch != null) {
            try {
                loginLatch.await();
            } catch (InterruptedException e) {
                throw new IOException(e);
            }
        }

        ObjectNode jsonObject = createSubscribeMessage(event, channelName);
        jsonObject.put("api_key", apiKey);
        jsonObject.put("original_challenge", challenge);
        jsonObject.put("signed_challenge", signedChallenge);
        return jsonObject;
    }

    private String getMessage(String event, String channelName) throws IOException {
        ObjectNode jsonObject;
        String[] parts = channelName.split(":");
        if (parts.length > 1) {
            jsonObject = createSubscribeProductMessage(event, parts[0], parts[1]);
        } else if (apiKey == null) {
            jsonObject = createSubscribeMessage(event, channelName);
        } else {
            jsonObject = createPrivateSubscribeMessage(event, channelName);
        }

        return objectMapper.writeValueAsString(jsonObject);
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        return getMessage("subscribe", channelName);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        return getMessage("unsubscribe", channelName);
    }

    private <T> Observable<? extends T> handleArrayData(String dataNodeName, Class<T> valueType, JsonNode jsonNode) throws com.fasterxml.jackson.core.JsonProcessingException {
        ArrayNode dataNode = (ArrayNode) jsonNode.get(dataNodeName);
        List<T> dataList = new LinkedList<>();
        for (int i = 0; i < dataNode.size(); i++) {
            T data = objectMapper.treeToValue(dataNode.get(i), valueType);
            dataList.add(0, data);
        }

        return Observable.fromIterable(dataList);
    }

    public Observable<CryptoFacilitiesTrade> subscribeTradeChannel(String productId) {
        return subscribeChannel("trade" + ":" + productId).flatMap(jsonNode -> {
            if (jsonNode.get("feed").asText().equals("trade_snapshot")) {
                return handleArrayData("trades", CryptoFacilitiesTrade.class, jsonNode);
            } else {
                return Observable.just(objectMapper.treeToValue(jsonNode, CryptoFacilitiesTrade.class));
            }
        });
    }

    public Observable<CryptoFacilitiesTicker> subscribeTickerChannel(String productId) {
        return subscribeChannel("ticker" + ":" + productId).map(jsonNode -> objectMapper.treeToValue(jsonNode, CryptoFacilitiesTicker.class));
    }

    public Observable<CryptoFacilitiesTickerLite> subscribeTickerLiteChannel(String productId) {
        return subscribeChannel("ticker_lite" + ":" + productId).map(jsonNode -> objectMapper.treeToValue(jsonNode, CryptoFacilitiesTickerLite.class));
    }

    public Observable<CryptoFacilitiesOrderBook> subscribeOrderBookChannel(String productId) {
        CryptoFacilitiesOrderBook[] ref = new CryptoFacilitiesOrderBook[1];
        return subscribeChannel("book" + ":" + productId).map(jsonNode -> {
            if (jsonNode.get("feed").asText().equals("book_snapshot")) {
                ref[0] = objectMapper.treeToValue(jsonNode, CryptoFacilitiesOrderBook.class);
            } else if (ref[0] != null) {
                CryptoFacilitiesOrderBookDelta delta = objectMapper.treeToValue(jsonNode, CryptoFacilitiesOrderBookDelta.class);
                ref[0].updateDelta(delta);
            }

            return ref[0];
        });
    }

    public Observable<CryptoFacilitiesBalance> subscribeBalanceChannel() {
        return subscribeChannel("account_balances_and_margins").flatMap(jsonNode -> handleArrayData("margin_accounts", CryptoFacilitiesBalance.class, jsonNode));
    }

    public Observable<CryptoFacilitiesPosition> subscribePositionChannel() {
        return subscribeChannel("open_positions").flatMap(jsonNode -> handleArrayData("positions", CryptoFacilitiesPosition.class, jsonNode));
    }

    public Observable<CryptoFacilitiesOrder> subscribeOrderChannel() {
        Map<String, CryptoFacilitiesOrder> orderMap = new HashMap<>();
        return subscribeChannel("open_orders_verbose").flatMap(jsonNode -> {
            List<CryptoFacilitiesOrder> orderList = new ArrayList<>();
            if (jsonNode.get("feed").asText().equals("open_orders_verbose_snapshot")) {
                ArrayNode dataNode = (ArrayNode) jsonNode.get("orders");
                for (int i = 0; i < dataNode.size(); i++) {
                    CryptoFacilitiesOrder order = objectMapper.treeToValue(dataNode.get(i), CryptoFacilitiesOrder.class);
                    orderMap.put(order.getOrderId(), order);

                    orderList.add(order);
                }
            } else {
                JsonNode orderNode = jsonNode.get("order");
                String orderId = orderNode == null ? jsonNode.get("order_id").asText() : orderNode.get("order_id").asText();
                boolean isCancel = jsonNode.get("is_cancel").asBoolean();
                CryptoFacilitiesOrder order;
                if (isCancel) {
                    order = orderMap.remove(orderId);
                } else {
                    order = orderMap.get(orderId);
                    if (order == null) {
                        order = objectMapper.treeToValue(orderNode, CryptoFacilitiesOrder.class);
                        orderMap.put(order.getOrderId(), order);
                    } else {
                        order = objectMapper.readerForUpdating(order).readValue(orderNode);
                    }
                }
                order.setCancel(isCancel);
                order.setReason(jsonNode.get("reason").asText());

                orderList.add(order);
            }

            return Observable.fromIterable(orderList);
        });
    }
}
