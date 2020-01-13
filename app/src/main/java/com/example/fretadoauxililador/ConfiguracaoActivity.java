package com.example.fretadoauxililador;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import Models.Cadastro;

public class ConfiguracaoActivity extends AppCompatActivity {

    Button btnSalvar;
    String youWork,youHouse;
    EditText editSuaCasa,editSeuTrabalho,editDistancia;
    Database meu_banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        //Inicializar Variáveis
        btnSalvar = findViewById(R.id.btnSalvar);
        editSeuTrabalho = findViewById(R.id.editSeuTrabalho);
        editSuaCasa = findViewById(R.id.editSuaCasa);
        editDistancia = findViewById(R.id.editDistancia);
        meu_banco = new Database(this);
        

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cadastro cadastro = new Cadastro();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


                try{
                    String youHome = editSuaCasa.getText().toString();
                    List<Address> listaEnderecos = geocoder.getFromLocationName(youHome,1);
                    if(listaEnderecos != null && listaEnderecos.size() > 0){
                        Address endereco = listaEnderecos.get(0);
                        cadastro.setYourhome_lat(endereco.getLatitude());
                        cadastro.setYourhome_long(endereco.getLongitude());
                    }
                }catch(IOException e){
                    Log.i("Erro", e.getMessage());
                }

                try{
                    String yourWork = editSeuTrabalho.getText().toString();
                    List<Address> listaEnderecos = geocoder.getFromLocationName(yourWork,1);
                    if(listaEnderecos != null && listaEnderecos.size() > 0){
                        Address endereco = listaEnderecos.get(0);
                        cadastro.setYourjob_lat(endereco.getLatitude());
                        cadastro.setYourjob_long(endereco.getLongitude());
                    }
                }catch(IOException e) {
                }
                cadastro.setDistancia(Double.parseDouble(editDistancia.getText().toString()));
                meu_banco.insereRegistros(cadastro);

                Toast.makeText(getApplicationContext(),"Registro salvo com sucesso!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            case R.id.leave_menu:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
