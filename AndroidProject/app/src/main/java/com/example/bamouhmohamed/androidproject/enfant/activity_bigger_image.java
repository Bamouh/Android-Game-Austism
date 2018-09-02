package com.example.bamouhmohamed.androidproject.enfant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.bamouhmohamed.androidproject.R;

public class activity_bigger_image extends AppCompatActivity {
    ImageView biggerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigger_image);
        biggerImage = this.findViewById(R.id.biggerImage);
        if(getIntent().hasExtra("byteArray")) {//Recupère l'image passée par l'activité EnfantActivity via l'intent
            Bitmap _bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            //Convertit l'image d'un array de bits en Bitmap
            biggerImage.setImageBitmap(_bitmap);
        }
    }
}
