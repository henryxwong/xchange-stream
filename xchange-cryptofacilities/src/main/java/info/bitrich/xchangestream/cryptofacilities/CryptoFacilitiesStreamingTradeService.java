package info.bitrich.xchangestream.cryptofacilities;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesOrder;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesPosition;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

public class CryptoFacilitiesStreamingTradeService implements StreamingTradeService {

    private CryptoFacilitiesStreamingService streamingService;

    public CryptoFacilitiesStreamingTradeService(CryptoFacilitiesStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @Override
    public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        return null;
    }

    @Override
    public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        return null;
    }

    public Observable<CryptoFacilitiesPosition> getRawPosition() {
        return streamingService.subscribePositionChannel();
    }

    public Observable<CryptoFacilitiesOrder> getRawOrder() {
        return streamingService.subscribeOrderChannel();
    }
}
