package com.usjt.android_gps;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaLocaActivity extends AppCompatActivity {

    private ListView LocalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_loca);
        LocalListView = findViewById(R.id.LocalListView);

        Intent origemIntent = getIntent();
        final List<Localizacao>
                lista = (List<Localizacao>) origemIntent.getSerializableExtra("LIST");

        ArrayAdapter<Localizacao> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        LocalListView.setAdapter(adapter);

        LocalListView.setOnItemClickListener(new
             AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int
                         position, long id) {
                     Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?q=restaurantes", lista.get(position).getLatitude(), lista.get(position).getLongitude()));
                     Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                     intent.setPackage("com.google.android.apps.maps");
                     startActivity(intent);
                 }
             });
    }
//    public List<Localizacao> s50(List<Localizacao> localizacaos){
//        List<Localizacao> aux
//            if (localizacaos.size() > 50){
//                while (aux.size() != 50 ){
//
//            }
//        return localizacaos;
//    }
}
