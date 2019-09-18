package info.bitrich.xchangestream.cryptofacilities;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesOrder;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesPosition;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoFacilitiesStreamingTradeServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(CryptoFacilitiesStreamingTradeServiceTest.class);

    private static CryptoFacilitiesStreamingExchange exchange;

    private static CryptoFacilitiesStreamingTradeService tradeService;

    @BeforeClass
    public static void setup() {
        ExchangeSpecification exSpec = new CryptoFacilitiesStreamingExchange().getDefaultExchangeSpecification();
        exSpec.setExchangeSpecificParametersItem("Use_Sandbox", Boolean.TRUE);
        exSpec.setApiKey(System.getProperty("API_KEY"));
        exSpec.setSecretKey(System.getProperty("SECRET_KEY"));

        exchange = (CryptoFacilitiesStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(exSpec);
        exchange.connect().blockingAwait();
        tradeService = (CryptoFacilitiesStreamingTradeService) exchange.getStreamingTradeService();
    }

    @Test
    public void testGetPosition() {
        Observable<CryptoFacilitiesPosition> observable = tradeService.getRawPosition();
        Disposable disposable = observable.subscribe(rawPosition -> LOG.info(rawPosition.toString()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

    @Test
    public void testGetOrder() {
        Observable<CryptoFacilitiesOrder> observable = tradeService.getRawOrder();
        Disposable disposable = observable.subscribe(rawOrder -> LOG.info(rawOrder.toString()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }
}
