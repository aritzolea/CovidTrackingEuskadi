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

    List<ListElement> towns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_towns_table);

        init();
    }

    public void init() {
        towns = new ArrayList<>();
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain1", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain2", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain3", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain4", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain5", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain6", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain7", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain8", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain9", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain10", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain11", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain12", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain13", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain14", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain15", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain16", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain17", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain18", "700"));
        towns.add(new ListElement(Color.parseColor("#FFA7A7"), "Beasain19", "700"));

        ListAdapter listAdapter = new ListAdapter(towns, this, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {
                moveToTownDetails(item);
            }
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