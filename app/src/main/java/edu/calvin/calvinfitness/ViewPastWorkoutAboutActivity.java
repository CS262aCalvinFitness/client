package edu.calvin.calvinfitness;

import android.os.Bundle;
import android.view.Menu;

/*
 * Activity that displays the information regarding the View Past Workout activity
 * @date: 10/10/2016
 */

public class ViewPastWorkoutAboutActivity extends MainActivity {

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
        setContentView(R.layout.activity_about_view_past_workout);
        setTitle("About View Past Workout");
    }

    /* Remove the triple-dot icon from the top of the application */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {return false; }
}
