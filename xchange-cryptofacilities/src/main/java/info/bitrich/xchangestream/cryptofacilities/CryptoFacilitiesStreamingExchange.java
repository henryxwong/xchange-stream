package info.bitrich.xchangestream.cryptofacilities;

import info.bitrich.xchangestream.core.*;
import io.reactivex.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptofacilities.CryptoFacilitiesExchange;

public class CryptoFacilitiesStreamingExchange extends CryptoFacilitiesExchange implements StreamingExchange {

    private static final String API_URI = "wss://www.cryptofacilities.com/ws/v1";

    private static final String TESTNET_API_URI = "wss://conformance.cryptofacilities.com/ws/v1";

    private CryptoFacilitiesStreamingService streamingService;

    private CryptoFacilitiesStreamingAccountService streamingAccountService;

    private CryptoFacilitiesStreamingTradeService streamingTradeService;

    private CryptoFacilitiesStreamingMarketDataService streamingMarketDataService;

    @Override
    protected void initServices() {
        super.initServices();
        streamingService = createStreamingService();
        streamingAccountService = new CryptoFacilitiesStreamingAccountService(streamingService);
        streamingTradeService = new CryptoFacilitiesStreamingTradeService(streamingService);
        streamingMarketDataService = new CryptoFacilitiesStreamingMarketDataService(streamingService);
    }

    private CryptoFacilitiesStreamingService createStreamingService() {
        ExchangeSpecification exchangeSpec = getExchangeSpecification();
        Boolean useSandbox = (Boolean) exchangeSpec.getExchangeSpecificParametersItem(USE_SANDBOX);
        String uri = useSandbox == null || !useSandbox ? API_URI : TESTNET_API_URI;
        CryptoFacilitiesStreamingService streamingService = new CryptoFacilitiesStreamingService(uri, exchangeSpec.getApiKey(), exchangeSpec.getSecretKey());
        applyStreamingSpecification(exchangeSpec, streamingService);
        return streamingService;
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        return streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        return streamingService.disconnect();
    }

    @Override
    public boolean isAlive() {
        return streamingService != null && streamingService.isSocketOpen();
    }

    @Override
    public StreamingAccountService getStreamingAccountService() {
        return streamingAccountService;
    }

    @Override
    public StreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = super.getDefaultExchangeSpecification();
        spec.setShouldLoadRemoteMetaData(false);
        return spec;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        streamingService.useCompressedMessages(compressedMessages);
    }
}
