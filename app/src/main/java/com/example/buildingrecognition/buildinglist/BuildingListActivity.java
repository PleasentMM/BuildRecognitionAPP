package com.example.buildingrecognition.buildinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buildingrecognition.R;
import com.example.buildingrecognition.model.Building;
import com.example.buildingrecognition.model.Recognition;
import com.example.library.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class BuildingListActivity extends AppCompatActivity {

    private Recognition recognition;
    private BaseAdapter baseAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_list);

        setupBuildingList();
    }

    private void setupBuildingList() {
        setTitle("建筑列表");
        Intent intent = getIntent();
        recognition = (Recognition) intent.getSerializableExtra("BuildingList");

        baseAdapter = new BaseAdapter.Builder().build();
        recyclerView = findViewById(R.id.list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(baseAdapter);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupInfo();
    }

    private void setupInfo() {
        List<buildingList> buildingLists = new ArrayList<>();
        for (Building building:recognition.getBuildings()) {
            buildingLists.add(new buildingList((building)));
        }
        baseAdapter.add(buildingLists);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
