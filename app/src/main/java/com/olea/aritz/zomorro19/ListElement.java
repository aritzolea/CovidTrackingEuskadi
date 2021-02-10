package com.olea.aritz.zomorro19;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Map;

public class ListElement implements Serializable {

    final String RED = "#FF6F6F";
    final String ORANGE = "#FFA86F";
    final String YELLOW = "#FFD800";
    final String GREEN = "#6FFF6F";
    final String WHITE = "#FFFFFF";

    private int color;
    private String town;
    private double incidence;
    private int code;
    private int population;
    private double r0;

    private Map<String, Integer> incidences;

    public ListElement(String town, double incidence, int code, int population, double r0, Map<String, Integer> incidences) {
        int colorToShow;

        if (population > 5000) colorToShow = getIncidenceColor(incidence);
        else colorToShow = Color.parseColor(WHITE);

        this.color = colorToShow;
        this.town = town;
        this.incidence = incidence;
        this.code = code;
        this.population = population;
        this.r0 = r0;

        this.incidences = incidences;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getR0() {
        return r0;
    }

    public void setR0(double r0) {
        this.r0 = r0;
    }

    public Map<String, Integer> getIncidences() {
        return incidences;
    }

    public void setIncidences(Map<String, Integer> incidences) {
        this.incidences = incidences;
    }

    public int getIncidenceColor(double incidence) {
        if (incidence < 60) return Color.parseColor(GREEN);
        else if (incidence < 300) return Color.parseColor(YELLOW);
        else if (incidence < 500) return Color.parseColor(ORANGE);
        else return Color.parseColor(RED);
    }
}
