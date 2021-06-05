package com.example.currentplacedetailsonmap;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WelcomeNewUser extends AppCompatActivity {

    private String username;
    private String occupation;
    private String age;
    private String gender;
    private String condition;

    private EditText occupationEditText;
    private EditText ageEditText;
    private EditText genderEditText;
    private EditText conditionEditText;

    private TextView welcomeTextView;

    public static final String AGE = "AGE";
    public static final String OCCUPATION = "OCCUPATION";
    public static final String GENDER = "GENDER";
    public static final String CONDITION = "CONDITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_new_user);
        bindView();
        Intent i = getIntent();
        username = i.getStringExtra(StartingActivity.USERNAME);
        welcomeTextView.setText("Welcome, "+username);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        welcomeTextView.setText("Welcome, "+username);
    }

    public void bindView()
    {
        occupationEditText = findViewById(R.id.welcome_new_user_occupation_edittext);
        ageEditText = findViewById(R.id.welcome_new_user_age_edittext);
        genderEditText = findViewById(R.id.welcome_new_user_gender_edittext);
        conditionEditText = findViewById(R.id.welcome_new_user_condition_edittext);
        welcomeTextView = findViewById(R.id.welcome_new_greeting_textview);
    }

    /**
     * Called when Next button is clicked
     * @param view
     */
    public void onNextButtonClick(View view) {

        occupation = occupationEditText.getText().toString();
        age = ageEditText.getText().toString();
        gender = genderEditText.getText().toString();
        condition = conditionEditText.getText().toString();
        if(occupation == null || occupation.equals(""))
        {
            Toast.makeText(this, "Please provide occupation", Toast.LENGTH_SHORT).show();
        }
        else if(age == null || age.equals(""))
        {
            Toast.makeText(this, "Please provide age", Toast.LENGTH_SHORT).show();
        }
        else if(gender == null || gender.equals(""))
        {
            Toast.makeText(this, "Please provide gender", Toast.LENGTH_SHORT).show();
        }
        else
        {
            OutputStreamWriter outputStreamWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                outputStreamWriter = new OutputStreamWriter(this.openFileOutput("user.json", Context.MODE_PRIVATE));
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                UserModel user = new UserModel();
                user.setId(UUID.randomUUID().toString());
                user.setUsername(username);
                List<TripModel> tripsList = new ArrayList<>();
                user.setTrips(tripsList);
                try {
                    user.setAge(Integer.parseInt(age));
                }
                catch (NumberFormatException e)
                {
                    Log.e("WelcomeNewUser", "NumberFormatException");
                    user.setAge(0);
                }
                user.setGender(gender.toLowerCase());
                user.setCondition(condition.toLowerCase());
                user.setOccupation(occupation.toLowerCase());
                Gson gson = new GsonBuilder().setDateFormat("MMM d, yyyy HH:mm:ss").create();;
                gson.toJson(user, bufferedWriter);
            }
            catch (FileNotFoundException e)
            {
                Log.e("WelcomeNewUser", e.getMessage());
            }
            finally {
                try {
                    if (bufferedWriter != null) bufferedWriter.close();
                    if (outputStreamWriter != null) outputStreamWriter.close();
                }
                catch (IOException e)
                {
                    Log.e("WelcomeNewUser", e.getMessage());
                }
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(StartingActivity.USERNAME, username);
            intent.putExtra(WelcomeNewUser.AGE, age);
            intent.putExtra(WelcomeNewUser.CONDITION, (condition==null || condition.equals("") ? "NA" : condition));
            intent.putExtra(WelcomeNewUser.GENDER, gender);
            intent.putExtra(WelcomeNewUser.OCCUPATION, occupation);
            startActivity(intent);
            finish();
        }
    }


}
