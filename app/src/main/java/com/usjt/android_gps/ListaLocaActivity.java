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

        ArrayAdapterLocal local =
                new ArrayAdapterLocal(this,lista);
        LocalListView.setAdapter(local);

        LocalListView.setOnItemClickListener(new
             AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int
                         position, long id) {

                     Localizacao localizacao = lista.get(position);
                     Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?q=restaurantes", localizacao.getLatitude(), localizacao.getLongitude()));
                     Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                     intent.setPackage("com.google.android.apps.maps");
                     startActivity(intent);
                 }
             });
    }

}
