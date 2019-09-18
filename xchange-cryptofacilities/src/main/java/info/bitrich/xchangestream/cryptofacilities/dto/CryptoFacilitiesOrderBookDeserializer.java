package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.TreeMap;

public class CryptoFacilitiesOrderBookDeserializer extends JsonDeserializer<TreeMap<BigDecimal, BigDecimal>> {

    @Override
    public TreeMap<BigDecimal, BigDecimal> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeMap<BigDecimal, BigDecimal> map = "asks".equals(p.getCurrentName()) ? new TreeMap<>() : new TreeMap<>(Collections.reverseOrder());
        ArrayNode jsonNode = p.readValueAsTree();
        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode entryNode = jsonNode.get(i);
            double price = entryNode.get("price").asDouble();
            double qty = entryNode.get("qty").asDouble();
            map.put(new BigDecimal(price), new BigDecimal(qty));
        }

        return map;
    }
}
