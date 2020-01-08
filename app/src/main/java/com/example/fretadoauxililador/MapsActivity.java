package com.example.fretadoauxililador;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] permissoes = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ValidarPermissões
        Permissoes.validarPermissoes(permissoes, this, 1);


        //Setar Variáveis
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng from = new LatLng(-23.4960716, -47.4723136);
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(from);
        circleOptions.radius(2000);
        circleOptions.fillColor(Color.argb(128, 255, 153, 0));
        circleOptions.strokeWidth(10);
        circleOptions.strokeColor(Color.GREEN);
        mMap.addCircle(circleOptions);

        mMap.addMarker(new MarkerOptions()
                .position(from)
                .title("De")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house)));

        //Recuperar Localização Usuário
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                mMap.clear();
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
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(your,14));

                Log.d("Localização","onLocationChanged"+location);


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
