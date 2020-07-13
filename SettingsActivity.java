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
import android.widget.TextView;

import java.util.Random;

public class SettingsActivity extends AppCompatActivity
{
    public SharedPreferences tutorialPreferences,passwordPreferences,themePreferences;

    public RelativeLayout settingsLayout;

    public TextView passwordText;
//
//    public ListView passwordView;
//
//    public ArrayList<String> passwordList;
//    public ArrayAdapter<String> passwordAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        themePreferences = getSharedPreferences("appTheme",MODE_PRIVATE);
        passwordPreferences = getSharedPreferences("passwords",MODE_PRIVATE);
        tutorialPreferences = getSharedPreferences("isHidden",MODE_PRIVATE);

//        passwordView = (ListView)findViewById(R.id.password_list);
//        passwordList = new ArrayList<>();
//        passwordAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, passwordList);
//        passwordView.setAdapter(passwordAdapter);
//        passwordList.add(passwordPreferences.getString("savedPassword",""));

        settingsLayout = (RelativeLayout)findViewById(R.id.settings_activity);
        passwordText = (TextView)findViewById(R.id.password_text);
        passwordText.setText(passwordPreferences.getString("savedPassword", ""));
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(),GeneratorActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()== R.id.restore)
        {
//            new AlertDialog.Builder(getApplicationContext())
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("Restore")
//                    .setMessage("Are you sure you want to restore the app to its default settings?")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which)
//                        {
                            passwordPreferences.edit().clear().apply();
                            tutorialPreferences.edit().clear().apply();
                            themePreferences.edit().clear().apply();
                            passwordText.setText(passwordPreferences.getString("savedPassword", ""));
                            Log.i("Logging", "App Restored");
//                            Toast.makeText(SettingsActivity.this, "App successfully restored", Toast.LENGTH_SHORT).show();
//                        }
//                })
//                .setNegativeButton("No", null).show();
        }
        return true;
    }

    //choose color
    public void toggleTheme(View view)
    {
        int tag=Integer.parseInt(view.getTag().toString());
        Random random = new Random();

        switch (tag)
        {
            case 0:
                view.setTag(random.nextInt(3) + 1);
            case 1:
                settingsLayout.setBackgroundColor(getResources().getColor(R.color.darkPrimary));
                themePreferences.edit().clear().apply();
                themePreferences.edit().putInt("theme", R.color.darkPrimary).apply();
                break;
            case 2:
                settingsLayout.setBackgroundColor(getResources().getColor(R.color.lightPrimary));
                themePreferences.edit().clear().apply();
                themePreferences.edit().putInt("theme", R.color.lightPrimary).apply();
                break;
            case 3:
                settingsLayout.setBackgroundColor(getResources().getColor(R.color.teal));
                themePreferences.edit().clear().apply();
                themePreferences.edit().putInt("theme", R.color.teal).apply();
                break;
            default:
                Log.i("Logging","Failed to set Theme");
                break;
        }
    }
    //back to generator
    public void back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),GeneratorActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
