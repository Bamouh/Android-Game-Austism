package com.example.bamouhmohamed.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_auth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Toast.makeText(MainActivity.this,"kid_id : "+intent.getStringExtra("kid_id"), Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this,"id_accomp : "+intent.getStringExtra("id_accomp"), Toast.LENGTH_SHORT).show();


    }

}
