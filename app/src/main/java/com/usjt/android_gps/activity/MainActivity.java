package com.usjt.android_gps.activity;

import android.Manifest;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.usjt.android_gps.R;
import com.usjt.android_gps.model.Localizacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_GPS = 1001;

    private TextView locationTextView;

    Localizacao localizacao = new  Localizacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


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

                locationTextView.setText (String.format("Lat: %f,Lon %f",lat,lon));
               listaLocal.add(novaLoca);

                if(listaLocal.size() > 50){
                    Log.e("Size",">50");
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Camadno a  text view, tem q ser depois do setContextView
        locationTextView = findViewById(R.id.locationTextView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
//                Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?q=restaurantes",latitudeAtual,longitudeAtual));
//                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                intent.setPackage("com.google.android.apps.maps");
//                startActivity(intent);

                List<Localizacao> nomeFila = listaLocal;
                Intent intent = new Intent(MainActivity.this,ListaLocaActivity.class);
                intent.putExtra("LIST", (Serializable)nomeFila);
                startActivity(intent);
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
    protected void onStart(){
        super.onStart();
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED){

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    ,0,0,locationListener);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions,@NonNull int[] gantResults){
        if (requestCode == REQUEST_CODE_GPS){
            if (gantResults.length == 0 && gantResults[0] == PackageManager.PERMISSION_GRANTED ){
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED){

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }

            }else{
                Toast.makeText(this,getString(R.string.no_gps_no_app),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
}
