package com.example.whackamole10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class HighScores extends AppCompatActivity implements View.OnClickListener{
    Intent playsScreenIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        playsScreenIntent = new Intent(this,Game.class);

        //load in all high scores and show them
        loadHsIF();
    }

    private void loadHsIF(){ //internal file
       try{
           FileInputStream fis = openFileInput("HighScores.txt"); //open this file
           readScoresFIS(fis);
       }
       //try this, but if it doesn't work, do the following. it prevents crashes.
       catch(Exception e){
           CharSequence text = "The file could not be opened. "+e.toString();
           int dur = Toast.LENGTH_LONG;

           Toast message = Toast.makeText( this,text,dur);
           message.show();
        }
    }

    private void readScoresFIS(FileInputStream fis){
        InputStreamReader isr = new InputStreamReader(fis); //read this file

        TextView tvname = (TextView)findViewById(R.id.tvPlayerName);
        TextView tvscore = (TextView)findViewById(R.id.tvScore);

        String endline = System.getProperty("line.separator"); //get the line separator (\n or enter) for specific device
        //set for a new name or score

        LinkedList<String> playerNames = new LinkedList<>();
        LinkedList<Integer> playerScores = new LinkedList<>();
        //keep track of all existing players and their scores so we can figure out who to show in high score list

        try{
            //use a BufferedReader to allow easy reading of file data
            BufferedReader buffReader = new BufferedReader(isr);
            //read in data, line by line
            String name = buffReader.readLine();
            //while we still have data
            while(name != null){
                //read next line (score)
                String strScore = buffReader.readLine();
                int score = Integer.parseInt(strScore); //convert to int
                //we need them to be ints so we can compare and sort so we know which ones are the highest
                ListIterator<Integer> scoreIter = playerScores.listIterator();
                ListIterator<String> playerIter = playerNames.listIterator();
                while (scoreIter.hasNext()){
                    //as long as there are values to read, it will keep reading and sorting.
                    Integer thisScore = scoreIter.next();
                    playerIter.next();

                    if (score >= thisScore) {
                        break;
                    }
                }
                if(playerScores.size() > 0){
                    scoreIter.previous();
                    playerIter.previous();
                }
                scoreIter.add(new Integer(score));
                playerIter.add(name);

                name = buffReader.readLine();
            }
            buffReader.close(); //don't forget to close!!!!!!
        }
        catch(Exception e){
            CharSequence text = "There was an issue reading the file. "+e.toString();
            int dur = Toast.LENGTH_LONG;

            Toast message = Toast.makeText( this,text,dur);
            message.show();
        }

        //put values together in list on screen
        ListIterator<Integer> scoreIter = playerScores.listIterator();
        ListIterator<String> playerIter = playerNames.listIterator();

        //these strings contain he sorted scores and corresponding names
        String sortedNames = "";
        String sortedScores = "";

        int numPresent = 0; //count how many scores we're adding
        while (scoreIter.hasNext()){
            Integer score = scoreIter.next();
            String name = playerIter.next();

            sortedScores += score.toString()  + endline;
            //take what comes next and put it on the line below
            sortedNames += name + endline;

            numPresent++;
            if (numPresent >= 10){
                break;
            }
        }

        //put the sorted values in the text views so they will be visible on screen
        tvname.setText(sortedNames);
        tvscore.setText(sortedScores);

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