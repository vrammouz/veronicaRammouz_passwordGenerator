package com.example.veronica.passwordgenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    public RelativeLayout mainLayout;

    public CheckBox checkBox;

    public boolean isFromOptions=false, isChecked=false;

    public SharedPreferences tutorialPreferences,themePreferences;

    public int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        isFromOptions = intent.getBooleanExtra("getTutorial", false);

        tutorialPreferences = getSharedPreferences("isHidden", MODE_PRIVATE);
        themePreferences = getSharedPreferences("appTheme", MODE_PRIVATE);
        theme = themePreferences.getInt("theme", 0);
        isChecked = tutorialPreferences.getBoolean("isChecked", false);
        mainLayout = (RelativeLayout)findViewById(R.id.layout_main);
        checkBox = (CheckBox)findViewById(R.id.checkbox_tutorial);

        skipChecker();
        setAppTheme();
    }

    public void skipChecker()
    {
        if(isChecked && !isFromOptions)
        {
            Intent skipMain = new Intent(getApplicationContext(),GeneratorActivity.class);
            startActivity(skipMain);
        }
        else if (isFromOptions && isChecked)
        {
            checkBox.setChecked(isChecked);
        }
    }

    public void setAppTheme()
    {
        //check for theme selection
        switch(theme)
        {
            case R.color.darkPrimary:
                mainLayout.setBackgroundColor(getResources().getColor(R.color.lightPrimary));
            case R.color.lightPrimary:
                mainLayout.setBackgroundColor(getResources().getColor(R.color.darkPrimary));
            case R.color.teal:
                mainLayout.setBackgroundColor(getResources().getColor(R.color.teal));
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(MainActivity.this, "You are on the first page!", Toast.LENGTH_SHORT).show();
    }

    //choose password strength
    public void getHelp(View view)
    {
        int tag = Integer.parseInt(view.getTag().toString());
        Intent intent = new Intent(getApplicationContext(),HelpActivity.class);
        intent.putExtra("helpChoice", tag);
        startActivity(intent);
    }

    //to generator
    public void proceed(View view)
    {
        Intent intent = new Intent(getApplicationContext(),GeneratorActivity.class);
        startActivity(intent);
    }

    //checkbox
    public void removeTutorial(View view)
    {
        isChecked = ((CheckBox)view).isChecked();
        tutorialPreferences.edit().putBoolean("isChecked",isChecked).apply();
    }
}
