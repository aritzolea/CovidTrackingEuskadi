package com.olea.aritz.covidtrackingeuskadi;

import java.io.Serializable;

public class ListElement implements Serializable {

    private int color;
    private String town;
    private String incidence;

    public ListElement(int color, String town, String incidence) {
        this.color = color;
        this.town = town;
        this.incidence = incidence;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }
}
