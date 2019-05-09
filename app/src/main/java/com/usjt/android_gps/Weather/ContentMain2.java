package com.usjt.android_gps.Weather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.usjt.android_gps.R;
import com.usjt.android_gps.model.Localizacao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ContentMain2 extends AppCompatActivity {

    private RecyclerView weatherRecyclerView;
    private WeatherAdapter adapter;
    private List<Weather> previsoes;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main2);

        recyclerView = findViewById(R.id.chamadosRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent origemIntent = getIntent();
        final List<Localizacao>
                lista = (List<Localizacao>) origemIntent.getSerializableExtra("LIST");

        for (Localizacao localizacao : lista){
            obtemPrevisoesV3(localizacao.getLatitude(),localizacao.getLongitude());
        }

        weatherRecyclerView =
                findViewById(R.id.weatherRecyclerView);
        previsoes = new ArrayList<>();
        adapter = new WeatherAdapter(previsoes, this);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherRecyclerView.setAdapter(adapter);
    }
    public void obtemPrevisoesV3(double lat,double lon) {
        //veja uma expressão lambda implementando a interface Runnable...
        new Thread(() -> {
            try {
                @SuppressLint("StringFormatMatches") String endereco = getString(
                        R.string.web_service_url2,
                        lat,
                        lon,
                        getString(R.string.api_key)
                );
                URL url = new URL(endereco);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder resultado = new StringBuilder("");
                String aux = null;
                while ((aux = reader.readLine()) != null)
                    resultado.append(aux);
                runOnUiThread(() -> {
                    Toast.makeText(this, resultado.toString(),
                            Toast.LENGTH_SHORT).show();
                    lidaComJSON(resultado.toString());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void lidaComJSON(String resultado) {
        previsoes.clear();
        try {
            //JSONObject json = new JSONObject(resultado);
            JSONObject obj = new JSONObject(resultado);
            JSONObject main = obj.getJSONObject("main");
            JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);

//                        Double temp_min = obj.getDouble("temp_min");
//                        Double temp_max = obj.getDouble("temp_max");
//                        Double humidity = obj.getDouble("humidity");
//                        long dt = obj.getLong("dt");
//                        String icon = obj.getString("icon");
//                        String description = obj.getString("description");

            previsoes.add (new Weather(obj.getLong("dt"), main.getDouble("temp_min"),
                    main.getDouble("temp_max"), main.getDouble ("humidity"),
                    weather.getString("description"),weather.getString("icon")));
//            JSONArray list = json.getJSONArray("list");
//            for (int i = 0; i < list.length(); i++) {
//                JSONObject day = list.getJSONObject(i);
//                JSONObject main = day.getJSONObject("main");
//                JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
//                previsoes.add(new Weather(day.getLong("dt"), main.getDouble("temp_min"),
//                        main.getDouble("temp_max"), main.getDouble("humidity"),
//                        weather.getString("description"), weather.getString("icon")));
//            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
