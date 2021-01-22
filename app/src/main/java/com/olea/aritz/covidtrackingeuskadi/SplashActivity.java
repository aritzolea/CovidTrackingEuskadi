package com.olea.aritz.covidtrackingeuskadi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    Animation topAnimation, bottomAnimation;

    ImageView splashImageView;
    TextView splashTitle, splashSubtitle, loadingText;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        topAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadingText.setAlpha(1.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        splashImageView = findViewById(R.id.splashImageView);
        splashTitle = findViewById(R.id.splashTitle);
        splashSubtitle = findViewById(R.id.splashSubtitle);
        loadingText = findViewById(R.id.loadingText);

        splashImageView.setAnimation(bottomAnimation);
        splashTitle.setAnimation(topAnimation);
        splashSubtitle.setAnimation(topAnimation);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            AlertDialog internetDialog = new MaterialAlertDialogBuilder(this).setMessage("Esta aplicación no puede funcionar sin conexión a Internet.")
                    .setPositiveButton("OK", null)
                    .show();

            internetDialog.setOnDismissListener(dialog -> {
                finish();
                System.exit(0);
            });

            return;
        }

        requestQueue = Volley.newRequestQueue(this);

        getGeneralAndTownsInfo();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
        }, 5000);
    }

    public void getGeneralAndTownsInfo() {
        String generalDataUrl = "http://35.180.25.221:1512/general_data";
        String townsDataUrl = "http://35.180.25.221:1512/towns";

        JsonArrayRequest generalDataRequest = new JsonArrayRequest(Request.Method.GET, generalDataUrl, null,
                response -> {
                    JSONObject jsonObject;
                    try {
                        jsonObject = response.getJSONObject(0);

                        BackendData.lastUpdateDate = jsonObject.getString("last_update");
                        BackendData.dayPositives = jsonObject.getInt("day_positives");
                        BackendData.r0 = jsonObject.getDouble("r0");
                        BackendData.totalHospital = jsonObject.getInt("total_hospital");
                        BackendData.totalUCI = jsonObject.getInt("total_uci");
                        BackendData.totalPositives = jsonObject.getInt("total_positives");
                        BackendData.totalPositivesGI = jsonObject.getInt("total_positivesgi");
                        BackendData.totalPositivesBI = jsonObject.getInt("total_positivesbi");
                        BackendData.totalPositivesAR = jsonObject.getInt("total_positivesar");
                        BackendData.incidence = jsonObject.getDouble("incidence");
                        BackendData.incidenceGI = jsonObject.getDouble("incidencegi");
                        BackendData.incidenceBI = jsonObject.getDouble("incidencebi");
                        BackendData.incidenceAR = jsonObject.getDouble("incidencear");
                    } catch (JSONException e) {
                        //TODO: Dar error al LEER los datos
                    }

                },
                error -> {
                    //TODO: Dar error al OBTENER los datos
                }
        );

        JsonArrayRequest townsRequest = new JsonArrayRequest(Request.Method.GET, townsDataUrl, null,
                response -> {
                    try {
                        BackendData.towns = new ArrayList<>();

                        for (int i = 0; i < response.length(); i += 14) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String name = jsonObject.getString("name");
                            double incidence = jsonObject.getDouble("incidence");
                            int code = jsonObject.getInt("code");
                            int population = jsonObject.getInt("population");
                            double r0 = jsonObject.getDouble("r0");

                            Map<String, Integer> incidencesMap = new LinkedHashMap<>();

                            for (int j = i; j < i + 14; j++) {
                                JSONObject jsonIndividualObject = response.getJSONObject(j);

                                String date = jsonIndividualObject.getString("date");
                                int day_incidence = jsonIndividualObject.getInt("day_incidence");

                                incidencesMap.put(date, day_incidence);
                            }

                            BackendData.towns.add(new ListElement(name, incidence, code, population, r0, incidencesMap));
                        }
                    } catch (JSONException e) {
                        //TODO: Dar error al LEER los datos
                    }

                },
                error -> {
                    //TODO: Dar error al OBTENER los datos
                }
        );

        requestQueue.add(generalDataRequest);
        requestQueue.add(townsRequest);
    }
}