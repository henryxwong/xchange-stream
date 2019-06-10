package info.bitrich.xchangestream.hbdm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HbdmStreamingMarketService extends HbdmStreamingService {

    private final static Logger logger = LoggerFactory.getLogger(HbdmStreamingMarketService.class);

    public HbdmStreamingMarketService(String apiUrl) {
        super(apiUrl);
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        Map<String, String> msg = new HashMap<>();
        msg.put("sub", channelName);
        return mapper.writeValueAsString(msg);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        return "";
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if (message.has("ping")) {
            Map<String, Long> pong = new HashMap<>();
            pong.put("pong", message.get("ping").asLong());
            try {
                sendMessage(mapper.writeValueAsString(pong));
            } catch (JsonProcessingException e) {
                logger.error("Convert pong message to json failed", e);
            }
            return;
        }
        if (message.has("status")) {
            logger.info("Response message: {}", message);
            return;
        }
        super.handleMessage(message);
    }

}
