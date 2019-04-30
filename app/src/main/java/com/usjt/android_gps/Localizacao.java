package com.usjt.android_gps;

import java.io.Serializable;

public class Localizacao implements Serializable {

    private double latitude;
    private double longitude;

    public Localizacao(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Localizacao() {
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
