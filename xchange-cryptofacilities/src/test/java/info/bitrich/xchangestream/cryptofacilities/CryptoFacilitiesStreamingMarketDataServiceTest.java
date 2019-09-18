package info.bitrich.xchangestream.cryptofacilities;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesOrderBook;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTicker;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTickerLite;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTrade;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoFacilitiesStreamingMarketDataServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(CryptoFacilitiesStreamingMarketDataServiceTest.class);

    private static CryptoFacilitiesStreamingExchange exchange;

    private static CryptoFacilitiesStreamingMarketDataService mds;

    @BeforeClass
    public static void setup() {
        ExchangeSpecification spec = new CryptoFacilitiesStreamingExchange().getDefaultExchangeSpecification();
        exchange = (CryptoFacilitiesStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
        exchange.connect().blockingAwait();
        mds = (CryptoFacilitiesStreamingMarketDataService) exchange.getStreamingMarketDataService();
    }

    @Test
    public void testGetRawTicker() {
        Observable<CryptoFacilitiesTicker> rawTickerObservable = mds.getRawTicker("PI_XBTUSD");
        Disposable disposable = rawTickerObservable.subscribe(rawTicker -> LOG.info(rawTicker.toString()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

    @Test
    public void testGetRawTickerLite() {
        Observable<CryptoFacilitiesTickerLite> rawTickerObservable = mds.getRawTickerLite("PI_XBTUSD");
        Disposable disposable = rawTickerObservable.subscribe(rawTickerLite -> LOG.info(rawTickerLite.toString()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

    @Test
    public void testGetTicker() {
        Observable<Ticker> tickerObservable = mds.getTicker(new CurrencyPair("PI_XBTUSD", ""));
        Disposable disposable = tickerObservable.subscribe(ticker -> LOG.info(ticker.toString()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

    @Test
    public void testGetRawOrderBook() {
        Observable<CryptoFacilitiesOrderBook> orderBookObservable = mds.getRawOrderBook("PI_XBTUSD");
        Disposable disposable = orderBookObservable.subscribe(rawOrderBook -> LOG.info(rawOrderBook.toString()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

    @Test
    public void testGetTrade() {
        Observable<CryptoFacilitiesTrade> tradeObservable = mds.getRawTrade("PI_XBTUSD");
        Disposable disposable = tradeObservable.subscribe(ticker -> LOG.info(ticker.toString()));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }
}
