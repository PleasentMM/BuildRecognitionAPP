package com.example.buildingrecognition.utils;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.buildingrecognition.R;

public class GlideUtils {

    public static void GlideDesignerImg(Context context, String url, ImageView view) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_ren)
                .centerCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(view);
    }
}
