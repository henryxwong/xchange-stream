package info.bitrich.xchangestream.hbdm;

import org.junit.Ignore;
import org.junit.Test;

public class HbdmStreamingServiceTest {

    @Test
    @Ignore("run mannually")
    public void testPublicStreamingService() throws InterruptedException {
        HbdmStreamingService streamingService = new HbdmPublicStreamingService("wss://www.hbdm.com/ws");
        streamingService.connect().blockingAwait();
        streamingService.subscribeChannel("market.BTC_CQ.kline.1min").subscribe(System.out::println);
        for (int i=0; i<100; i++) {
            Thread.sleep(1000);
        }
    }

}
