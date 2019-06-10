package info.bitrich.xchangestream.hbdm;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.hbdm.HbdmExchange;

public class HbdmStreamingExchange extends HbdmExchange implements StreamingExchange {

    @Override
    public Completable connect(ProductSubscription... args) {
        return null;
    }

    @Override
    public Completable disconnect() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return null;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {

    }

}
