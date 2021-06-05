package com.example.currentplacedetailsonmap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;

import java.io.*;

public class StatsActivity extends AppCompatActivity {

    private TextView gettingDataTextView;
    private TextView scoreTextView;
    private TextView ageTextView;
    private TextView genderTextView;
    private TextView occupationTextView;
    private TextView conditionTextView;

    private TextView ageGrpTextView;
    private TextView genderGrpTextView;
    private TextView occupationGrpTextView;
    private TextView topTextView;
    private TextView conditionGrpTextView;
    private TextView youScoreTextView;

    private String scoreStat;
    private String genderStat;
    private String occupationStat;
    private  String conditionStat;
    private  String ageStat;

    private String username;

    public static final String SCORE = "SCORE";
    public static final String OCCUPATION = "OCCUPATION";
    public static final String AGE = "AGE";
    public static final String CONDITION = "CONDITION";
    public static final String GENDER = "GENDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        username = getIntent().getStringExtra(StartingActivity.USERNAME);
        bindView();
        setVisibility(0);
        IntentFilter filter = new IntentFilter("ServerStatsReceiver");
        this.registerReceiver(serverStatReceiver, filter);
        Intent statServiceIntent = new Intent(this, ServerStatsService.class);
        statServiceIntent.putExtra(StartingActivity.USERNAME, username);
        startService(statServiceIntent);
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
    @Override
    protected void onResume()
    {
        super.onResume();
        IntentFilter filter = new IntentFilter("ServerStatsReceiver");
        this.registerReceiver(serverStatReceiver, filter);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.unregisterReceiver(serverStatReceiver);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        this.unregisterReceiver(serverStatReceiver);
    }
    BroadcastReceiver serverStatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                scoreStat = bundle.getString(SCORE);
                ageStat = bundle.getString(AGE);
                occupationStat = bundle.getString(OCCUPATION);
                conditionStat = bundle.getString(CONDITION);
                genderStat = bundle.getString(CONDITION);
                if(ageStat.length() == 0
                        && occupationStat.length() == 0
                        && conditionStat.length() == 0
                        && genderStat.length() == 0)
                {
                    scoreStat = String.format("%.2f", Double.parseDouble(scoreStat));
                    scoreTextView.setText(scoreStat);
                    setVisibility(1);
                }
                else
                {
                    scoreStat = String.format("%.2f", Double.parseDouble(scoreStat));
                    ageStat = String.format("%.2f", Double.parseDouble(ageStat));
                    occupationStat = String.format("%.2f", Double.parseDouble(occupationStat));
                    genderStat = String.format("%.2f", Double.parseDouble(genderStat));
                    conditionStat = String.format("%.2f", Double.parseDouble(conditionStat));
                    scoreTextView.setText(scoreStat);
                    ageTextView.setText(ageStat+"%");
                    occupationTextView.setText(occupationStat+"%");
                    genderTextView.setText(genderStat+"%");
                    conditionTextView.setText(conditionStat+"%");
                    setVisibility(2);
                }
            }

        }
    };

    public void setVisibility(int _case)
    {
        switch(_case)
        {
            case 0:
                gettingDataTextView.setVisibility(View.VISIBLE);
                scoreTextView.setVisibility(View.INVISIBLE);
                ageTextView.setVisibility(View.INVISIBLE);
                genderTextView.setVisibility(View.INVISIBLE);
                occupationTextView.setVisibility(View.INVISIBLE);
                conditionTextView.setVisibility(View.INVISIBLE);
                topTextView.setVisibility(View.INVISIBLE);
                ageGrpTextView.setVisibility(View.INVISIBLE);
                genderGrpTextView.setVisibility(View.INVISIBLE);
                occupationGrpTextView.setVisibility(View.INVISIBLE);
                conditionGrpTextView.setVisibility(View.INVISIBLE);
                youScoreTextView.setVisibility(View.INVISIBLE);
                break;
            case 1:
                gettingDataTextView.setVisibility(View.INVISIBLE);
                scoreTextView.setVisibility(View.VISIBLE);
                youScoreTextView.setVisibility(View.VISIBLE);
                break;
            case 2:
                gettingDataTextView.setVisibility(View.INVISIBLE);
                scoreTextView.setVisibility(View.VISIBLE);
                youScoreTextView.setVisibility(View.VISIBLE);
                ageTextView.setVisibility(View.VISIBLE);
                genderTextView.setVisibility(View.VISIBLE);
                occupationTextView.setVisibility(View.VISIBLE);
                conditionTextView.setVisibility(View.VISIBLE);
                topTextView.setVisibility(View.VISIBLE);
                ageGrpTextView.setVisibility(View.VISIBLE);
                genderGrpTextView.setVisibility(View.VISIBLE);
                occupationGrpTextView.setVisibility(View.VISIBLE);
                conditionGrpTextView.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void bindView()
    {
        gettingDataTextView = findViewById(R.id.stats_getting_data_textView);
        scoreTextView = findViewById(R.id.stats_score_textView);
        ageTextView = findViewById(R.id.stats_age_textView);
        genderTextView = findViewById(R.id.stats_gender_textView);
        occupationTextView = findViewById(R.id.stats_occupation_textView);
        conditionTextView = findViewById(R.id.stats_condition_textView);

        topTextView = findViewById(R.id.stats_top_textView);
        ageGrpTextView = findViewById(R.id.stats_agegrp_textView);
        genderGrpTextView = findViewById(R.id.stats_gendergrp_textView);
        occupationGrpTextView = findViewById(R.id.stats_occgrp_textView);
        conditionGrpTextView = findViewById(R.id.stats_congrp_textView);
        youScoreTextView = findViewById(R.id.stats_your_score_textView);


    }

    public void onHomeButtonClick(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(StartingActivity.USERNAME, username);
        startActivity(intent);
    }

}
