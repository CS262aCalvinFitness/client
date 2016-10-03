package edu.calvin.calvinfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
The Start Workout activity gives users the ability to start and fill in data for a workout. This
activity is shown when the "Start Workout" button is clicked from MainActivity.
 */
public class Start_Workout_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__workout_);
        Intent intent = getIntent();
        setTitle("Select Workout");
    }
}
