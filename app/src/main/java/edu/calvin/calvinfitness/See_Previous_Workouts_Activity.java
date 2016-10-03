package edu.calvin.calvinfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
This activity shows the user the previous workouts that they have completed. This activity is shown
when the "See Previous Workouts" button is clicked on MainActivity.
 */
public class See_Previous_Workouts_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see__previous__workouts_);
        Intent intent = getIntent();
        setTitle("View Past Workout Results");
    }
}
