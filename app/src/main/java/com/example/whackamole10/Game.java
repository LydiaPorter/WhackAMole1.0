package com.example.whackamole10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class Game extends AppCompatActivity implements View.OnClickListener{
    //this is the new, updated, and working game!!!

    //set up an array of integers to hold the button ids of the "moles"
    ArrayList<Integer> myButtonIDs = new ArrayList<>();
    //the handler will be used to run a timer in our game
    protected Handler taskHandler = new Handler();
    //the isComplete variable will tll us when time is up!
    protected Boolean isComplete = false;
    ImageButton currentMole;
    //use current time for start time for the game
    long startTime= System.currentTimeMillis();
    //keep track of how many times the user has hit the mole
    int score = 0;
    //settings
    String playerName = "Default";
    int difficultyLevel = 2; //which number is easiest and which is hardest
    // 1 hard, 2 medium, 3 easy bc the difficulty is multiplied by milliseconds per mole.
    // the more time you have the easier it is.
    int numMoles = 8; // any value between 3 and 8
    int duration = 20; //any value up to 30 secs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
/*
        Bundle bun = getIntent().getExtras();
        playerName = bun.getString("name");
        difficultyLevel = bun.getInt("difficulty");
        numMoles = bun.getInt("numMoles");
        duration = bun.getInt("duration");
*/
///////////////////////////////////////////
        SharedPreferences prefs = getSharedPreferences("WhackSettings",MODE_PRIVATE); //get access to pref file
        //private: can load but not edit it
        //get values from sharedpreferences
        playerName = prefs.getString("name","Default"); //assign player name. assign to Default if empty.
       difficultyLevel = prefs.getInt("difficulty",1);
       numMoles = prefs.getInt("numMoles", 8);
       duration = prefs.getInt("duration",20);

        initButtons();
        setNewMole();
        setTimer(difficultyLevel * 1000); //start timer //milliseconds
    }

    @Override
    public void onClick(View v) {
        if(isComplete){
            return;
        }
        //only increase score if what you clicked was the current mole
        if (v == currentMole){
            score++;
            TextView tvscore = (TextView)findViewById(R.id.score);
            tvscore.setText("Score: "+score);
            setNewMole(); //now get a new mole and make this one invisible!
        }
    }
    //this method is called when the game is completed
    public void gameOver() {
        //takes us to new screen
        isComplete = true; //the game is over.
        TextView tvscore = (TextView)findViewById(R.id.score);
        tvscore.setText("Game Over! \n Score: "+score);

        Intent gameoverintent = new Intent(this,GameOver.class);
        gameoverintent.putExtra("score",score);
        gameoverintent.putExtra("name",playerName);
        startActivity(gameoverintent);
        finish(); //end current activity and start new activity with our intent

    }
    //this method will choose a new button as the current mole
    //this method is provided complete as part of the activity starter.
    public void setNewMole() {
        Random generator = new Random(); //create random number generator

        int randomItem = generator.nextInt(numMoles); //pass in number of moles; depends on difficulty level

        int newButtonId = myButtonIDs.get(randomItem);
        if(currentMole != null){
            currentMole.setVisibility(View.INVISIBLE);
        }
        ImageButton newMole = (ImageButton)findViewById(newButtonId);
        newMole.setVisibility(View.VISIBLE);
        currentMole = newMole;

    }

    public void initButtons(){
        ViewGroup group = (ViewGroup)findViewById(R.id.GameLayout);
        View v;
        //can't see the buttons even though they're still there
        //now we can loop through all the controls
        for(int i = 0; i < group.getChildCount(); i ++){
            v = group.getChildAt(i);
            if (v instanceof ImageButton){
                v.setOnClickListener(this);
                if(!isComplete){//if game is not over/still going
                    myButtonIDs.add(v.getId()); //add the button ID to the array
                    v.setVisibility(View.INVISIBLE); //set button to invisible
                }
            }
        }
    }
    //create timer that will allow us to switch current moles
    protected void setTimer(long time) {
        //get the time that we want our timer to last from the input parameter
        final long elapse = time;
        Runnable t = new Runnable(){ //runnable ends with ;
            @Override
            public void run() {
                onTimerTask();
                if(!isComplete){
                    //create new timer task to go off when next mole should be shown
                    taskHandler.postDelayed(this,elapse);
                }
            }
        };
        //create new timer task to go off when next mole should be shown
        taskHandler.postDelayed(t,elapse);

    }
    //change the current mole whenever the timer ges off
    //i.e. how long a mole is on the screen
    protected void onTimerTask() {
        long endtime = startTime+(duration*1000);

        //if ending time is greater than current time, keep game going (set a new mole)
        if(endtime > System.currentTimeMillis()){
            setNewMole();
        }
        else {
            gameOver(); //if ending time is less than current time, game over.
        }
    }
}