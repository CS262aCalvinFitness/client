package edu.calvin.calvinfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

/*
This activity shows the user the previous workouts that they have completed. This activity is shown
when the "See Previous Workouts" button is clicked on MainActivity.
 */
public class See_Previous_Workouts_Activity extends AppCompatActivity {

    //Define Variables
    private Spinner past_workout_spinner;
    private List<Exercise> exerciseList;
    private ListView itemsListView;
    private List<String> workout_names;
    private TextView workout_name_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the appropriate content (.xml) for the content
        setContentView(R.layout.activity_see__previous__workouts_);
        setTitle("View Past Workout Results");

        // Read in the list of workouts currently stored for the user
        final List<Workout> prevWorkouts = new Workout_Reader().read(this, Constants.COMPLETED_FILE);
        System.out.println(prevWorkouts);
        workout_names = new ArrayList<String>();
        for(int i = 0; i < prevWorkouts.size(); i++) {
            String name = prevWorkouts.get(i).getWorkout_name();
            workout_names.add(name);
        }

        // Create variables for the Spinner and the ListView of this .xml content
        past_workout_spinner = (Spinner) findViewById(R.id.previous_result_spinner);
        itemsListView = (ListView) findViewById(R.id.Exercise_List_View);
        workout_name_TextView = (TextView) findViewById(R.id.Workout_Name_Text);

        //Adapter for past workouts spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, workout_names
        );

        // Assign the dropdown items in the past workouts spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        past_workout_spinner.setAdapter(adapter);
        past_workout_spinner.setSelection(0);

        Button view_workout_button = (Button) findViewById(R.id.view_workout_button);
        view_workout_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = past_workout_spinner.getSelectedItem().toString();
                for(Workout temp: prevWorkouts) {
                    if (temp.getWorkout_name() == name) {
                        See_Previous_Workouts_Activity.this.updateDisplay(temp);
                    }
                }
            }
        });
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
                startActivity(new Intent(getApplicationContext(), HomePageAboutActivity.class));
                return true;
            case R.id.help_page_about:
                startActivity(new Intent(getApplicationContext(), ViewPastWorkoutAboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * updateDisplay() updates the ListView in the xml file to display all the exercises from the selected
     *      workout from the dropdown menu when the user presses the view past workout button
     *
     * @param: NONE
     * @return: NONE
     */
    private void updateDisplay(Workout temp) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        workout_name_TextView.setText(temp.getWorkout_name());

        exerciseList = temp.getExercise_list();
        for (Exercise item : exerciseList) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", item.getName());
            map.put("sets", Integer.toString(item.getSets()));
            map.put("reps", Integer.toString(item.getReps()));
            map.put("weight", Integer.toString(item.getWeights()));
            data.add(map);
        }

        int resource = R.layout.exercise_item;
        String[] from = {"name", "sets", "reps", "weight"};
        int[] to = {R.id.Exercise_Name, R.id.Number_Sets, R.id.Number_Reps, R.id.Number_Weight};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        itemsListView.setAdapter(adapter);
    }
}
