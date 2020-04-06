package com.example.fretadoauxililador;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import Models.Cadastro;

public class ConfiguracaoActivity extends AppCompatActivity {

    Button btnSalvar;
    EditText editSuaCasa, editSeuEmprego, editDistancia;
    Geocoder coderjob,coderhome;
    List<Address> addressjob,addresshome;
    Database database;
    Cadastro cadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        Toolbar toolbar = findViewById(R.id.toolbar);
        btnSalvar = findViewById(R.id.btnSalvar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(toolbar);
        coderjob = new Geocoder(this);
        coderhome = new Geocoder(this);
        cadastro = new Cadastro();
        database = new Database(this);

        editDistancia = findViewById(R.id.editDistancia);
        editSuaCasa = findViewById(R.id.editSuaCasa);
        editSeuEmprego = findViewById(R.id.editSeuTrabalho);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addressjob = coderjob.getFromLocationName(String.valueOf(editSeuEmprego.getText()),1);
                    addresshome = coderhome.getFromLocationName(String.valueOf(editSuaCasa.getText()),1);
                    if (addressjob.size() > 0 && addresshome.size() > 0){
                        double latitude = addressjob.get(addressjob.size() - 1).getLatitude();
                        double longitude = addressjob.get(addressjob.size() - 1).getLongitude();
                        double longitudehome = addresshome.get(addresshome.size() - 1).getLongitude();
                        double latitudehome = addresshome.get(addresshome.size() - 1).getLatitude();
                        cadastro.setYourjob_long(longitude);
                        cadastro.setYourjob_lat(latitude);
                        cadastro.setYourhome_long(longitudehome);
                        cadastro.setYourhome_lat(latitudehome);
                        cadastro.setDistancia(Double.valueOf(editDistancia.getText().toString()));
                        database.insereRegistros(cadastro);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selecsted
            case R.id.leave_menu:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
