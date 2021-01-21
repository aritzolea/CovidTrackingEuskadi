package com.olea.aritz.covidtrackingeuskadi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class TownDetailsActivity extends AppCompatActivity {

    private TextView townName, populationText, townIncidenceText, townR0Text;

    final String RED = "#FF6F6F";
    final String ORANGE = "#FFA86F";
    final String YELLOW = "#FFF66F";
    final String GREEN = "#6FFF6F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_town_details);

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");

        townName = findViewById(R.id.townName);
        populationText = findViewById(R.id.populationText);
        townIncidenceText = findViewById(R.id.townIncidenceText);
        townR0Text = findViewById(R.id.townR0Text);

        townName.setText(element.getTown());
        populationText.setText(String.valueOf(element.getPopulation()));
        townIncidenceText.setText(String.valueOf(element.getIncidence()));
        townR0Text.setText(String.valueOf(BackendData.townR0));

        if (element.getPopulation() >= 5000) applyIncidenceColor(townIncidenceText);
    }

    public void applyIncidenceColor(TextView textView) {
        String value = textView.getText().toString();

        try {
            double numericVal = Double.valueOf(value);

            if (numericVal < 60) textView.setTextColor(Color.parseColor(GREEN));
            else if (numericVal < 300) textView.setTextColor(Color.parseColor(YELLOW));
            else if (numericVal < 500) textView.setTextColor(Color.parseColor(ORANGE));
            else textView.setTextColor(Color.parseColor(RED));

        } catch (Exception e) {}
    }
}