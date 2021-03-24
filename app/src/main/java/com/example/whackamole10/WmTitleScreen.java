package com.example.whackamole10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WmTitleScreen extends AppCompatActivity implements View.OnClickListener{
    Intent playIntent;
    Intent optionIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wm_title_screen);

        playIntent = new Intent(this,Game.class);
        optionIntent = new Intent(this, Options.class);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.startGame){
            startActivity(playIntent);
            finish();
        }
        else if (v.getId() ==  R.id.startOptions){
            startActivity(optionIntent);
            finish();
        }
    }
}