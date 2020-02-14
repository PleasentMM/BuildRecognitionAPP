package com.example.buildingrecognition.buildingdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buildingrecognition.R;
import com.example.buildingrecognition.model.Building;
import com.example.buildingrecognition.model.Designer;
import com.example.buildingrecognition.model.Event;
import com.example.library.BaseAdapter;
/**
 * @author  mcc
 * */
public class BuildingDetailActivity extends AppCompatActivity {

    //建筑信息
    private Building building;

    private BaseAdapter designerAdapter;
    private BaseAdapter eventAdapter;
    private RecyclerView designerRecyclerView;
    private RecyclerView eventRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R. layout.activity_building_detail);

        setupBuildingDetail();
    }

    private void setupBuildingDetail() {
        setTitle("建筑详情");
        Intent intent = getIntent();
        building = (Building) intent.getSerializableExtra("Building");

        designerAdapter = new BaseAdapter.Builder().build();
        eventAdapter = new BaseAdapter.Builder().build();

        designerRecyclerView = findViewById(R.id.detail_rv);
        eventRecyclerView = findViewById(R.id.event_rv);

        designerRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        designerRecyclerView.setAdapter(designerAdapter);

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        eventRecyclerView.setAdapter(eventAdapter);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupInfo();
    }

    private void setupInfo() {
        ((TextView)findViewById(R.id.year)).setText(building.getCreated());
        ((TextView)findViewById(R.id.name)).setText(building.getName());
        ((TextView)findViewById(R.id.style)).setText(building.getArchitecturalStyle());
        ((TextView)findViewById(R.id.architecturalStyle)).append(building.getArchitecturalStyle());
        ((TextView)findViewById(R.id.address)).append(building.getAddress());
        ((TextView)findViewById(R.id.houseNumber)).append(building.getHouseNumber());
        ((TextView)findViewById(R.id.description)).setText(building.getDescription());
        if (building.getRelation() != null ) {
            if (building.getRelation().size() != 0) {
                for (Designer designer:building.getRelation()) {
                    designerAdapter.add(new designer(designer));
                }
            }
        }
        if (building.getEvent() != null) {
            if (building.getEvent().size() != 0) {
                for (Event event:building.getEvent()) {
                    eventAdapter.add(new event(event));
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
