package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoFacilitiesTickerLite {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("bid")
    private double bid;

    @JsonProperty("ask")
    private double ask;

    @JsonProperty("change")
    private double change;

    @JsonProperty("premium")
    private double premium;

    @JsonProperty("volume")
    private double volume;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("pair")
    private String pair;

    @JsonProperty("dtm")
    private int dtm;

    @JsonProperty("maturityTime")
    private long maturityTime;

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

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
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

    public int getDtm() {
        return dtm;
    }

    public void setDtm(int dtm) {
        this.dtm = dtm;
    }

    public long getMaturityTime() {
        return maturityTime;
    }

    public void setMaturityTime(long maturityTime) {
        this.maturityTime = maturityTime;
    }

    @Override
    public String toString() {
        return "CryptoFacilitiesTickerLite{" +
                "productId='" + productId + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", change=" + change +
                ", premium=" + premium +
                ", volume=" + volume +
                ", tag='" + tag + '\'' +
                ", pair='" + pair + '\'' +
                ", dtm=" + dtm +
                ", maturityTime=" + maturityTime +
                '}';
    }
}
