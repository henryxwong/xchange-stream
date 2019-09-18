package info.bitrich.xchangestream.cryptofacilities;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesOrderBook;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTicker;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTickerLite;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTrade;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

public class CryptoFacilitiesStreamingMarketDataService implements StreamingMarketDataService {

    private CryptoFacilitiesStreamingService streamingService;

    public CryptoFacilitiesStreamingMarketDataService(CryptoFacilitiesStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    public Observable<CryptoFacilitiesOrderBook> getRawOrderBook(String productId) {
        return streamingService.subscribeOrderBookChannel(productId);
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        return null;
    }

    public Observable<CryptoFacilitiesTicker> getRawTicker(String productId) {
        return streamingService.subscribeTickerChannel(productId);
    }

    public Observable<CryptoFacilitiesTickerLite> getRawTickerLite(String productId) {
        return streamingService.subscribeTickerLiteChannel(productId);
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        return getRawTicker(currencyPair.base.toString()).map(CryptoFacilitiesAdapters::adaptTicker);
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        return null;
    }

    public Observable<CryptoFacilitiesTrade> getRawTrade(String productId) {
        return streamingService.subscribeTradeChannel(productId);
    }
}
