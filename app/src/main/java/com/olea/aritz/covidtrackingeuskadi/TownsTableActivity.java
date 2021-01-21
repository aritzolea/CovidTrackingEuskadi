package com.olea.aritz.covidtrackingeuskadi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TownsTableActivity extends AppCompatActivity {

    private ListAdapter listAdapter;

    private RecyclerView recyclerView;

    private List<ListElement> towns;

    private TownsTableActivity instance;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_towns_table);

        searchView = findViewById(R.id.searchView);

        instance = this;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                towns.clear();
                if (TextUtils.isEmpty(newText)) {
                    for (ListElement le : BackendData.towns)
                        towns.add(le);
                } else {
                    for (ListElement le : BackendData.towns) {
                        if (le.getTown().toLowerCase().contains(newText.trim().toLowerCase()))
                            towns.add(le);
                    }
                }

                listAdapter = new ListAdapter(towns, instance, item -> {
                    //TODO: BUG: Se puede hacer click rápido en dos municipios y se abren los dos
                    moveToTownDetails(item);
                });

                recyclerView.setLayoutManager(new LinearLayoutManager(instance));
                recyclerView.setAdapter(listAdapter);

                return true;
            }
        });

        init();
    }

    public void init() {

        towns = new ArrayList<>();
        for (ListElement le : BackendData.towns) {
            towns.add(le);
        }

        listAdapter = new ListAdapter(towns, this, item -> {
            //TODO: BUG: Se puede hacer click rápido en dos municipios y se abren los dos
            moveToTownDetails(item);
        });

        recyclerView = findViewById(R.id.townList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    private void moveToTownDetails(ListElement townItem) {
        String townDataUrl = "http://35.180.25.221:1512/town/" + townItem.getCode();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest townRequest = new JsonArrayRequest(Request.Method.GET, townDataUrl, null,
                response -> {
                    try {
                        BackendData.townIncidences = new HashMap<>();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            if (i == 0) {
                                BackendData.townR0 = jsonObject.getDouble("r0");
                            }

                            BackendData.townIncidences.put(jsonObject.getString("date"), jsonObject.getInt("incidence"));
                        }
                    } catch (JSONException e) {
                        //TODO: Dar error al LEER los datos
                    }

                },
                error -> {
                    //TODO: Dar error al OBTENER los datos
                }
        );

        requestQueue.add(townRequest);

        Intent intent = new Intent(this, TownDetailsActivity.class);
        intent.putExtra("ListElement", townItem);
        startActivity(intent);
    }

}