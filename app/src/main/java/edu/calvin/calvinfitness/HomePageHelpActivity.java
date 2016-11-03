package edu.calvin.calvinfitness;

import android.os.Bundle;
import android.view.Menu;

/**
 * Created by ethanclark on 11/1/16.
 */

public class HomePageHelpActivity extends MainActivity {

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
        setContentView(R.layout.activity_home_page_help);
        setTitle("CalvinFitness Help");
    }

    /* Remove the triple-dot icon from the top of the application */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {return false; }
}
