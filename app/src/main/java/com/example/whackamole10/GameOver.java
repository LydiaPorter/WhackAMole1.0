package com.example.whackamole10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

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

        saveDataToIF();
    }

    private void saveDataToIF() {
         try{
             FileOutputStream fos = openFileOutput("HighScores.txt",MODE_APPEND); //append: you can add to file
             writeToFOS(fos);
         }
         catch(Exception e) {
             CharSequence text = "The file could not be opened. "+e.toString();
             int dur = Toast.LENGTH_LONG;
             Toast message = Toast.makeText( this,text,dur);
             message.show();
         }
    }

    public void writeToFOS(FileOutputStream fos){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            String endline = System.getProperty("line.separator");
            osw.write(playerName+endline);
            osw.write(score+endline);
            osw.flush(); //make sure stream is  empty and done.
            osw.close(); //!!!!!
        }
        catch(Exception e){
            CharSequence text = "Could not write to file. "+e.toString();
            int dur = Toast.LENGTH_LONG;
            Toast message = Toast.makeText( this,text,dur);
            message.show();
        }
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