package com.usjt.android_gps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterLocal extends ArrayAdapter<Localizacao> {
    public ArrayAdapterLocal (Context context, List<Localizacao> localizacaoList){
        super (context, -1, localizacaoList);

    }

    @Override
    public View getView (int position,View convertView, ViewGroup parent){
        Localizacao localAtual = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_item, parent,
                    false);
        }

        TextView Latitude = convertView.findViewById(R.id.LatitudeTextView);
        TextView Longitude = convertView.findViewById(R.id.LongitudeTextView);

        Latitude.setText(localAtual.toString());
        Longitude.setText(localAtual.toString2());

        return convertView;
    }
}
