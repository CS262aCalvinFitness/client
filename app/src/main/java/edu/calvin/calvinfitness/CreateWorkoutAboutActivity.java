package edu.calvin.calvinfitness;

import android.os.Bundle;
import android.view.Menu;


/*
 * Activity that displays the About information for the Create Workout Activity
 * @date: 10/11/2016
 */

public class CreateWorkoutAboutActivity extends MainActivity {

    /*
     * onCreate() is run when this activity is called
     *
     * Overrides the default onCreate method to set the activity layout
     *
     * @param: savedInstanceState
     * @return: void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_create_workout);
        setTitle("Create Workout: Help");
    }

    /* Remove the triple-dot icon from the top of the application */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {return false; }
}
