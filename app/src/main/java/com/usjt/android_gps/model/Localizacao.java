package com.usjt.android_gps.model;

import java.io.Serializable;

public class Localizacao implements Serializable {

    private long id;
    private double latitude;
    private double longitude;

    public Localizacao(
            long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Localizacao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Localizacao" +
                "\nLatitude=" + latitude ;
    }

    public String toString2(){
        return ", Longitude=" + longitude;
    }
}
