package edu.calvin.calvinfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/*
This activity gives the user the option to create a new workout. This activity shows up when the
"Create New Workout" button is clicked from the MainActivity.
 */
public class Create_New_Workout_Activity extends AppCompatActivity {

    // Declare variables
    private Spinner default_exercises_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__new__workout_);
        Intent intent = getIntent();
        setTitle("Create New Workout");

        default_exercises_spinner = (Spinner) findViewById(R.id.SelectExercise_Spinner);

        //Adapter for new exercise spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.quick_exercise_array, android.R.layout.simple_spinner_item
        );

        // Assign the dropdown items in the default exercise spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        default_exercises_spinner.setAdapter(adapter);
        default_exercises_spinner.setSelection(0);
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
                startActivity(new Intent(getApplicationContext(), CreateWorkoutAboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
