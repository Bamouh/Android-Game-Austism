package com.example.bamouhmohamed.androidproject.parent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bamouhmohamed.androidproject.R;
import com.example.bamouhmohamed.androidproject.beans.Personne;
import com.example.bamouhmohamed.androidproject.database.PersonneBDD;

public class activity_add_personne extends AppCompatActivity {

    ImageButton button;
    ImageButton retourner;
    EditText Label;
    PersonneBDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personne);
        bdd=ParentActivity.bdd;
        button= this.findViewById(R.id.addButton);
        retourner= this.findViewById(R.id.retournerAdd);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                myClick2(v);
            }
        });
        retourner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void myClick2(View v){
        Label = this.findViewById(R.id.addLabel);
        Personne et = new Personne(Label.getText().toString());
        if((bdd.getPersonne(et.getLabel()))==null){
            bdd.insertPersonne(et);
            Toast.makeText(getApplicationContext() , et.getLabel() + " ajouté ", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext() , et.getLabel() + " existe dèja ", Toast.LENGTH_LONG).show();
        }
    }

}
