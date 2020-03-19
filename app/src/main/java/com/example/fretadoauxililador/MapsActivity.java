package com.example.fretadoauxililador;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import Models.Cadastro;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] permissoes = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Cadastro cadastro;
    private Database meu_banco;
    private MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadastro = new Cadastro();

        //ValidarPermissões
        Permissoes.validarPermissoes(permissoes, this, 1);


        //Setar Variáveis
        meu_banco = new Database(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ConfiguracaoActivity.class));
            }
        });

    }

    public void play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.sound);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    play();
                }
            });
        }

        player.start();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Recuperar Localização Usuário
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //cadastro = meu_banco.recuperaRegistros();
                cadastro.setDistancia(0.02);
                cadastro.setYourhome_lat(-23.4910951);
                cadastro.setYourhome_long(-47.4628921);
                mMap.clear();

                double lat = cadastro.getYourhome_lat()-cadastro.getDistancia();
                double log = cadastro.getYourhome_long()-cadastro.getDistancia();
                double latb = cadastro.getYourhome_lat()+cadastro.getDistancia();
                double logb = cadastro.getYourhome_long()+cadastro.getDistancia();

                LatLng from = new LatLng(cadastro.getYourhome_lat(), cadastro.getYourhome_long());
                CircleOptions circleOptionsHouse = new CircleOptions();
                circleOptionsHouse.center(from);
                circleOptionsHouse.radius(cadastro.getDistancia()*1000);
                circleOptionsHouse.fillColor(Color.argb(128, 255, 153, 0));
                circleOptionsHouse.strokeWidth(10);
                circleOptionsHouse.strokeColor(Color.YELLOW);
                mMap.addCircle(circleOptionsHouse);

                mMap.addMarker(new MarkerOptions()
                        .position(from)
                        .title("Casa")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house)));

/*
                LatLng to = new LatLng(cadastro.getYourjob_lat(), cadastro.getYourjob_long());
                CircleOptions circleOptionsJob = new CircleOptions();
                circleOptionsJob.center(to);
                circleOptionsJob.radius(2000);
                circleOptionsJob.fillColor(Color.argb(128, 255, 153, 0));
                circleOptionsJob.strokeWidth(10);
                circleOptionsJob.strokeColor(Color.RED);
                mMap.addCircle(circleOptionsJob);

                mMap.addMarker(new MarkerOptions()
                        .position(to)
                        .title("Work")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house)));
*/


                LatLng your = new LatLng( latitude,longitude);
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(your);
                circleOptions.radius(2000);
                circleOptions.fillColor(Color.argb(128, 65, 85, 90));
                circleOptions.strokeWidth(1);
                circleOptions.strokeColor(Color.BLUE);
                mMap.addCircle(circleOptions);
                mMap.addMarker(new MarkerOptions().position(your).title("Sua posição")
                        .icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.icon_you)));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(your,12));
                //double distance = SphericalUtil.computeDistanceBetween(your, from);
                Log.d("Test", "Lat"+your.latitude);
                Log.d("Test", "L"+your.longitude);
                Log.d("LongBet", "BET"+log+","+logb);
                Log.d("LagBet", "BET"+lat+","+latb);

                if(lat < your.latitude && latb > your.latitude){
                    if(log < your.longitude && logb > your.longitude) {
                        Log.d("Entrou", "entrou");
                        play();
                    }
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

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    locationListener);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResult : grantResults) {
            if (permissaoResult == PackageManager.PERMISSION_DENIED) {
                //Alerta
                alertaValidacao();
            } else if (permissaoResult == PackageManager.PERMISSION_GRANTED) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0,
                            locationListener


                    );
                    return;
                }
            }
        }
    }

    private void alertaValidacao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o APP, é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
