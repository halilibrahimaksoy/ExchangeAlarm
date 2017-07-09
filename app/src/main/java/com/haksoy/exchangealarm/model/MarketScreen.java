package com.haksoy.exchangealarm.model;

/**
 * Created by haksoy on 5.04.2017.
 */

public class MarketScreen {
    private int id;
    private String name;
    private float price;
    private float yesterDayPrice;
    private String percentageChange;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getYesterDayPrice() {
        return yesterDayPrice;
    }

    public void setYesterDayPrice(float yesterDayPrice) {
        this.yesterDayPrice = yesterDayPrice;
    }

    public String getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(String percentageChange) {
        this.percentageChange = percentageChange;
    }
}
