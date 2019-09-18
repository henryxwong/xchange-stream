package info.bitrich.xchangestream.cryptofacilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoFacilitiesBalance {

    @JsonProperty("name")
    private String name;

    @JsonProperty("pv")
    private double portfolioValue;

    @JsonProperty("am")
    private double availableMargin;

    @JsonProperty("im")
    private double initialMargin;

    @JsonProperty("mm")
    private double maintenanceMargin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(double portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public double getAvailableMargin() {
        return availableMargin;
    }

    public void setAvailableMargin(double availableMargin) {
        this.availableMargin = availableMargin;
    }

    public double getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(double initialMargin) {
        this.initialMargin = initialMargin;
    }

    public double getMaintenanceMargin() {
        return maintenanceMargin;
    }

    public void setMaintenanceMargin(double maintenanceMargin) {
        this.maintenanceMargin = maintenanceMargin;
    }

    @Override
    public String toString() {
        return "CryptoFacilitiesBalance{" +
                "name='" + name + '\'' +
                ", portfolioValue=" + portfolioValue +
                ", availableMargin=" + availableMargin +
                ", initialMargin=" + initialMargin +
                ", maintenanceMargin=" + maintenanceMargin +
                '}';
    }
}
