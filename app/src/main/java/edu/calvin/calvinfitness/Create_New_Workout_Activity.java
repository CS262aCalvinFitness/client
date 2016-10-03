package edu.calvin.calvinfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

/*
This activity gives the user the option to create a new workout. This activity shows up when the
"Create New Workout" button is clicked from the MainActivity.
 */
public class Create_New_Workout_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__new__workout_);
        Intent intent = getIntent();
        setTitle("Create New Workout");
    }
}
