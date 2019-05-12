package com.usjt.android_gps.Weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.android_gps.R;
import com.usjt.android_gps.activity.MainActivity;
import com.usjt.android_gps.model.Localizacao;

import org.json.JSONArray;
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
    private RequestQueue requestQueue;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_GPS = 1001;


    //  private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main2);
        requestQueue = Volley.newRequestQueue(this);



        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent origemIntent = getIntent();
//        final List<Localizacao>
//                lista = (List<Localizacao>) origemIntent.getSerializableExtra("LIST");
        final List<Weather>
                previsoes1 = (List<Weather>) origemIntent.getSerializableExtra("LIST");


        weatherRecyclerView =
                findViewById(R.id.weatherRecyclerView);
        previsoes = previsoes1;
        previsoes.add(new Weather(500, 37, 38, 0.7, "teste 1", ""));
        adapter = new WeatherAdapter(previsoes, this);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherRecyclerView.setAdapter(adapter);

//        for (Localizacao localizacao : lista){
//            obtemPrevisoesV3(localizacao.getLatitude(),localizacao.getLongitude());
//        }
       // obtemPrevisoesV3(-23.5506507,-46.6333824);
    }
    public void obtemPrevisoesV1(double lat,double log) {
        try {
            @SuppressLint("StringFormatMatches") String endereco = getString(
                    R.string.web_service_url,
                    lat,
                    log,
                    getString(R.string.api_key)
            );
            URL url = new URL(endereco);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder resultado = new StringBuilder("");
            //vamos tratar o resultado aqui...
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
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
    public void obtemPrevisoesV4(double lat,double lon) {
        //veja uma expressão lambda implementando a interface Runnable...
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

    public void obtemPrevisoesV5 (String cidade){
        String url = getString(
                R.string.web_service_url,
                cidade,
                getString(R.string.api_key)
        );
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (response)-> {
                    previsoes.clear();
                    try {
                        JSONArray list = response.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject day = list.getJSONObject(i);
                            JSONObject main = day.getJSONObject("main");
                            JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                            previsoes.add(new Weather(day.getLong("dt"), main.getDouble("temp_min"),
                                    main.getDouble("temp_max"), main.getDouble("humidity"),
                                    weather.getString("description"), weather.getString("icon")));
                        }
                        adapter.notifyDataSetChanged();
                        dismissKeyboard(weatherRecyclerView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (error)->{
                    Toast.makeText(
                            ContentMain2    .this,
                            getString(R.string.connect_error) + ": " + error.getLocalizedMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
        requestQueue.add(req);
    }

    private void dismissKeyboard (View view){
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


}
