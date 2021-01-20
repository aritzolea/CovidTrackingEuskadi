package com.olea.aritz.covidtrackingeuskadi;

import android.graphics.Color;
import android.util.Log;

import java.io.Serializable;

public class ListElement implements Serializable {

    final String RED = "#FF6F6F";
    final String ORANGE = "#FFA86F";
    final String YELLOW = "#FFF66F";
    final String GREEN = "#6FFF6F";
    final String WHITE = "#FFFFFF";

    private int color;
    private String town;
    private double incidence;
    private int code;

    public ListElement(String town, double incidence, int code, int population) {
        int colorToShow;

        if (population > 5000) colorToShow = getIncidenceColor(incidence);
        else colorToShow = Color.parseColor(WHITE);

        this.color = colorToShow;
        this.town = town;
        this.incidence = incidence;
        this.code = code;
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

    public double getIncidence() {
        return incidence;
    }

    public void setIncidence(double incidence) {
        this.incidence = incidence;
    }

    public int getIncidenceColor(double incidence) {
        if (incidence < 60) return Color.parseColor(GREEN);
        else if (incidence < 300) return Color.parseColor(YELLOW);
        else if (incidence < 500) return Color.parseColor(ORANGE);
        else return Color.parseColor(RED);
    }
}
