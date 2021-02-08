package com.olea.aritz.zomorro19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button townsButton;

    TextView lastUpdateText, newPositivesText, r0Text, totalHospitalText, totalUCIText,
            totalPositivesText, totalPositivesGIText, totalPositivesBIText, totalPositivesARText,
            incidenceText, incidenceGIText, incidenceBIText, incidenceARText;

    AdView mAdView;

    final String RED = "#FF6F6F";
    final String ORANGE = "#FFA86F";
    final String YELLOW = "#FFEF00";
    final String GREEN = "#6FFF6F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        townsButton = findViewById(R.id.townsButton);
        lastUpdateText = findViewById(R.id.lastUpdateText);
        newPositivesText = findViewById(R.id.newPositivesText);
        r0Text = findViewById(R.id.r0Text);
        totalHospitalText = findViewById(R.id.totalHospitalText);
        totalUCIText = findViewById(R.id.totalUCIText);
        totalPositivesText = findViewById(R.id.totalPositivesText);
        totalPositivesGIText = findViewById(R.id.totalPositivesGIText);
        totalPositivesBIText = findViewById(R.id.totalPositivesBIText);
        totalPositivesARText = findViewById(R.id.totalPositivesARText);
        incidenceText = findViewById(R.id.incidenceText);
        incidenceGIText = findViewById(R.id.incidenceGIText);
        incidenceBIText = findViewById(R.id.incidenceBIText);
        incidenceARText = findViewById(R.id.incidenceARText);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat destinyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            Date date = format.parse(BackendData.lastUpdateDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, 1);

            lastUpdateText.setText("Última actualización: " + destinyFormat.format(calendar.getTime()));
        } catch (ParseException e) {
            lastUpdateText.setText("Última actualización: -");
        }
        newPositivesText.setText(String.valueOf(BackendData.dayPositives));
        r0Text.setText(String.valueOf(BackendData.r0));
        totalHospitalText.setText(String.valueOf(BackendData.totalHospital));
        totalUCIText.setText(String.valueOf(BackendData.totalUCI));
        totalPositivesText.setText(String.valueOf(BackendData.totalPositives));
        totalPositivesGIText.setText(String.valueOf(BackendData.totalPositivesGI));
        totalPositivesBIText.setText(String.valueOf(BackendData.totalPositivesBI));
        totalPositivesARText.setText(String.valueOf(BackendData.totalPositivesAR));
        incidenceText.setText(String.valueOf(BackendData.incidence));
        incidenceGIText.setText(String.valueOf(BackendData.incidenceGI));
        incidenceBIText.setText(String.valueOf(BackendData.incidenceBI));
        incidenceARText.setText(String.valueOf(BackendData.incidenceAR));

        applyIncidenceColor(incidenceText);
        applyIncidenceColor(incidenceGIText);
        applyIncidenceColor(incidenceBIText);
        applyIncidenceColor(incidenceARText);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        townsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TownsTableActivity.class);
            startActivity(intent);
        });
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