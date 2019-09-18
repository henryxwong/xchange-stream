package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CryptoFacilitiesOrderBookDelta {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("side")
    private String side;

    @JsonProperty("seq")
    private long seq;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("qty")
    private BigDecimal qty;

    @JsonProperty("timestamp")
    private long timestamp;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CryptoFacilitiesOrderBookDelta{" +
                "productId='" + productId + '\'' +
                ", side='" + side + '\'' +
                ", seq=" + seq +
                ", price=" + price +
                ", qty=" + qty +
                ", timestamp=" + timestamp +
                '}';
    }
}
