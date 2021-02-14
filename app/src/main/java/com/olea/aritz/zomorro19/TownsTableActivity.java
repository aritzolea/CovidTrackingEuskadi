package com.olea.aritz.zomorro19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.SearchView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TownsTableActivity extends AppCompatActivity {

    private ListAdapter listAdapter;

    private RecyclerView recyclerView;

    private List<ListElement> towns;

    private TownsTableActivity instance;

    private SearchView searchView;

    private boolean clickable;

    final String FILENAME = "TOWN_FAVS";

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
                    List<String> favTowns = getFavTownCodes();

                    for (String townCode: favTowns) {
                        towns.add(getTownFromCode(townCode));
                    }

                    for (ListElement le : BackendData.towns) {
                        if (!towns.contains(le)) towns.add(le);
                    }
                } else {
                    for (ListElement le : BackendData.towns) {
                        if (le.getTown().toLowerCase().contains(newText.trim().toLowerCase()))
                            towns.add(le);
                    }
                }

                listAdapter = new ListAdapter(towns, instance, item -> {
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
        List<String> favTowns = getFavTownCodes();

        for (String townCode: favTowns) {
            towns.add(getTownFromCode(townCode));
        }

        for (ListElement le : BackendData.towns) {
            if (!towns.contains(le)) towns.add(le);
        }

        listAdapter = new ListAdapter(towns, this, item -> {
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

    public List<String> getFavTownCodes() {
        FileInputStream inputStream = null;

        List<String> ret = new ArrayList<>();

        try {
            inputStream = openFileInput(FILENAME);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            String text = bufferedReader.readLine();

            while (text != null) {
                ret.add(text);
                text = bufferedReader.readLine();
            }

            return ret;
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public ListElement getTownFromCode(String code) {
        for (ListElement listElement: BackendData.towns) {
            if (String.valueOf(listElement.getCode()).equals(code)) return listElement;
        }

        return null;
    }

}