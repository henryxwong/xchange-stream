package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoFacilitiesTicker {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("bid")
    private double bid;

    @JsonProperty("ask")
    private double ask;

    @JsonProperty("bid_size")
    private double bidSize;

    @JsonProperty("ask_size")
    private double askSize;

    @JsonProperty("volume")
    private double volume;

    @JsonProperty("dtm")
    private int dtm;

    @JsonProperty("leverage")
    private String leverage;

    @JsonProperty("index")
    private double index;

    @JsonProperty("premium")
    private double premium;

    @JsonProperty("last")
    private double last;

    @JsonProperty("time")
    private long time;

    @JsonProperty("change")
    private double change;

    @JsonProperty("funding_rate")
    private double fundingRate;

    @JsonProperty("funding_rate_prediction")
    private double fundingRatePrediction;

    @JsonProperty("suspended")
    private boolean suspended;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("pair")
    private String pair;

    @JsonProperty("openInterest")
    private double openInterest;

    @JsonProperty("markPrice")
    private double markPrice;

    @JsonProperty("maturityTime")
    private long maturityTime;

    @JsonProperty("relative_funding_rate")
    private double relativeFundingRate;

    @JsonProperty("relative_funding_rate_prediction")
    private double relativeFundingRatePrediction;

    @JsonProperty("next_funding_rate_time")
    private long nextFundingRateTime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBidSize() {
        return bidSize;
    }

    public void setBidSize(double bidSize) {
        this.bidSize = bidSize;
    }

    public double getAskSize() {
        return askSize;
    }

    public void setAskSize(double askSize) {
        this.askSize = askSize;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getDtm() {
        return dtm;
    }

    public void setDtm(int dtm) {
        this.dtm = dtm;
    }

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getFundingRate() {
        return fundingRate;
    }

    public void setFundingRate(double fundingRate) {
        this.fundingRate = fundingRate;
    }

    public double getFundingRatePrediction() {
        return fundingRatePrediction;
    }

    public void setFundingRatePrediction(double fundingRatePrediction) {
        this.fundingRatePrediction = fundingRatePrediction;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public double getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(double openInterest) {
        this.openInterest = openInterest;
    }

    public double getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(double markPrice) {
        this.markPrice = markPrice;
    }

    public long getMaturityTime() {
        return maturityTime;
    }

    public void setMaturityTime(long maturityTime) {
        this.maturityTime = maturityTime;
    }

    public double getRelativeFundingRate() {
        return relativeFundingRate;
    }

    public void setRelativeFundingRate(double relativeFundingRate) {
        this.relativeFundingRate = relativeFundingRate;
    }

    public double getRelativeFundingRatePrediction() {
        return relativeFundingRatePrediction;
    }

    public void setRelativeFundingRatePrediction(double relativeFundingRatePrediction) {
        this.relativeFundingRatePrediction = relativeFundingRatePrediction;
    }

    public long getNextFundingRateTime() {
        return nextFundingRateTime;
    }

    public void setNextFundingRateTime(long nextFundingRateTime) {
        this.nextFundingRateTime = nextFundingRateTime;
    }

    @Override
    public String toString() {
        return "CryptoFacilitiesTicker{" +
                "productId='" + productId + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", bidSize=" + bidSize +
                ", askSize=" + askSize +
                ", volume=" + volume +
                ", dtm=" + dtm +
                ", leverage='" + leverage + '\'' +
                ", index=" + index +
                ", premium=" + premium +
                ", last=" + last +
                ", time=" + time +
                ", change=" + change +
                ", fundingRate=" + fundingRate +
                ", fundingRatePrediction=" + fundingRatePrediction +
                ", suspended=" + suspended +
                ", tag='" + tag + '\'' +
                ", pair='" + pair + '\'' +
                ", openInterest=" + openInterest +
                ", markPrice=" + markPrice +
                ", maturityTime=" + maturityTime +
                ", relativeFundingRate=" + relativeFundingRate +
                ", relativeFundingRatePrediction=" + relativeFundingRatePrediction +
                ", nextFundingRateTime=" + nextFundingRateTime +
                '}';
    }
}
