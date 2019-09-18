package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoFacilitiesPosition {

    @JsonProperty("instrument")
    private String instrument;

    @JsonProperty("balance")
    private double balance;

    @JsonProperty("entry_price")
    private double entryPrice;

    @JsonProperty("mark_price")
    private double markPrice;

    @JsonProperty("index_price")
    private double indexPrice;

    @JsonProperty("pnl")
    private double pnl;

    @JsonProperty("liquidation_threshold")
    private double liquidationThreshold;

    @JsonProperty("return_on_equity")
    private double returnOnEquity;

    @JsonProperty("effective_leverage")
    private double effectiveLeverage;

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public double getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(double markPrice) {
        this.markPrice = markPrice;
    }

    public double getIndexPrice() {
        return indexPrice;
    }

    public void setIndexPrice(double indexPrice) {
        this.indexPrice = indexPrice;
    }

    public double getPnl() {
        return pnl;
    }

    public void setPnl(double pnl) {
        this.pnl = pnl;
    }

    public double getLiquidationThreshold() {
        return liquidationThreshold;
    }

    public void setLiquidationThreshold(double liquidationThreshold) {
        this.liquidationThreshold = liquidationThreshold;
    }

    public double getReturnOnEquity() {
        return returnOnEquity;
    }

    public void setReturnOnEquity(double returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }

    public double getEffectiveLeverage() {
        return effectiveLeverage;
    }

    public void setEffectiveLeverage(double effectiveLeverage) {
        this.effectiveLeverage = effectiveLeverage;
    }

    @Override
    public String toString() {
        return "CryptoFacilitiesPosition{" +
                "instrument='" + instrument + '\'' +
                ", balance=" + balance +
                ", entryPrice=" + entryPrice +
                ", markPrice=" + markPrice +
                ", indexPrice=" + indexPrice +
                ", pnl=" + pnl +
                ", liquidationThreshold=" + liquidationThreshold +
                ", returnOnEquity=" + returnOnEquity +
                ", effectiveLeverage=" + effectiveLeverage +
                '}';
    }
}
