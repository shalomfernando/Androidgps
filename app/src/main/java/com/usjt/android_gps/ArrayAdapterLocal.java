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


        ViewHolder viewHolder = null;
        if (convertView == null){
            Context context = getContext();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent,
                    false);

            viewHolder = new ViewHolder();
            viewHolder.Latitude = convertView.findViewById(R.id.LatitudeTextView);
            viewHolder.Longitude = convertView.findViewById(R.id.LongitudeTextView);
        }

        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.Latitude.setText(localAtual.toString());
        viewHolder.Longitude.setText(localAtual.toString2());

        return convertView;
    }
    private class ViewHolder{
        public TextView Latitude;
        public TextView Longitude;
    }
}
