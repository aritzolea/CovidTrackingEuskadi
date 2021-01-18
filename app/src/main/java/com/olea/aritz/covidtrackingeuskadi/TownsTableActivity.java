package com.olea.aritz.covidtrackingeuskadi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class TownsTableActivity extends AppCompatActivity {

    private List<ListElement> towns;

    final String RED = "#FF6F6F";
    final String ORANGE = "#FFA86F";
    final String YELLOW = "#FFF66F";
    final String GREEN = "#6FFF6F";
    final String WHITE = "#FFFFFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_towns_table);

        init();
    }

    public void init() {
        towns = new ArrayList<>();
        towns.add(new ListElement(Color.parseColor(ORANGE), "Lazkao", "9999"));
        towns.add(new ListElement(Color.parseColor(GREEN), "Ataun", "430"));
        towns.add(new ListElement(Color.parseColor(GREEN), "Beasain", "430"));
        towns.add(new ListElement(Color.parseColor(GREEN), "Tolosa", "430"));

        ListAdapter listAdapter = new ListAdapter(towns, this, item -> {
            //TODO: BUG: Se puede hacer click rápido en dos municipios y se abren los dos
            moveToTownDetails(item);
        });

        RecyclerView recyclerView = findViewById(R.id.townList);

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