package com.example.buildingrecognition.buildingdetail;

import android.widget.TextView;
import com.example.buildingrecognition.R;
import com.example.buildingrecognition.model.Event;
import com.example.library.BaseAdapter;
import com.example.library.IEntity;

public class event implements IEntity<event> {

    public event(Event event) {
        this.event = event;
    }

    Event event;

    @Override
    public int getLayoutId() {
        return R.layout.item_event;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder, event data, int position) {
        ((TextView)holder.itemView.findViewById(R.id.startedAtTime)).setText(event.getStartedAtTime());
        ((TextView)holder.itemView.findViewById(R.id.description)).setText(event.getDescription());
    }
}
