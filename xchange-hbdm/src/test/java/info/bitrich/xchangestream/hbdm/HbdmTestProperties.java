package info.bitrich.xchangestream.hbdm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class HbdmTestProperties {

    private final String FILENAME = "hbdm-test.properties";
    private final String API = "api-key";
    private final String SECRET = "secret-key";

    private String proxyHost;
    private Integer proxyPort;

    private String apiKey;
    private String secretKey;
    private boolean valid;

    public HbdmTestProperties() {

        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(FILENAME)) {
            properties.load(input);
            apiKey = properties.getProperty(API);
            secretKey = properties.getProperty(SECRET);
            valid = true;
            if (apiKey == null || apiKey.isEmpty()) {
                valid = false;
            }
            if (secretKey == null || secretKey.isEmpty()) {
                valid = false;
            }
        } catch (IOException e) {
            valid = false;
            apiKey = null;
            secretKey = null;
        }
    }

    public boolean isValid() {
        return valid;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }
}
