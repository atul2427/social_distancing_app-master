package com.example.currentplacedetailsonmap;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerFetchService extends IntentService
{
    public static final int SERVER_RESULT_FOUND = 0;
    public static final int SERVER_RESULT_NOTFOUND = 1;
    public static final String SERVER_RESULT = "SERVER_RESULT";
    private String username;
    public ServerFetchService() {
        super("com.example.currentplacedetailsonmap.ServerFetchService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        username = intent.getStringExtra(StartingActivity.USERNAME);
        // Sync with server
        File file = this.getFileStreamPath("user.json");
        if(file.exists())
        {
            try {
                InputStream inputStream = this.openFileInput("user.json");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String data = bufferedReader.readLine();
                int postResponse = ServerRequestHandler.sendHttpPOSTRequest("syncdb/", data);
                Log.d("ServerFetchService", "postResponse:"+postResponse);
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

            } catch (IOException e) {
                Log.e("ServerFetchService", e.getMessage());
            }

        }
        // check if user exists
        String response = ServerRequestHandler.sendHttpGetRequest("fetchuser", username);
        Intent broadcastIntent = new Intent("ServerFetchIntent");
        if(response == null || response.length() == 0)
        {
            broadcastIntent.putExtra(SERVER_RESULT, SERVER_RESULT_NOTFOUND);
        }
        else
        {
            broadcastIntent.putExtra(SERVER_RESULT, SERVER_RESULT_FOUND);
            try
            {
                Gson gson = new GsonBuilder().setDateFormat("MMM d, yyyy HH:mm:ss").create();;
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("user.json", Context.MODE_PRIVATE));
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                UserModel user = gson.fromJson(response, UserModel.class);
                gson.toJson(user, bufferedWriter);
                bufferedWriter.close();
                outputStreamWriter.close();
            }
            catch (IOException e)
            {
                Log.e("ServerFetchService", e.getMessage());
            }
        }
        broadcastIntent.putExtra(StartingActivity.USERNAME, username);
        sendBroadcast(broadcastIntent);
    }
}
