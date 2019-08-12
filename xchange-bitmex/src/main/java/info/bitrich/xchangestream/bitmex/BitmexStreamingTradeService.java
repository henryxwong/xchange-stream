package info.bitrich.xchangestream.bitmex;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static info.bitrich.xchangestream.bitmex.BitmexStreamingMarketDataService.getBitmexSymbol;


/**
 * Created by Declan
 */
public class BitmexStreamingTradeService {

    private static final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    private final BitmexStreamingService streamingService;

    private final Map<String, ObjectNode> orderMap = new HashMap<>();

    private final Map<String, ObjectNode> positionMap = new HashMap<>();

    private final Map<String, BitmexPosition> inactivePositionMap = new HashMap<>();

    public BitmexStreamingTradeService(BitmexStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    public Observable<Order> getOrders(CurrencyPair currencyPair, Object... args) {
        String instrument = getBitmexSymbol(currencyPair);
        String channelName = String.format("order:%s", instrument);

        return streamingService.subscribeBitmexChannel(channelName).flatMapIterable(s -> {
            String action = s.getAction();
            JsonNode data = s.getData();

            List<Order> orderList = new ArrayList<>(data.size());

            if ("update".equals(action)) {
                for (JsonNode node : data) {
                    String orderId = node.get("orderID").textValue();

                    ObjectNode orderNode = orderMap.get(orderId);
                    if (orderNode != null) {
                        orderNode.setAll((ObjectNode) node);
                        Order order = BitmexAdapters.adaptOrder(mapper.treeToValue(orderNode, BitmexPrivateOrder.class));
                        if (order.getStatus().isFinal()) {
                            orderMap.remove(orderId);
                        }
                        orderList.add(order);
                    }
                }
            } else if ("insert".equals(action)) {
                for (JsonNode node : data) {
                    String orderId = node.get("orderID").textValue();

                    Order order = BitmexAdapters.adaptOrder(mapper.treeToValue(node, BitmexPrivateOrder.class));
                    if (order.getStatus().isOpen()) {
                        orderMap.put(orderId, (ObjectNode) node);
                    }
                    orderList.add(order);
                }
            }

            return orderList;
        });
    }

    private <T, K> Observable<T> getData(String channel, CurrencyPair currencyPair, Function<ObjectNode, K> keyExtractor, Map<K, ObjectNode> dataMap, Function<ObjectNode, T> dataMapper, Predicate<T> inactiveTester, Map<K, T> inactiveDataMap) {
        String channelName = String.format(channel + ":%s", getBitmexSymbol(currencyPair));

        return streamingService.subscribeBitmexChannel(channelName).flatMapIterable(s -> {
            String action = s.getAction();
            JsonNode data = s.getData();

            List<T> itemList = new ArrayList<>(data.size());
            if ("update".equals(action)) {
                for (JsonNode node : data) {
                    ObjectNode objectNode = (ObjectNode) node;
                    K key = keyExtractor.apply(objectNode);

                    ObjectNode dataNode = dataMap.get(key);
                    if (dataNode != null) {
                        dataNode.setAll(objectNode);
                        T mappedData = dataMapper.apply(dataNode);
                        if (inactiveTester.test(mappedData)) {
                            dataMap.remove(key);
                            inactiveDataMap.put(key, mappedData);
                        }
                        itemList.add(mappedData);
                    }
                }
            } else if ("insert".equals(action)) {
                for (JsonNode node : data) {
                    ObjectNode objectNode = (ObjectNode) node;
                    K key = keyExtractor.apply(objectNode);

                    T mappedData = dataMapper.apply(objectNode);
                    if (inactiveTester.test(mappedData)) {
                        inactiveDataMap.put(key, mappedData);
                    } else {
                        dataMap.put(key, objectNode);
                    }
                    itemList.add(mappedData);
                }
            }

            return itemList;
        });
    }

    public Observable<BitmexPosition> getPosition(CurrencyPair currencyPair) {
        return getData("position",
                currencyPair,
                node -> node.get("account").textValue() + "/" + node.get("symbol").textValue(),
                positionMap,
                node -> mapper.treeToValue(node, BitmexPosition.class),
                position -> !position.getOpen(),
                inactivePositionMap);
    }
}
