package edu.calvin.calvinfitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/*
The Start Workout activity gives users the ability to start and fill in data for a workout. This
activity is shown when the "Start Workout" button is clicked from MainActivity.
 */
public class Start_Workout_Activity extends AppCompatActivity {

    //Define Variables
    //private Spinner quick_workout_spinner;
    private Spinner select_workout_spinner;
    private Button start_workout_button;
    private String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__workout_);
        Intent intent = getIntent();
        setTitle("Select Workout");

        //quick_workout_spinner = (Spinner) findViewById(R.id.quick_workout_spinner);
        select_workout_spinner = (Spinner) findViewById(R.id.select_workout_spinner);
        start_workout_button = (Button) findViewById(R.id.start_workout_button);

        //Adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.quick_workout_array, android.R.layout.simple_spinner_item
        );

        // Assign the dropdown items in the workout spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_workout_spinner.setAdapter(adapter);
        select_workout_spinner.setSelection(0);

        //Adapter for saved spinner
        ArrayAdapter<CharSequence> saved_adapter = ArrayAdapter.createFromResource(
                this,R.array.saved_workout_array, android.R.layout.simple_spinner_item
        );

        // Assign the dropdown items in the saved workouts spinner
        saved_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_workout_spinner.setAdapter(saved_adapter);
        select_workout_spinner.setSelection(0);
        //wont' need this? ^^


        //when start workout button is clicked.
        start_workout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutName = select_workout_spinner.getSelectedItem().toString();
            }
            //read json files here ####################
        });



        //create workout in ActivityMain only once

        //user Workout_Reader.java

        

        /*
        read in JSON file to break down exercise name, weight, set rep
        list view that loads up reps, weight and sets
        start workout button with selected workout from spinner
         */
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
