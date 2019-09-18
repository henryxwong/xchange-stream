package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.SortedMap;

public class CryptoFacilitiesOrderBook {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("seq")
    private long seq;

    @JsonProperty("bids")
    @JsonDeserialize(using = CryptoFacilitiesOrderBookDeserializer.class)
    private SortedMap<BigDecimal, BigDecimal> bids;

    @JsonProperty("asks")
    @JsonDeserialize(using = CryptoFacilitiesOrderBookDeserializer.class)
    private SortedMap<BigDecimal, BigDecimal> asks;

    @JsonProperty("tickSize")
    private double tickSize;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public SortedMap<BigDecimal, BigDecimal> getBids() {
        return bids;
    }

    public void setBids(SortedMap<BigDecimal, BigDecimal> bids) {
        this.bids = bids;
    }

    public SortedMap<BigDecimal, BigDecimal> getAsks() {
        return asks;
    }

    public void setAsks(SortedMap<BigDecimal, BigDecimal> asks) {
        this.asks = asks;
    }

    public double getTickSize() {
        return tickSize;
    }

    public void setTickSize(double tickSize) {
        this.tickSize = tickSize;
    }

    @Override
    public String toString() {
        return "CryptoFacilitiesOrderBook{" +
                "productId='" + productId + '\'' +
                ", timestamp=" + timestamp +
                ", seq=" + seq +
                ", bids=" + bids +
                ", asks=" + asks +
                ", tickSize=" + tickSize +
                '}';
    }

    public void updateDelta(CryptoFacilitiesOrderBookDelta delta) {
        if (delta.getSeq() <= seq || delta.getTimestamp() < timestamp) {
            throw new IllegalArgumentException("Invalid delta: " + delta);
        }

        SortedMap<BigDecimal, BigDecimal> map = delta.getSide().equals("buy") ? bids : asks;
        if (delta.getQty().compareTo(BigDecimal.ZERO) == 0) {
            map.remove(delta.getPrice());
        } else {
            map.put(delta.getPrice(), delta.getQty());
        }

        seq = delta.getSeq();
        timestamp = delta.getTimestamp();
    }
}
