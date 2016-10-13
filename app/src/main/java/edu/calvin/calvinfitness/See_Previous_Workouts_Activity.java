package edu.calvin.calvinfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/*
This activity shows the user the previous workouts that they have completed. This activity is shown
when the "See Previous Workouts" button is clicked on MainActivity.
 */
public class See_Previous_Workouts_Activity extends AppCompatActivity {

    //Define Variables
    private Spinner past_workout_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see__previous__workouts_);
        Intent intent = getIntent();
        setTitle("View Past Workout Results");

        past_workout_spinner = (Spinner) findViewById(R.id.previous_result_spinner);

        //Adapter for past workouts spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.past_workouts_array, android.R.layout.simple_spinner_item
        );

        // Assign the dropdown items in the past workouts spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        past_workout_spinner.setAdapter(adapter);
        past_workout_spinner.setSelection(0);
    }

    /**
     * onCreateOptionsMenu creates the menu at the top of the page layout
     *
     * @param: menu
     * @return: true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_about, menu);
        return true;
    }

    /* onOptionsItemSelected performs an action if an menu item is selected
     *
     * @param: item
     * @return: true -> if About item is clicked
     *          super.onCreateItemsSelected(item) -> otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_page_about:
                startActivity(new Intent(getApplicationContext(), ViewPastWorkoutAboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
