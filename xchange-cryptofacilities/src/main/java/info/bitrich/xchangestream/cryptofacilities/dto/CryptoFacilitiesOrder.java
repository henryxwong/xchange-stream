package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoFacilitiesOrder {

    @JsonProperty("instrument")
    private String instrument;

    @JsonProperty("time")
    private long time;

    @JsonProperty("last_update_time")
    private long lastUpdateTime;

    @JsonProperty("qty")
    private double qty;

    @JsonProperty("filled")
    private double filled;

    @JsonProperty("limit_price")
    private double limitPrice;

    @JsonProperty("stop_price")
    private double stopPrice;

    @JsonProperty("type")
    private String type;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("cli_ord_id")
    private String cliOrdId;

    @JsonProperty("direction")
    private int direction;

    @JsonProperty("reduce_only")
    private boolean reduceOnly;

    @JsonProperty("is_cancel")
    private boolean isCancel;

    @JsonProperty("reason")
    private String reason;

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getFilled() {
        return filled;
    }

    public void setFilled(double filled) {
        this.filled = filled;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCliOrdId() {
        return cliOrdId;
    }

    public void setCliOrdId(String cliOrdId) {
        this.cliOrdId = cliOrdId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isReduceOnly() {
        return reduceOnly;
    }

    public void setReduceOnly(boolean reduceOnly) {
        this.reduceOnly = reduceOnly;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "CryptoFacilitiesOrder{" +
                "instrument='" + instrument + '\'' +
                ", time=" + time +
                ", lastUpdateTime=" + lastUpdateTime +
                ", qty=" + qty +
                ", filled=" + filled +
                ", limitPrice=" + limitPrice +
                ", stopPrice=" + stopPrice +
                ", type='" + type + '\'' +
                ", orderId='" + orderId + '\'' +
                ", cliOrdId='" + cliOrdId + '\'' +
                ", direction=" + direction +
                ", reduceOnly=" + reduceOnly +
                ", isCancel=" + isCancel +
                ", reason='" + reason + '\'' +
                '}';
    }
}
