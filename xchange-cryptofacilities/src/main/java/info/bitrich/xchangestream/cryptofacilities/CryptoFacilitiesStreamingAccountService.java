package info.bitrich.xchangestream.cryptofacilities;

import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesBalance;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

public class CryptoFacilitiesStreamingAccountService implements StreamingAccountService {

    private CryptoFacilitiesStreamingService streamingService;

    public CryptoFacilitiesStreamingAccountService(CryptoFacilitiesStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        return null;
    }

    public Observable<CryptoFacilitiesBalance> getRawBalance() {
        return streamingService.subscribeBalanceChannel();
    }
}
