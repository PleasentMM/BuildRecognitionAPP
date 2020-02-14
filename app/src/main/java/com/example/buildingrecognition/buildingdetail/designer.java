package com.example.buildingrecognition.buildingdetail;

import android.widget.ImageView;
import android.widget.TextView;
import com.example.buildingrecognition.R;
import com.example.buildingrecognition.model.Designer;
import com.example.buildingrecognition.utils.GlideUtils;
import com.example.library.BaseAdapter;
import com.example.library.IEntity;

public class designer implements IEntity<designer> {

    public designer(Designer designer) {
        this.designer = designer;
    }

    private Designer designer;

    @Override
    public int getLayoutId() {
        return R.layout.item_designer;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder, designer data, int position) {
        GlideUtils.GlideDesignerImg(holder.itemView.getContext(),designer.getUri(),(ImageView) holder.itemView.findViewById(R.id.designer_img));
        ((TextView)holder.itemView.findViewById(R.id.designer_name)).setText(designer.getName());
    }
}
