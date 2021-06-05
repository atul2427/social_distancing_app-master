package com.example.currentplacedetailsonmap;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeReturnUser extends AppCompatActivity {

    private String username;
    private TextView welcomeTextView;
    Handler handler;
    private final String TAG = "WelcomeReturnUser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_return_user);
        bindView();
        username = getIntent().getStringExtra(StartingActivity.USERNAME);
        welcomeTextView.setText("Welcome back, "+username);
        handler = new Handler();
        handler.postDelayed(callNextActivityRunnable, 2000);
    }
    public void bindView()
    {
        welcomeTextView = findViewById(R.id.welcome_return_greeting_textview);
    }
    // Define the code block to be executed
    private Runnable callNextActivityRunnable = new Runnable() {
        @Override
        public void run() {
            // Do something here on the main thread
            Log.d(TAG, "Going to next activity");
            Intent intent = new Intent(WelcomeReturnUser.this, MainActivity.class);
            intent.putExtra(StartingActivity.USERNAME, username);
            startActivity(intent);
        }
    };

}
