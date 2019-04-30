package com.usjt.android_gps;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LocalViewHolder extends  RecyclerView.ViewHolder  {

    public TextView LatitudeTextView;
    public TextView LongitudeTextView;
    public LocalViewHolder( View itemView) {
        super(itemView);

        LatitudeTextView = itemView.findViewById(R.id.LatitudeTextView);
        LongitudeTextView = itemView.findViewById(R.id.LongitudeTextView);

    }


}
