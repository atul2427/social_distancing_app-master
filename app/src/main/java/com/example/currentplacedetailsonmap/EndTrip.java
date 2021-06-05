package com.example.currentplacedetailsonmap;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EndTrip extends AppCompatActivity {

    private TextView tripDurationTextView;
    private TextView tripNumberDevicesTextView;
    private TextView tripPlacesVisitedTextView;
    private TextView tripScoreTextView;

    private String username;
    private String duration;
    private String numPlaces;
    private String numDevices;
    private String score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_trip);
        bindView();
        username = getIntent().getStringExtra(StartingActivity.USERNAME);
        duration = getIntent().getStringExtra(MapsActivityCurrentPlace.DURATION);
        numDevices = getIntent().getStringExtra(MapsActivityCurrentPlace.NUMDEVICES);
        numPlaces = getIntent().getStringExtra(MapsActivityCurrentPlace.NUMPLACES);
        score = getIntent().getStringExtra(MapsActivityCurrentPlace.SCORE);
        setTextView();

    }
    public void setTextView()
    {
        tripDurationTextView.setText(duration);
        tripNumberDevicesTextView.setText(numDevices);
        tripPlacesVisitedTextView.setText(numPlaces);
        tripScoreTextView.setText(score);
    }

    public void bindView()
    {
        tripDurationTextView = findViewById(R.id.end_trip_duration_value_textView);
        tripNumberDevicesTextView = findViewById(R.id.end_trip_number_value_textView);
        tripPlacesVisitedTextView = findViewById(R.id.end_trip_places_value_textView);
        tripScoreTextView = findViewById(R.id.end_trip_score_value_textView);
    }


    /**
     * On click of home button
     * @param view
     */
    public void onHomeButtonClick(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(StartingActivity.USERNAME, username);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(StartingActivity.USERNAME, username);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
