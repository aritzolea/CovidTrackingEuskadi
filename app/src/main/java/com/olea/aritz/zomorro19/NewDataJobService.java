package com.olea.aritz.zomorro19;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewDataJobService extends JobService {

    final String FILENAME = "LASTUPDATE";
    final String GENERALDATAURL = "http://35.180.25.221:1512/general_data";

    @Override
    public boolean onStartJob(JobParameters params) {
        new Thread(() -> {
            FileInputStream inputStream = null;

            try {
                inputStream = openFileInput(FILENAME);
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader);

                String text = bufferedReader.readLine();

                JsonArrayRequest lastUpdateRequest = new JsonArrayRequest(Request.Method.GET, GENERALDATAURL, null,
                        response -> {
                            JSONObject jsonObject;
                            try {
                                jsonObject = response.getJSONObject(0);

                                String lastUpdate = jsonObject.getString("last_update");

                                if (!text.equals(lastUpdate)) {
                                    writeLastUpdateDate(lastUpdate);
                                    sendNewDataNotification();
                                }

                            } catch (JSONException e) {
                                //TODO: Dar error al LEER los datos
                            }

                        },
                        error -> {
                            //TODO: Dar error al OBTENER los datos
                        }
                );

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(lastUpdateRequest);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void writeLastUpdateDate(String lastUpdate) {
        FileOutputStream outputStream = null;

        try {
            outputStream = openFileOutput(FILENAME, MODE_PRIVATE);
            outputStream.write(lastUpdate.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendNewDataNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CovidEuskadiChannel", "New data channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CovidEuskadiChannel");
        builder.setContentTitle("ZOMORRO 19");
        builder.setContentText("Ya están disponibles los nuevos datos!");
        builder.setSmallIcon(R.drawable.ic_lens_black_24dp);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setWhen(System.currentTimeMillis());
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        Intent appIntent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());
    }
}
