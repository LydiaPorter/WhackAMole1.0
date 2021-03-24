package com.example.whackamole10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;

public class Options extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        setUpSpinner();
        loadSettings();
    }
    private void setUpSpinner(){
        //get a handle ot the spinner view control
        Spinner sp = (Spinner)findViewById(R.id.spMoles);
        //create an array of integers to use in the array
        String[] numMoles = {"3","4","5","6","7","8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,numMoles);
        sp.setAdapter(adapter);


    }
    @Override
    public void onClick(View v) {
        //if play button was clicked . . .
        if(v.getId()!=R.id.buttonPlay){
            return; //if you don't click play, don't do anything
        }

        Intent playIntent = new Intent(this,Game.class);


        //SAVING DATA when you click the play button
        String name;
        int difficulty;
        int duration;
        int numMoles;

        EditText etName = (EditText)findViewById(R.id.editTextName);
        RadioButton easy = (RadioButton)findViewById(R.id.rbEasy);
        RadioButton medium = (RadioButton)findViewById(R.id.rbMedium);
        SeekBar sb = (SeekBar)findViewById(R.id.sbDuration);
        Spinner sp = (Spinner)findViewById(R.id.spMoles);

        name = etName.getText().toString();
        duration = sb.getProgress();
        numMoles = sp.getSelectedItemPosition()+3;
        //take whatever index position (starting counting by 0) and add 3 to get the numbers in the numMoles array above. :)
        //+= is assingment so . . .

        if(easy.isChecked()){
            difficulty = 3;
        }
        else if(medium.isChecked()){
            difficulty = 2;
        }
        else {
            difficulty = 1;
        }

     // saveSettingsInIntent(difficulty, name, numMoles, duration, playIntent);
        saveSettingsInPrefs(difficulty, name, numMoles, duration);

        startActivity(playIntent);

    }

    private void saveSettingsInPrefs(int difficulty, String name, int numMoles, int duration){
        //get references to shared preferences for application
        SharedPreferences prefs = getSharedPreferences("WhackSettings",MODE_PRIVATE);
        //get an editor object that we cna use to write our option settings
        SharedPreferences.Editor editor = prefs.edit();

        //save all option info to shared preferences area
        editor.putString("name",name);
        editor.putInt("difficulty",difficulty);
        editor.putInt("numMoles",numMoles);
        editor.putInt("duration",duration);

        editor.commit(); //commit all these to the file
        //save data using onClick

    }
    private void saveSettingsInIntent(int difficulty, String name, int numMoles, int duration, Intent myIntent){
        myIntent.putExtra("name",name);
        myIntent.putExtra("difficulty",difficulty);
        myIntent.putExtra("duration",duration);
        myIntent.putExtra("number of moles", numMoles);
    }
    private void loadSettings(){
        SharedPreferences prefs = getSharedPreferences("WhackSettings",MODE_PRIVATE); //get access to pref file
        //get values from sharedpreferences
        String playerName = prefs.getString("name","Default"); //assign player name. assign to Default if empty.
        int difficultyLevel = prefs.getInt("difficulty",1);
        int numMoles = prefs.getInt("numMoles", 8);
        int duration = prefs.getInt("duration",20);

        EditText etName = (EditText)findViewById(R.id.editTextName);
        RadioButton easy = (RadioButton)findViewById(R.id.rbEasy);
        RadioButton medium = (RadioButton)findViewById(R.id.rbMedium);
        RadioButton hard = (RadioButton)findViewById(R.id.rbHard);
        SeekBar sb = (SeekBar)findViewById(R.id.sbDuration);
        Spinner sp = (Spinner)findViewById(R.id.spMoles);

        etName.setText(playerName);
        sp.setSelection(numMoles-3);
        sb.setProgress(duration);

        if (difficultyLevel ==3){
            easy.setChecked(true);
        }
        else if (difficultyLevel == 2){
            medium.setChecked(true);
        }
        else{
            hard.setChecked(true);
        }
    }
}