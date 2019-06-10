package info.bitrich.xchangestream.hbdm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HbdmPrivateStreamingService extends HbdmStreamingService {

    private final static Logger logger = LoggerFactory.getLogger(HbdmPrivateStreamingService.class);

    public HbdmPrivateStreamingService(String apiUrl) {
        super(apiUrl);
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        Map<String, String> msg = new HashMap<>();
        msg.put("op", "sub");
        msg.put("topic", channelName);
        return mapper.writeValueAsString(msg);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        Map<String, String> msg = new HashMap<>();
        msg.put("op", "unsub");
        msg.put("topic", channelName);
        return mapper.writeValueAsString(msg);
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if ("ping".equals(message.get("op").asText())) {
            Map<String, Object> pong = new HashMap<>();
            pong.put("op", "pong");
            pong.put("ts", message.get("ts").asLong());
            try {
                sendMessage(mapper.writeValueAsString(pong));
            } catch (JsonProcessingException e) {
                logger.error("Convert pong message to json failed", e);
            }
            return;
        }
        super.handleMessage(message);
    }

}
