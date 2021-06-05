package com.example.currentplacedetailsonmap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StartingActivity extends AppCompatActivity {

    private String username;
    private EditText usernameEditText;
    private TextView gettingDataTextView;
    public static final String USERNAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        bindView();
        IntentFilter intentFilter = new IntentFilter("ServerFetchIntent");
        this.registerReceiver(receiver, intentFilter);
    }
    public void bindView()
    {
        usernameEditText = findViewById(R.id.starting_username_edittext);
        gettingDataTextView = findViewById(R.id.starting_getting_data_textview);
        gettingDataTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(receiver != null)
        {
            this.unregisterReceiver(receiver);
        }
    }
    @Override
    protected  void onPause()
    {
        super.onPause();
        if(receiver != null)
        {
            this.unregisterReceiver(receiver);
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        gettingDataTextView.setVisibility(View.INVISIBLE);
        IntentFilter intentFilter = new IntentFilter("ServerFetchIntent");
        this.registerReceiver(receiver, intentFilter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            System.exit(0);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onNextButtonFocus(View view)
    {
        usernameEditText.setText("");
    }

    public void onNextButtonClick(View view)
    {
        username = usernameEditText.getText().toString();
        if(username == null || username.equals(""))
        {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }
        else
        {
            gettingDataTextView.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, ServerFetchService.class);
            intent.putExtra(StartingActivity.USERNAME, username);
            startService(intent);
        }
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                if(bundle.getInt(ServerFetchService.SERVER_RESULT) == ServerFetchService.SERVER_RESULT_FOUND)
                {
                    Intent welcomeReturnIntent = new Intent(StartingActivity.this, WelcomeReturnUser.class);
                    welcomeReturnIntent.putExtra(StartingActivity.USERNAME, username);
                    startActivity(welcomeReturnIntent);
                }
                else
                {
                    Intent welcomeNewIntent = new Intent(StartingActivity.this, WelcomeNewUser.class);
                    welcomeNewIntent.putExtra(StartingActivity.USERNAME, username);
                    startActivity(welcomeNewIntent);
                }
            }

        }
    };

}

