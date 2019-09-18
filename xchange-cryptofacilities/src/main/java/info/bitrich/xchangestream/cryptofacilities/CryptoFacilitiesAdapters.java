package info.bitrich.xchangestream.cryptofacilities;

import info.bitrich.xchangestream.cryptofacilities.dto.CryptoFacilitiesTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.math.BigDecimal;
import java.util.Date;

public class CryptoFacilitiesAdapters {

    public static Ticker adaptTicker(CryptoFacilitiesTicker ticker) {
        return new Ticker.Builder()
                .currencyPair(adaptCurrencyPair(ticker.getProductId()))
                .last(new BigDecimal(ticker.getLast()))
                .bid(new BigDecimal(ticker.getBid()))
                .ask(new BigDecimal(ticker.getAsk()))
                .volume(new BigDecimal(ticker.getVolume()))
                .timestamp(new Date(ticker.getTime()))
                .bidSize(new BigDecimal(ticker.getBidSize()))
                .askSize(new BigDecimal(ticker.getAskSize()))
                .build();
    }

    public static CurrencyPair adaptCurrencyPair(String productId) {
        return new CurrencyPair(productId, "");
    }
}
