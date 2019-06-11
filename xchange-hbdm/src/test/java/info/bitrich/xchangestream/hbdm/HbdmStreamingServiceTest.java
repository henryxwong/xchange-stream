package info.bitrich.xchangestream.hbdm;

import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;

public class HbdmStreamingServiceTest {

    private final HbdmTestProperties hbdmTestProperties = new HbdmTestProperties();

    @Test
    @Ignore("run mannually")
    public void testMarketStreamingService() throws InterruptedException {
        HbdmStreamingService streamingService = new HbdmStreamingMarketService("wss://www.hbdm.com/ws");
        streamingService.connect().blockingAwait();
        streamingService.subscribeChannel("market.BTC_CQ.kline.1min").subscribe(System.out::println);
        for (int i=0; i<100; i++) {
            Thread.sleep(1000);
        }
    }

    @Test
    @Ignore("run mannually")
    public void testTradingStreamingService() throws InterruptedException {
        assert hbdmTestProperties.isValid();
        HbdmStreamingTradeService streamingService = new HbdmStreamingTradeService("wss://api.hbdm.com/notification");
        ExchangeSpecification exchangeSpecification = (new HbdmStreamingExchange()).getDefaultExchangeSpecification();
        exchangeSpecification.setApiKey(hbdmTestProperties.getApiKey());
        exchangeSpecification.setSecretKey(hbdmTestProperties.getSecretKey());
        streamingService.setExchangeSpecification(exchangeSpecification);
        streamingService.connect().blockingAwait();
        streamingService.subscribeChannel("orders.btc").subscribe(System.out::println);
        for (;;) {
            Thread.sleep(1000);
        }
    }

}
