package com.olea.aritz.zomorro19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button townsButton;

    TextView lastUpdateText, newPositivesText, r0Text, totalHospitalText, totalUCIText,
            totalPositivesText, totalPositivesGIText, totalPositivesBIText, totalPositivesARText,
            incidenceText, incidenceGIText, incidenceBIText, incidenceARText;

    TextView newPositivesLabel, totalHospitalLabel, totalUCILabel, totalPositivesLabel, incidenceLabel, titleLabel;

    TextView spanishLabel, basqueLabel;

    AdView mAdView;

    final String RED = "#FF6F6F";
    final String ORANGE = "#FFA86F";
    final String YELLOW = "#FFD800";
    final String GREEN = "#6FFF6F";

    final String GRAY = "#A5A5A5";
    final String BLACK = "#000000";

    boolean isSpanish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        isSpanish = true;

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
        newPositivesLabel = findViewById(R.id.newPositivesLabel);
        totalHospitalLabel = findViewById(R.id.totalHospitalLabel);
        totalUCILabel = findViewById(R.id.totalUCILabel);
        totalPositivesLabel = findViewById(R.id.totalPositivesLabel);
        incidenceLabel = findViewById(R.id.incidenceLabel);
        titleLabel = findViewById(R.id.titleLabel);

        spanishLabel = findViewById(R.id.spanishLabel);
        basqueLabel = findViewById(R.id.basqueLabel);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat destinyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            Date date = format.parse(BackendData.lastUpdateDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, 1);

            lastUpdateText.setText(getString(R.string.ultima_actualizaci_n_xx).replace("XX", destinyFormat.format(calendar.getTime())));
        } catch (ParseException e) {
            lastUpdateText.setText(getString(R.string.ultima_actualizaci_n_xx).replace("XX", "-"));
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

        spanishLabel.setOnClickListener(v -> {
            if (isSpanish) return;

            setLocale("es");

            basqueLabel.setTextColor(Color.parseColor(GRAY));
            basqueLabel.setTypeface(null, Typeface.NORMAL);

            spanishLabel.setTextColor(Color.parseColor(BLACK));
            spanishLabel.setTypeface(null, Typeface.BOLD);

            isSpanish = true;
        });

        basqueLabel.setOnClickListener(v -> {
            if (!isSpanish) return;

            setLocale("eu");

            spanishLabel.setTextColor(Color.parseColor(GRAY));
            spanishLabel.setTypeface(null, Typeface.NORMAL);

            basqueLabel.setTextColor(Color.parseColor(BLACK));
            basqueLabel.setTypeface(null, Typeface.BOLD);

            isSpanish = false;
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

    public void setLocale(String lang) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        Locale myLocale = new Locale(lang);
        conf.locale = myLocale;

        res.updateConfiguration(conf, dm);
        getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());

        newPositivesLabel.setText(getString(R.string.nuevos_positivos));
        totalHospitalLabel.setText(getString(R.string.total_hospitalizados));
        totalUCILabel.setText(getString(R.string.total_en_uci));
        totalPositivesLabel.setText(getString(R.string.total_positivos));
        incidenceLabel.setText(getString(R.string.incidencia_14_dias_100_000));
        townsButton.setText(getString(R.string.datos_por_municipio));
        titleLabel.setText(getString(R.string.datos_generales_euskadi));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat destinyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            Date date = format.parse(BackendData.lastUpdateDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, 1);

            lastUpdateText.setText(getString(R.string.ultima_actualizaci_n_xx).replace("XX", destinyFormat.format(calendar.getTime())));
        } catch (ParseException e) {
            lastUpdateText.setText(getString(R.string.ultima_actualizaci_n_xx).replace("XX", "-"));
        }
    }
}