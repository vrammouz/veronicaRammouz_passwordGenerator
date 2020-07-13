package com.example.veronica.passwordgenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class HelpActivity extends AppCompatActivity
{
    public RelativeLayout help;

    public SharedPreferences themePreferences,tutorialPreferences;

    public VideoView tutorialVideo;

    public TextView explainText;

    public int choice;

    public int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Intent intent = getIntent();
        choice = intent.getIntExtra("helpChoice", 0);

        themePreferences = getSharedPreferences("appTheme", MODE_PRIVATE);
        tutorialPreferences = getSharedPreferences("isHidden", MODE_PRIVATE);
        theme = themePreferences.getInt("theme", 0);

        help = (RelativeLayout)findViewById(R.id.help_layout);
        tutorialVideo = (VideoView)findViewById(R.id.video_help);
        explainText = (TextView)findViewById(R.id.text_explain);

        setAppTheme();
        setVideo();
    }

    public void setVideo()
    {
        switch(choice)
        {
            case 1:
                tutorialVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.floki1);
                explainText.setText(R.string.video_weak);
                break;
            case 2:
                tutorialVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.floki2);
                explainText.setText(R.string.video_medium);
                break;
            case 3:
                tutorialVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.bellaciao);
                explainText.setText(R.string.video_strong);
                break;
            default:
                break;
        }
        MediaController controller = new MediaController(this);
        controller.setAnchorView(tutorialVideo);

        tutorialVideo.setMediaController(controller);
        tutorialVideo.start();
    }

    public void setAppTheme()
    {
        //check for theme selection
        switch(theme)
        {
            case R.color.darkPrimary:
                help.setBackgroundColor(getResources().getColor(R.color.darkPrimary));
            case R.color.lightPrimary:
                help.setBackgroundColor(getResources().getColor(R.color.lightPrimary));
            case R.color.teal:
                help.setBackgroundColor(getResources().getColor(R.color.teal));
            default:
                Log.i("Logging", "Failed to set Theme");
                break;
        }
    }

    //"main" menu
    public void toPreferredMenu(View view)
    {
        boolean shouldSkip = tutorialPreferences.getBoolean("isChecked",false);
        if(shouldSkip)
        {
            Intent skipMain = new Intent(getApplicationContext(), GeneratorActivity.class);
            startActivity(skipMain);
        }
        else if(!shouldSkip)
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(),GeneratorActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
