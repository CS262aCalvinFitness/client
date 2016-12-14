package edu.calvin.calvinfitness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/*
 * Created by Ethan Clark on 12/13/16.
 *
 * This Activity is for the help page for the Find Shared Workouts Activity
 */

public class ViewSharedWorkoutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_find_shared_workouts);
        setTitle("Help: Finding Workouts");
    }

    /* Remove the triple-dot icon from the top of the application */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {return false; }
}
