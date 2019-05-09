package com.usjt.android_gps.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usjt.android_gps.R;
import com.usjt.android_gps.model.Localizacao;

import java.util.List;

public class LocalRecyclerViewAdapter extends RecyclerView.Adapter<LocalViewHolder> {

    private List<Localizacao> localizacaoList;

    public LocalRecyclerViewAdapter(List<Localizacao> localizacaoList) {
        this.localizacaoList = localizacaoList;
    }

    @Override
    public int getItemCount() {
        return localizacaoList.size();
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View raiz =
                LayoutInflater.from(viewGroup.getContext()).
                        inflate(R.layout.list_item, viewGroup, false);
        return new LocalViewHolder(raiz);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder vh, int i) {
        Localizacao localizacao = localizacaoList.get(i);
        vh.LatitudeTextView.setText(localizacao.toString());
        vh.LongitudeTextView.setText(localizacao.toString2());

    }


}

