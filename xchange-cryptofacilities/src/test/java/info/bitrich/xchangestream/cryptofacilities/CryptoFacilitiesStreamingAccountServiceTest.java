package info.bitrich.xchangestream.cryptofacilities;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesBalance;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoFacilitiesStreamingAccountServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(CryptoFacilitiesStreamingAccountServiceTest.class);

    private static CryptoFacilitiesStreamingExchange exchange;

    private static CryptoFacilitiesStreamingAccountService accountService;

    @BeforeClass
    public static void setup() {
        ExchangeSpecification exSpec = new CryptoFacilitiesStreamingExchange().getDefaultExchangeSpecification();
        exSpec.setExchangeSpecificParametersItem("Use_Sandbox", Boolean.TRUE);
        exSpec.setApiKey(System.getProperty("API_KEY"));
        exSpec.setSecretKey(System.getProperty("SECRET_KEY"));

        exchange = (CryptoFacilitiesStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(exSpec);
        exchange.connect().blockingAwait();
        accountService = (CryptoFacilitiesStreamingAccountService) exchange.getStreamingAccountService();
    }

    @Test
    public void testSignChallenge() {
        Assert.assertEquals("4JEpF3ix66GA2B+ooK128Ift4XQVtc137N9yeg4Kqsn9PI0Kpzbysl9M1IeCEdjg0zl00wkVqcsnG4bmnlMb3A==",
                CryptoFacilitiesStreamingService.signChallenge("c100b894-1729-464d-ace1-52dbce11db42",
                        "7zxMEF5p/Z8l2p2U7Ghv6x14Af+Fx+92tPgUdVQ748FOIrEoT9bgT+bTRfXc5pz8na+hL/QdrCVG7bh9KpT0eMTm"));
    }

    @Test
    public void testRawBalance() {
        Observable<CryptoFacilitiesBalance> balanceObservable = accountService.getRawBalance();
        Disposable disposable = balanceObservable.subscribe(rawBalance -> LOG.info(rawBalance.toString()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }
}
