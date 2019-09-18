package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoFacilitiesTrade {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("side")
    private String side;

    @JsonProperty("type")
    private String type;

    @JsonProperty("seq")
    private long seq;

    @JsonProperty("time")
    private long time;

    @JsonProperty("qty")
    private double qty;

    @JsonProperty("price")
    private double price;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CryptoFacilitiesTrade{" +
                "productId='" + productId + '\'' +
                ", side='" + side + '\'' +
                ", type='" + type + '\'' +
                ", seq=" + seq +
                ", time=" + time +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
