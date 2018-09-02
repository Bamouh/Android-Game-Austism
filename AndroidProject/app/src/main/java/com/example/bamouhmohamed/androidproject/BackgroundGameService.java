package com.example.bamouhmohamed.androidproject;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by younes on 08/05/2018.
 */

public class BackgroundGameService extends IntentService {
    private String id_application;
    private String id_apprenant;
    private String id_accompagnant;
    private String id_exercice;
    private String id_niveau;
    private String date_actuelle;
    private String heure_debut;
    private String heure_fin;
    private String Nombre_operation_reuss;
    private String Nombre_operation_echou;
    private String minimum_temps_operation_sec;
    private String moyen_temps_operation_sec;
    private String longitude;
    private String latitude;
    private String device;
    private String flag;
    SQLiteDatabase db;
    GameInfo gameInfo;
    // Must create a default constructor
    public BackgroundGameService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println(SaveSharedPreference.getSharedPref(BackgroundGameService.this,"Main_Server"));
        System.out.print("Intent started");
        db = this.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("select * from Game_Infos",null);
        GameInfos gameInfoList = new GameInfos();
        gameInfo = null;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
//                private String id_application;
//                private String id_apprenant;
//                private String id_accompagnant;
//                private String id_exercice;
//                private String id_niveau;
//                private Date date_actuelle;
//                private Time heure_debut;
//                private Time heure_fin;
//                private int Nombre_operation_reuss;
//                private int Nombre_operation_echou;
//                private int minimum_temps_operation_sec;
//                private int moyen_temps_operation_sec;
//                private double longitude;
//                private double latitude;
//                private String device;
//                private boolean flag;
                id_application = cursor.getString(cursor.getColumnIndex("id_application"));
                id_apprenant = cursor.getString(cursor.getColumnIndex("id_apprenant"));
                id_accompagnant = cursor.getString(cursor.getColumnIndex("id_accompagnant"));
                id_exercice = cursor.getString(cursor.getColumnIndex("id_exercice"));
                id_niveau = cursor.getString(cursor.getColumnIndex("id_niveau"));
                date_actuelle = cursor.getString(cursor.getColumnIndex("date_actuelle"));
                heure_debut = cursor.getString(cursor.getColumnIndex("heure_debut"));
                heure_fin = cursor.getString(cursor.getColumnIndex("heure_fin"));
                Nombre_operation_reuss = Integer.toString(cursor.getInt(cursor.getColumnIndex("Nombre_operation_reuss")));
                Nombre_operation_echou = Integer.toString(cursor.getInt(cursor.getColumnIndex("Nombre_operation_echou")));
                minimum_temps_operation_sec = Integer.toString(cursor.getInt(cursor.getColumnIndex("minimum_temps_operation_sec")));
                moyen_temps_operation_sec =   Integer.toString(cursor.getInt(cursor.getColumnIndex("moyen_temps_operation_sec")));
                longitude = Double.toString(cursor.getDouble(cursor.getColumnIndex("longitude")));
                latitude =  Double.toString(cursor.getDouble(cursor.getColumnIndex("latitude")));
                device = cursor.getString(cursor.getColumnIndex("device"));
                flag = cursor.getString(cursor.getColumnIndex("flag"));
//                String name = cursor.getString(cursor.getColumnIndex(countyname));
                gameInfo = new GameInfo(id_application,id_apprenant,id_accompagnant,id_exercice,id_niveau,date_actuelle,heure_debut,heure_fin,
                        Nombre_operation_reuss,Nombre_operation_echou,minimum_temps_operation_sec,moyen_temps_operation_sec,longitude,latitude,
                        device,flag);
                //list.add(name);

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(SaveSharedPreference.getSharedPref(BackgroundGameService.this,"Main_Server"))
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();

                RetrofitConnections client = retrofit.create(RetrofitConnections.class);
                Call<GameInfoResponse> call = client.sendGameInfo(gameInfo,"Bearer " + SaveSharedPreference.getAccessToken(BackgroundGameService.this));
                call.enqueue(new Callback<GameInfoResponse>() {
                    @Override
                    public void onResponse(Call<GameInfoResponse> call, Response<GameInfoResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<GameInfoResponse> call, Throwable t) {

                    }
                });
                db.execSQL("DELETE FROM game_infos Where date_actuelle = '"+gameInfo.updated_at+"' AND heure_debut = '"+gameInfo.created_at+"'");





                cursor.moveToNext();
            }


        }
        db.close();
    }

}