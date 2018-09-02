package com.example.bamouhmohamed.androidproject.parent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.example.bamouhmohamed.androidproject.R;
import com.example.bamouhmohamed.androidproject.enfant.EnfantActivity;

public class BallActivity extends AppCompatActivity {

    RadioGroup radio;
    int selectionnedBall;
    ImageButton retourner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ball);
        radio = this.findViewById(R.id.ballSelection);
        retourner = this.findViewById(R.id.retournerBall);
        retourner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View v) {
        switch(v.getId()){
            case R.id.radio0 :  selectionnedBall=R.drawable.ball;break;
            case R.id.radio1 :  selectionnedBall=R.drawable.ball2;break;
            case R.id.radio2 :  selectionnedBall=R.drawable.ball3;break;
            case R.id.radio3 :  selectionnedBall=R.drawable.ball4;break;
            case R.id.radio4 :  selectionnedBall=R.drawable.ball5;break;
        }
        EnfantActivity.BALL=selectionnedBall;
    }
}
