package com.example.buildingrecognition.buildinglist;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.example.buildingrecognition.R;
import com.example.buildingrecognition.buildingdetail.BuildingDetailActivity;
import com.example.buildingrecognition.model.Building;
import com.example.library.BaseAdapter;
import com.example.library.IEntity;

public class buildingList implements IEntity<buildingList> {

    public buildingList(Building building) {
        this.building = building;
    }

    Building building;

    @Override
    public int getLayoutId() {
        return R.layout.item_building;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, final BaseAdapter.ViewHolder holder, buildingList data, int position) {
        ((TextView)holder.itemView.findViewById(R.id.year)).setText(building.getCreated());
        ((TextView)holder.itemView.findViewById(R.id.name)).setText(building.getName());
        ((TextView)holder.itemView.findViewById(R.id.description)).setText(building.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), BuildingDetailActivity.class);
                intent.putExtra("Building",building);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
}
