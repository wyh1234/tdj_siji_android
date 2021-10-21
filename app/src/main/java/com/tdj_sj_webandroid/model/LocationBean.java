package com.tdj_sj_webandroid.model;

public class LocationBean {
    private double Longitude;
    private double Latitude;
    private String address;
    private boolean isBack;

    public boolean isWbeView() {
        return isWbeView;
    }

    public void setWbeView(boolean wbeView) {
        isWbeView = wbeView;
    }

    private boolean isWbeView;

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
}
