package com.example.currentplacedetailsonmap;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class ServerStatsService extends IntentService
{
    private String scoreStat;
    private String occupationStat;
    private String ageStat;
    private String conditionStat;
    private String genderStat;
    private String username;


    public ServerStatsService() {
        super("ServerStatsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        username = intent.getStringExtra(StartingActivity.USERNAME);
        scoreStat = "";
        occupationStat = "";
        ageStat = "";
        conditionStat = "";
        genderStat = "";
        performServerTask();
    }

    /**
     * Get stats from the server
     */
    public void performServerTask() {
        File file = this.getFileStreamPath("user.json");
        Intent serverStatsIntent = new Intent("ServerStatsReceiver");
        if (file.exists()) {
            try {
                Gson gson = new GsonBuilder().setDateFormat("MMM d, yyyy HH:mm:ss").create();;
                InputStream inputStream = this.openFileInput("user.json");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String data = bufferedReader.readLine();
                int postResponse = ServerRequestHandler.sendHttpPOSTRequest("syncdb/", data);
                Log.d("StatsActivity", "postResponse:" + postResponse);
                String response = ServerRequestHandler.sendHttpGetRequest("getstats", username);
                if (response.length() == 0) {
                    UserModel user = gson.fromJson(data, UserModel.class);
                    scoreStat = Double.toString(user.getScore());
                } else {
                    StatsModel stats = gson.fromJson(response, StatsModel.class);
                    scoreStat = Double.toString(stats.getCumScore());
                    occupationStat = Double.toString(stats.getOccupationPercent());
                    ageStat = Double.toString(stats.getAgePercent());
                    genderStat = Double.toString(stats.getGenderPercent());
                    conditionStat = Double.toString(stats.getConditionPercent());

                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                serverStatsIntent.putExtra(StatsActivity.AGE, ageStat);
                serverStatsIntent.putExtra(StatsActivity.CONDITION, conditionStat);
                serverStatsIntent.putExtra(StatsActivity.GENDER, genderStat);
                serverStatsIntent.putExtra(StatsActivity.OCCUPATION, occupationStat);
                serverStatsIntent.putExtra(StatsActivity.SCORE, scoreStat);
                sendBroadcast(serverStatsIntent);

            } catch (IOException e) {
                Log.e("StatsActivity", e.getMessage());
                serverStatsIntent.putExtra(StatsActivity.AGE, ageStat);
                serverStatsIntent.putExtra(StatsActivity.CONDITION, conditionStat);
                serverStatsIntent.putExtra(StatsActivity.GENDER, genderStat);
                serverStatsIntent.putExtra(StatsActivity.OCCUPATION, occupationStat);
                serverStatsIntent.putExtra(StatsActivity.SCORE, scoreStat);
                sendBroadcast(serverStatsIntent);
            }
        }
    }
}
