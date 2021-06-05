package com.example.currentplacedetailsonmap;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String username;

    private TextView welcomeUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        username = getIntent().getStringExtra(StartingActivity.USERNAME);
        welcomeUserTextView.setText("Hey, "+username);
    }

    public void bindView()
    {
        welcomeUserTextView = findViewById(R.id.main_greeting_textview);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(this, StartingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onStartTripButtonClick(View view)
    {
        Intent startActivityIntent = new Intent(this, MapsActivityCurrentPlace.class);
        startActivityIntent.putExtra(StartingActivity.USERNAME, username);
        startActivity(startActivityIntent);
    }

    public void onStatsButtonClick(View view)
    {
        Intent intent = new Intent(this, StatsActivity.class);
        intent.putExtra(StartingActivity.USERNAME, username);
        startActivity(intent);
    }


}
