package com.example.whackamole10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HighScores extends AppCompatActivity implements View.OnClickListener{
    Intent playsScreenIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        playsScreenIntent = new Intent(this,Game.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.hsplaybtn){
            startActivity(playsScreenIntent);
            finish();
        }
        else if (v.getId() ==  R.id.quitbtn){
            finish();
        }

    }
}