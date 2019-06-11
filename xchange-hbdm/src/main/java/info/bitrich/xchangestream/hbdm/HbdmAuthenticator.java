package info.bitrich.xchangestream.hbdm;

import org.knowm.xchange.service.BaseParamsDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class HbdmAuthenticator {

    public static Map<String, Object> authenticateMessage(String apiKey, String apiSecret, String host, String path) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String httpMethod = "GET";
        Map<String, Object> params = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp = sdf.format(new Date());
        params.put("AccessKeyId", apiKey);
        params.put("SignatureMethod", BaseParamsDigest.HMAC_SHA_256);
        params.put("SignatureVersion", 2);
        params.put("Timestamp", timestamp);
        String query = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + encodeValue(e.getValue().toString()))
                .collect(Collectors.joining("&"));
        String toSign = String.format("%s\n%s\n%s\n%s", httpMethod, host, path, query);
        final SecretKey secretKey = new SecretKeySpec(apiSecret.getBytes(), BaseParamsDigest.HMAC_SHA_256);
        Mac mac = Mac.getInstance(BaseParamsDigest.HMAC_SHA_256);
        mac.init(secretKey);
        String signature = Base64.getEncoder().encodeToString(mac.doFinal(toSign.getBytes())).trim();
        params.put("Signature", signature);
        params.put("op", "auth");
        params.put("type", "api");
        return params;
    }

    private static String encodeValue(String value) {
        String ret;
        try {
            ret = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return ret;
    }

}
