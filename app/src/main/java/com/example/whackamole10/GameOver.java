package com.example.whackamole10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity implements View.OnClickListener {
    String playerName;
    int score;
    Intent gameScreenIntent;
    Intent hsScreenIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        //get access to text views
        TextView message = (TextView)findViewById(R.id.tvMessage);
        TextView scoremes = (TextView)findViewById(R.id.tvGameOver);
        score = getIntent().getExtras().getInt("score");
        playerName = getIntent().getExtras().getString("name");
        message.setText("You hit "+ score +" times!");
        scoremes.setText("Game over, "+playerName+".");

        gameScreenIntent = new Intent(this,Game.class);
        hsScreenIntent = new Intent (this, HighScores.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonPlay) {
            startActivity(gameScreenIntent);
            finish(); //close the intent that we're in
        }
        else if(v.getId() == R.id.buttonScores){
            startActivity(hsScreenIntent);
            finish();
        }

    }
}