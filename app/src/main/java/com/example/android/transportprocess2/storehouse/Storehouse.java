package com.example.android.transportprocess2.storehouse;

public class Storehouse {

    private String storeTitle;
    private String storeLine;
    private String storePlace;
    private String storeState;
    private String storeNoOrder;
    private String storeTime;
    private int storeImageState;

    public Storehouse() {
    }

    public Storehouse(String storeTitle, String storeLine, String storePlace, String storeState, String storeNoOrder, String storeTime, int storeImageState) {
        this.storeTitle = storeTitle;
        this.storeLine = storeLine;
        this.storePlace = storePlace;
        this.storeState = storeState;
        this.storeNoOrder=storeNoOrder;
        this.storeTime=storeTime;
        this.storeImageState = storeImageState;
    }

    public String getStoreTitle() {
        return storeTitle;
    }

    public void setStoreTitle(String storeTitle) {
        this.storeTitle = storeTitle;
    }

    public String getStoreLine() {
        return storeLine;
    }

    public void setStoreLine(String storeLine) {
        this.storeLine = storeLine;
    }

    public String getStorePlace() {
        return storePlace;
    }

    public void setStorePlace(String storePlace) {
        this.storePlace = storePlace;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }

    public int getStoreImageState() {
        return storeImageState;
    }

    public void setStoreImageState(int storeImageState) {
        this.storeImageState = storeImageState;
    }

    public String getStoreNoOrder() {
        return storeNoOrder;
    }

    public void setStoreNoOrder(String storeNoOrder) {
        this.storeNoOrder = storeNoOrder;
    }

    public String getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(String storeTime) {
        this.storeTime = storeTime;
    }
}
