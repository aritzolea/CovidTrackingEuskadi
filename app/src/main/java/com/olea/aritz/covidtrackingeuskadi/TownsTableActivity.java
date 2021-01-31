package com.olea.aritz.covidtrackingeuskadi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

    private TownsTableActivity instance;

    private SearchView searchView;

    private boolean clickable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_towns_table);

        searchView = findViewById(R.id.searchView);

        instance = this;
        clickable = true;

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
                    if (clickable)
                        moveToTownDetails(item);
                });

                recyclerView.setLayoutManager(new LinearLayoutManager(instance));
                recyclerView.setAdapter(listAdapter);

                return true;
            }
        });

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clickable = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        clickable = true;
    }

    public void init() {

        towns = new ArrayList<>();
        for (ListElement le : BackendData.towns) {
            towns.add(le);
        }

        listAdapter = new ListAdapter(towns, this, item -> {
            //TODO: BUG: Se puede hacer click rápido en dos municipios y se abren los dos
            if (clickable)
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
        clickable = false;
    }

}
