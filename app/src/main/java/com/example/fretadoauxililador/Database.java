package com.example.fretadoauxililador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import Models.Cadastro;

public class Database extends SQLiteOpenHelper {

    public static final String BANCO = "BD_FRETADO";
    public static final int VERSAO = 1;

    public Database(@Nullable Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE APP_FRETADO (_id integer primary key autoincrement, yourhome_lat double, yourhome_long double, yourjob_lat double,yourjob_long double, distancia double );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS APP_FRETADO");
        onCreate(db);
    }

    public void insereRegistros(Cadastro cadastro) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("yourhome_lat",cadastro.getYourhome_lat());
        values.put("yourhome_long",cadastro.getYourhome_long());
        values.put("yourjob_lat",cadastro.getYourjob_lat());
        values.put("yourjob_long",cadastro.getYourjob_long());
        values.put("distancia",cadastro.getDistancia());

        database.insert("APP_FRETADO",null,values);
        database.close();
    }

    public Cadastro recuperaRegistros(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("APP_FRETADO",new String[] {
                "_id",
                "yourhome_lat",
                "yourhome_long",
                "yourjob_lat",
                "yourjob_long",
                "distancia"
        },null,null,null,null,null);

        if(cursor.moveToFirst()) {
            do {
                Cadastro cadastro = new Cadastro();

                String id = cursor.getString(cursor.getColumnIndex("_id"));
                String yourhome_lat = cursor.getString(cursor.getColumnIndex("yourhome_lat"));
                String yourhome_long = cursor.getString(cursor.getColumnIndex("yourhome_long"));
                String yourjob_lat = cursor.getString(cursor.getColumnIndex("yourjob_lat"));
                String yourjob_long = cursor.getString(cursor.getColumnIndex("yourjob_long"));
                String distancia = cursor.getString(cursor.getColumnIndex("distancia"));

                cadastro.setYourhome_lat(Double.parseDouble(yourhome_lat));
                cadastro.setYourhome_long(Double.parseDouble(yourhome_long));
                cadastro.setYourjob_long(Double.parseDouble(yourjob_long));
                cadastro.setYourjob_lat(Double.parseDouble(yourjob_lat));
                cadastro.setDistancia(Double.parseDouble(distancia));

                return cadastro;
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return null;
    }

    public void excluirRegistros(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("APP_FRETADO", "_id", new String[]{id});
        database.close ();
    }
}
