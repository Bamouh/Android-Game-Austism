package com.example.bamouhmohamed.androidproject.parent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bamouhmohamed.androidproject.R;
import com.example.bamouhmohamed.androidproject.enfant.EnfantActivity;

public class activity_niveau extends AppCompatActivity {
    RadioGroup level;
    String selectionnedLevel;
    ImageButton retourner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau);
        level = findViewById(R.id.level);
        retourner = this.findViewById(R.id.retournerDel);
        retourner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        selectionnedLevel = ((RadioButton) findViewById(level.getCheckedRadioButtonId())).getText().toString().substring(7);//Niveau 1 --> 1
        EnfantActivity.LEVEL=Integer.parseInt(selectionnedLevel);//Nombre d'imageView associés a des personnes aléatoires
    }
}
