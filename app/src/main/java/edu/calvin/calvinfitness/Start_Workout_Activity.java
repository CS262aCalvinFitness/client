package edu.calvin.calvinfitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/*
The Start Workout activity gives users the ability to start and fill in data for a workout. This
activity is shown when the "Start Workout" button is clicked from MainActivity.
 */
public class Start_Workout_Activity extends AppCompatActivity {

    //Define Variables
    private Spinner quick_workout_spinner;
    private Spinner saved_workout_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__workout_);
        Intent intent = getIntent();
        setTitle("Select Workout");

        quick_workout_spinner = (Spinner) findViewById(R.id.quick_workout_spinner);
        saved_workout_spinner = (Spinner) findViewById(R.id.saved_workout_spinner);

        //Adapter for quick spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.quick_workout_array, android.R.layout.simple_spinner_item
        );

        // Assign the dropdown items in the quick workout spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quick_workout_spinner.setAdapter(adapter);
        quick_workout_spinner.setSelection(0);

        //Adapter for saved spinner
        ArrayAdapter<CharSequence> saved_adapter = ArrayAdapter.createFromResource(
                this,R.array.saved_workout_array, android.R.layout.simple_spinner_item
        );

        // Assign the dropdown items in the saved workouts spinner
        saved_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        saved_workout_spinner.setAdapter(saved_adapter);
        saved_workout_spinner.setSelection(0);

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
                startActivity(new Intent(getApplicationContext(), StartWorkoutAboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
