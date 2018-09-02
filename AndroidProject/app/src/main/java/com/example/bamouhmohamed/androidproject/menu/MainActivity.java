package com.example.bamouhmohamed.androidproject.menu;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bamouhmohamed.androidproject.R;
import com.example.bamouhmohamed.androidproject.beans.Game;
import com.example.bamouhmohamed.androidproject.database.Game_Database_Handler;
import com.example.bamouhmohamed.androidproject.parent.*;
import com.example.bamouhmohamed.androidproject.enfant.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static String CURRENT_LANG = "fr";

    ImageButton parentButton;
    ImageButton enfantButton;
    ImageButton fr;
    ImageButton ar;
    ImageButton en;
    ImageButton quitter;
    public static int nbOpreussies = 0;
    public static int nbOpechouees = 0;
    public static String id_accompagnant;
    public static String id_apprenant;

    SharedPreferences pref;
    MediaPlayer maintheme;
    public static Time HeureDebut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Locale locale = new Locale(pref.getString("lang_code",CURRENT_LANG));
        Locale.setDefault(locale);
        Configuration conf = getBaseContext().getResources().getConfiguration();
        conf.locale= locale;
        getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentButton = this.findViewById(R.id.parent);
        enfantButton = this.findViewById(R.id.enfant);
        fr = this.findViewById(R.id.fr);
        ar = this.findViewById(R.id.ar);
        en = this.findViewById(R.id.en);
        quitter = this.findViewById(R.id.quitter);
        parentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                parentClick(v);
            }
        });
        enfantButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enfantClick(v);
            }
        });
        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                langClick(view,"fr");
            }
        });
        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                langClick(view,"ar");
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                langClick(view,"en");
            }
        });
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitter();
            }
        });
        Intent intent = getIntent();

        id_accompagnant = intent.getStringExtra("id_accomp");
        id_apprenant = intent.getStringExtra("kid_id");
        Toast.makeText(this,"kid_id : "+id_apprenant,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"id_accomp : "+id_accompagnant,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy(){
        maintheme.stop();
        super.onDestroy();
    }

    @Override
    public void onPause(){
        maintheme.stop();
        super.onPause();
    }

    @Override
    public void onResume(){
        maintheme = MediaPlayer.create(this, R.raw.main_theme);
        maintheme.start();
        super.onResume();
    }

    @Override
    public void recreate()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }


    public void parentClick(View v){
        Intent intent= new Intent(MainActivity.this, ParentActivity.class);
        this.startActivity(intent);
    }
    public void enfantClick(View v){
        HeureDebut = new Time(System.currentTimeMillis());
        Intent intent= new Intent(MainActivity.this, EnfantActivity.class);
        this.startActivity(intent);
    }
    public void langClick(View v,String lang){
        SharedPreferences.Editor editor = pref.edit();
        CURRENT_LANG=lang;
        editor.putString("lang_code",CURRENT_LANG);
        recreate();
    }
    public void quitter(){
        finish();
        System.exit(0);
    }
}
