package com.example.veronica.passwordgenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class GeneratorActivity extends AppCompatActivity
{
    public RelativeLayout generator;

    public SharedPreferences themePreferences, passwordPreferences, tutorialPreferences;

    public SeekBar chooseLevel;

    public TextView passwordExists;

    public boolean isFromOptions=true;

    public int tag,theme;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        passwordPreferences = getSharedPreferences("passwords",MODE_PRIVATE);
        themePreferences = getSharedPreferences("appTheme",MODE_PRIVATE);
        tutorialPreferences = getSharedPreferences("isHidden",MODE_PRIVATE);
        theme = themePreferences.getInt("theme", 0);

        chooseLevel = (SeekBar)findViewById(R.id.chosen_strength);
        passwordExists = (TextView)findViewById(R.id.does_exist);
        generator = (RelativeLayout)findViewById(R.id.generator_layout);
        setAppTheme();
        showExistingPassword();
        setChosenLevel();
    }

    public void setChosenLevel()
    {
        //choose password level from seekbar
        chooseLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (i == 0)
                    chooseLevel.setBackgroundColor(getResources().getColor(R.color.red_alert));
                if (i == 1)
                    chooseLevel.setBackgroundColor(getResources().getColor(R.color.yellow));
                if (i == 2 || i==3)
                    chooseLevel.setBackgroundColor(getResources().getColor(R.color.green));
                tag = i;
                Log.i("Chosen", tag + "");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void showExistingPassword()
    {
        // does the saved password exist
        if(!passwordPreferences.getString("savedPassword","").isEmpty())
            passwordExists.setVisibility(View.VISIBLE);
        else if(passwordPreferences.getString("savedPassword","").isEmpty())
            passwordExists.setVisibility(View.GONE);
    }

    public void setAppTheme()
    {
        //check for theme selection
        switch(theme)
        {
            case R.color.darkPrimary:
                generator.setBackgroundColor(getResources().getColor(R.color.darkPrimary));
            case R.color.lightPrimary:
                generator.setBackgroundColor(getResources().getColor(R.color.lightPrimary));
            case R.color.teal:
                generator.setBackgroundColor(getResources().getColor(R.color.teal));
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        if(tutorialPreferences.getBoolean("isChecked",false))
            Toast.makeText(GeneratorActivity.this, "Go to help. Go get help.", Toast.LENGTH_SHORT).show();
        else if(!tutorialPreferences.getBoolean("isChecked",false))
        {
            Intent intent = new Intent(getApplicationContext(),GeneratorActivity.class);
            startActivity(intent);
        }
        super.onBackPressed();
    }

    //the name says it all
    public void generatePassword(View view)
    {
        Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
        intent.putExtra("chosenPasswordLevel",tag);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.settings:
                Log.i("Logging Toggle", "SETTINGS");
                Intent intentSettings = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intentSettings);
                break;

            case R.id.help:
                Log.i("Logging Toggle", "HELP AKA MAIN");
                Intent intentHelp = new Intent(getApplicationContext(),MainActivity.class);
                intentHelp.putExtra("getTutorial",isFromOptions);
                startActivity(intentHelp);
                break;

            default:
                Log.i("Logging Toggle", "NAY");
                break;
        }
        return true;
    }
}
