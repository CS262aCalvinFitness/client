package edu.calvin.calvinfitness;

import android.os.Bundle;
import android.view.Menu;

/*
 * Activity that displays the About Start Workout information to the user
 * @date: 10/10/2016
 */

public class StartWorkoutHelpActivity extends MainActivity {

    /*
     * Called when this activity is called
     *
     * Overrides the default onCreate method to set the activity layout
     *
     * @param: savedInstanceState
     * @return: void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_start_workout);
        setTitle("Start Workout: Help");
    }

    /* Remove the triple-dot icon from the top of the application */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {return false; }
}
