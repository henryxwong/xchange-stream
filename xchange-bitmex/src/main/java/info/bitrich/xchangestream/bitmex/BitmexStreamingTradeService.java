package info.bitrich.xchangestream.bitmex;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static info.bitrich.xchangestream.bitmex.BitmexStreamingMarketDataService.getBitmexSymbol;


/**
 * Created by Declan
 */
public class BitmexStreamingTradeService {

    private static final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    private final BitmexStreamingService streamingService;

    private final Map<String, ObjectNode> orderMap = new HashMap<>();

    private final Map<String, ObjectNode> positionMap = new HashMap<>();

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

    public Observable<BitmexPosition> getPosition(CurrencyPair currencyPair, Object... args) {
        String instrument = getBitmexSymbol(currencyPair);
        String channelName = String.format("position:%s", instrument);

        return streamingService.subscribeBitmexChannel(channelName).flatMapIterable(s -> {
            String action = s.getAction();
            JsonNode data = s.getData();

            List<BitmexPosition> positionList = new ArrayList<>(data.size());

            if ("update".equals(action)) {
                for (JsonNode node : data) {
                    // TODO with account?
                    String symbol = node.get("symbol").textValue();

                    ObjectNode positionNode = positionMap.get(symbol);
                    if (positionNode != null) {
                        positionNode.setAll((ObjectNode) node);
                        BitmexPosition position = mapper.treeToValue(positionNode, BitmexPosition.class);
                        if (!position.getOpen()) {
                            positionMap.remove(symbol);
                        }
                        positionList.add(position);
                    }
                }
            } else if ("insert".equals(action)) {
                for (JsonNode node : data) {
                    String symbol = node.get("symbol").textValue();

                    BitmexPosition position = mapper.treeToValue(node, BitmexPosition.class);
                    if (!position.getOpen()) {
                        positionMap.put(symbol, (ObjectNode) node);
                    }
                    positionList.add(position);
                }
            }

            return positionList;
        });
    }
}
