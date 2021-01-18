package com.olea.aritz.covidtrackingeuskadi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class TownsTableActivity extends AppCompatActivity {

    private ListAdapter listAdapter;

    private RecyclerView recyclerView;

    private List<ListElement> towns;
    private List<ListElement> townsOriginal;

    private TownsTableActivity instance;

    final String RED = "#FF6F6F";
    final String ORANGE = "#FFA86F";
    final String YELLOW = "#FFF66F";
    final String GREEN = "#6FFF6F";
    final String WHITE = "#FFFFFF";

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
                if (TextUtils.isEmpty(newText)){
                    for (ListElement le: townsOriginal)
                        towns.add(le);
                } else {
                    for (ListElement le: townsOriginal) {
                        if (le.getTown().toLowerCase().startsWith(newText.trim().toLowerCase()))
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
        townsOriginal = new ArrayList<>();
        townsOriginal.add(new ListElement(Color.parseColor(RED), "Lazkao", "9999"));
        townsOriginal.add(new ListElement(Color.parseColor(WHITE), "Ataun", "430"));
        townsOriginal.add(new ListElement(Color.parseColor(ORANGE), "Beasain", "430"));
        townsOriginal.add(new ListElement(Color.parseColor(YELLOW), "Donostia", "230"));
        townsOriginal.add(new ListElement(Color.parseColor(GREEN), "Tolosa", "99"));

        towns = new ArrayList<>();
        for (ListElement le: townsOriginal) {
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
        Intent intent = new Intent(this, TownDetailsActivity.class);
        intent.putExtra("ListElement", townItem);
        startActivity(intent);
    }

}