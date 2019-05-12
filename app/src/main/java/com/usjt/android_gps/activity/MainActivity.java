package com.usjt.android_gps.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.usjt.android_gps.R;
import com.usjt.android_gps.Weather.ContentMain2;
import com.usjt.android_gps.Weather.Weather;
import com.usjt.android_gps.Weather.WeatherAdapter;
import com.usjt.android_gps.model.Localizacao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_GPS = 1001;

    private RecyclerView weatherRecyclerView;
    private WeatherAdapter adapter;
    private List<Weather> previsoes;
    private RequestQueue requestQueue;


    private TextView locationTextView;

    Localizacao localizacao = new Localizacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//         Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);

        // intanciando a localizaçã pelo manager location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final List<Localizacao> listaLocal = new ArrayList<>();


//        precisa atualizar a localização com o localização listener
//        como se trata de uma interface precisa sobrepor os methodos
//        segue listagem a baixo
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Localizacao novaLoca = new Localizacao();
                int i = 1;
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                novaLoca.setId(i);
                novaLoca.setLatitude(lat);
                novaLoca.setLongitude(lon);
                i++;

                locationTextView.setText(String.format("Lat: %f,Lon %f", lat, lon));
                listaLocal.add(novaLoca);

                if (listaLocal.size() > 50) {
                    Log.e("Size", ">50");
                    listaLocal.remove(0);
                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };

        // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        weatherRecyclerView =
                findViewById(R.id.weatherRecyclerView);
        previsoes = new ArrayList<>();
        previsoes.add(new Weather(500, 37, 38, 0.7, "teste 1", ""));
        //adapter = new WeatherAdapter(previsoes, this);

        // Camadno a  text view, tem q ser depois do setContextView
        locationTextView = findViewById(R.id.locationTextView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?q=restaurantes",latitudeAtual,longitudeAtual));
//                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                intent.setPackage("com.google.android.apps.maps");
//                startActivity(intent);
                //obtemPrevisoesV3(-23.5506507, -46.6333824);
                List<Localizacao> nomeFila = listaLocal;
                for (Localizacao localizacao : nomeFila){
                    double lat = localizacao.getLatitude();
                    double lon = localizacao.getLongitude();
                    //obtemPrevisoesV3(-23.5506507,-46.6333824);
                     obtemPrevisoesV3(lat,lon);
                }
                // Intent intent = new Intent(MainActivity.this, ContentMain2.class);

                Intent intent2 = new Intent(MainActivity.this, ContentMain2.class);
                intent2.putExtra("LIST", (Serializable) previsoes);
                startActivity(intent2);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , 0, 0, locationListener);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] gantResults) {
        if (requestCode == REQUEST_CODE_GPS) {
            if (gantResults.length == 0 && gantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }

            } else {
                Toast.makeText(this, getString(R.string.no_gps_no_app), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    public void lidaComJSON(String resultado) {
        previsoes.clear();
        try {
            JSONObject obj = new JSONObject(resultado);
            JSONObject main = obj.getJSONObject("main");
            JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);

            previsoes.add(new Weather(obj.getLong("dt"), main.getDouble("temp_min"),
                    main.getDouble("temp_max"), main.getDouble("humidity"),
                    weather.getString("description"), weather.getString("icon")));
            adapter.notifyDataSetChanged();
            dismissKeyboard(weatherRecyclerView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void obtemPrevisoesV3(double lat, double lon) {
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

    public void obtemPrevisoesV31(double lat, double lon) {
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

    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
