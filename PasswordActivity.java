package com.example.veronica.passwordgenerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PasswordActivity extends AppCompatActivity {
    public RelativeLayout passwordLayout;

    public SharedPreferences passwordPreferences, themePreferences;

    public TextView passwordText;

    public String passwordString;

    public int tag, length,theme;

    public int maxWeak = 6, minWeak = 4, maxMedium = 10, minMedium = 8, maxStrong = 16, minStrong = 12;

    public final String[] lowercase = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public final String[] uppercase = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public final String[] specialChar = {"!", "@", "#", "$", "?"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Intent intent = getIntent();
        tag = intent.getIntExtra("chosenPasswordLevel", 0);

        passwordLayout = (RelativeLayout) findViewById(R.id.password_layout);
        passwordText = (TextView) findViewById(R.id.password_text);

        passwordPreferences = getSharedPreferences("passwords", MODE_PRIVATE);
        themePreferences = getSharedPreferences("appTheme", MODE_PRIVATE);
        theme = themePreferences.getInt("theme", 0);
        setAppTheme();

        passwordText.setText("");
        passwordString = "";

        setPassword();
        passwordText.setText(passwordString);
    }

    public void setPassword()
    {
        Random random = new Random();

        if (tag == 0) {
            length = random.nextInt(maxWeak - minWeak + 1) + minWeak;
            for (int i = 0; i < Math.floor(length / 2); i++) {
                passwordString += lowercase[random.nextInt(lowercase.length)];
                passwordString += random.nextInt(9);
            }
            Log.i("Logging", "Weak Password");
        }
        if (tag == 1) {
            length = random.nextInt(maxMedium - minMedium + 1) + minMedium;
            for (int j = 0; j < Math.floor(length / 3); j++) {
                passwordString += random.nextInt(9);
                passwordString += lowercase[random.nextInt(lowercase.length)];
                passwordString += uppercase[random.nextInt(uppercase.length)];
            }
            Log.i("Logging", "Medium Password");
        }

        if (tag == 2 || tag == 3) {
            length = random.nextInt(maxStrong - minStrong + 1) + minStrong;
            for (int k = 0; k < Math.floor(length / 4); k++) {
                passwordString += specialChar[random.nextInt(specialChar.length)];
                passwordString += uppercase[random.nextInt(uppercase.length)];
                passwordString += lowercase[random.nextInt(lowercase.length)];
                passwordString += random.nextInt(9);
            }
            Log.i("Logging", "Strong Password");
        }
    }

    public void setAppTheme()
    {
        switch (theme)
        {
            case R.color.darkPrimary:
                passwordLayout.setBackgroundColor(getResources().getColor(R.color.darkPrimary));
            case R.color.lightPrimary:
                passwordLayout.setBackgroundColor(getResources().getColor(R.color.lightPrimary));
            case R.color.teal:
                passwordLayout.setBackgroundColor(getResources().getColor(R.color.teal));
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), GeneratorActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public void savePassword(View view)
    {
        if (passwordPreferences.getString("savedPassword", "").isEmpty())
            passwordSave();

        else if (!passwordPreferences.getString("savedPassword", "").isEmpty())
            alertOverwrite();
    }

    public void passwordSave()
    {
        passwordPreferences.edit().putString("savedPassword", passwordString).apply();
        Log.i("Password State", "SAVED");
        Toast.makeText(PasswordActivity.this, "Password successfully saved!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), GeneratorActivity.class);
        startActivity(intent);
    }

    public void alertOverwrite() {
        new AlertDialog.Builder(getApplicationContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Storing password for later use")
                .setMessage("Do you wish to overwrite the existing saved password?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Password State", "OVERWRITTEN");
                        passwordPreferences.edit().clear();
                        passwordPreferences.edit().putString("savedPassword", passwordString).apply();
                        Toast.makeText(PasswordActivity.this, "Password successfully overwritten!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), GeneratorActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null).show();
    }



    public void discardPassword(View view) {
        new AlertDialog.Builder(getApplicationContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Discard generated password")
                .setMessage("Are you sure you want to discard the generated password?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Password State", "DISCARDED");
                        Toast.makeText(PasswordActivity.this, "Password discarded", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), GeneratorActivity.class);
//                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null).show();
    }

}