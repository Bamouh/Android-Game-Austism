package com.example.bamouhmohamed.androidproject.parent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bamouhmohamed.androidproject.R;
import com.example.bamouhmohamed.androidproject.database.PersonneBDD;

public class activity_remove_personne extends AppCompatActivity {
    ImageButton button;
    ImageButton retourner;
    EditText Label;
    PersonneBDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_personne);
        bdd=ParentActivity.bdd;
        button= this.findViewById(R.id.remove);
        retourner= this.findViewById(R.id.retournerDel);
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
        Label = this.findViewById(R.id.removeLabel);
        if((bdd.getPersonne(Label.getText().toString()))!=null){//Regarde si une personne avec ce label existe dèja
            bdd.removePersonne(Label.getText().toString());
            Toast.makeText(getApplicationContext() , Label.getText().toString() + " supprimé ", Toast.LENGTH_LONG).show();
        }
    }
}
