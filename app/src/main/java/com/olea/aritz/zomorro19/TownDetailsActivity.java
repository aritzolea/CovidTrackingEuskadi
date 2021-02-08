package com.olea.aritz.zomorro19;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TownDetailsActivity extends AppCompatActivity {

    private TextView townName, populationText, townIncidenceText, townR0Text;

    private TextView day1, incidence1;
    private TextView day2, incidence2;
    private TextView day3, incidence3;
    private TextView day4, incidence4;
    private TextView day5, incidence5;
    private TextView day6, incidence6;
    private TextView day7, incidence7;
    private TextView day8, incidence8;
    private TextView day9, incidence9;
    private TextView day10, incidence10;
    private TextView day11, incidence11;
    private TextView day12, incidence12;
    private TextView day13, incidence13;
    private TextView day14, incidence14;

    final String RED = "#FF6F6F";
    final String ORANGE = "#FFA86F";
    final String YELLOW = "#FFEF00";
    final String GREEN = "#6FFF6F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_town_details);

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat destinyFormat = new SimpleDateFormat("dd/MM");

        townName = findViewById(R.id.townName);
        populationText = findViewById(R.id.populationText);
        townIncidenceText = findViewById(R.id.townIncidenceText);
        townR0Text = findViewById(R.id.townR0Text);

        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        day5 = findViewById(R.id.day5);
        day6 = findViewById(R.id.day6);
        day7 = findViewById(R.id.day7);
        day8 = findViewById(R.id.day8);
        day9 = findViewById(R.id.day9);
        day10 = findViewById(R.id.day10);
        day11 = findViewById(R.id.day11);
        day12 = findViewById(R.id.day12);
        day13 = findViewById(R.id.day13);
        day14 = findViewById(R.id.day14);

        incidence1 = findViewById(R.id.incidence1);
        incidence2 = findViewById(R.id.incidence2);
        incidence3 = findViewById(R.id.incidence3);
        incidence4 = findViewById(R.id.incidence4);
        incidence5 = findViewById(R.id.incidence5);
        incidence6 = findViewById(R.id.incidence6);
        incidence7 = findViewById(R.id.incidence7);
        incidence8 = findViewById(R.id.incidence8);
        incidence9 = findViewById(R.id.incidence9);
        incidence10 = findViewById(R.id.incidence10);
        incidence11 = findViewById(R.id.incidence11);
        incidence12 = findViewById(R.id.incidence12);
        incidence13 = findViewById(R.id.incidence13);
        incidence14 = findViewById(R.id.incidence14);

        List<TextView> daysList = new ArrayList<>();
        daysList.addAll(Arrays.asList(day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14));

        List<TextView> incidencesList = new ArrayList<>();
        incidencesList.addAll(Arrays.asList(incidence1, incidence2, incidence3, incidence4, incidence5, incidence6, incidence7, incidence8, incidence9, incidence10, incidence11, incidence12, incidence13, incidence14));

        townName.setText(element.getTown());
        populationText.setText(String.valueOf(element.getPopulation()));
        townIncidenceText.setText(String.valueOf(element.getIncidence()));

        if (element.getR0() == -1)
            townR0Text.setText("-");
        else
            townR0Text.setText(String.valueOf(element.getR0()));

        if (element.getPopulation() >= 5000) applyIncidenceColor(townIncidenceText);

        int i = 0;

        for (Map.Entry<String, Integer> entry : element.getIncidences().entrySet()) {
            try {
                daysList.get(i).setText(destinyFormat.format(format.parse(entry.getKey())));
                incidencesList.get(i).setText(String.valueOf(entry.getValue()));
            } catch (ParseException e) {
            }
            i++;
        }
    }

    public void applyIncidenceColor(TextView textView) {
        String value = textView.getText().toString();

        try {
            double numericVal = Double.valueOf(value);

            if (numericVal < 60) textView.setTextColor(Color.parseColor(GREEN));
            else if (numericVal < 300) textView.setTextColor(Color.parseColor(YELLOW));
            else if (numericVal < 500) textView.setTextColor(Color.parseColor(ORANGE));
            else textView.setTextColor(Color.parseColor(RED));

        } catch (Exception e) {
        }
    }
}