package edu.calvin.calvinfitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

/*
The Start Workout activity gives users the ability to start and fill in data for a workout. This
activity is shown when the "Start Workout" button is clicked from MainActivity.
 */
public class Start_Workout_Activity extends AppCompatActivity {

    //Define Variables
    private Spinner select_workout_spinner;
    private Button start_workout_button;
    private List<String> workout_names;
    private List<Exercise> exerciseList;
    private ListView itemListView;
    private TextView exercise_name_TextView;
    private ListView viewWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__workout_);
        Intent intent = getIntent();
        setTitle("Select Workout");

        select_workout_spinner = (Spinner) findViewById(R.id.select_workout_spinner);
        start_workout_button = (Button) findViewById(R.id.start_workout_button);
        exercise_name_TextView = (TextView) findViewById(R.id.exercise_name_TextView);
        itemListView = (ListView) findViewById(R.id.Exercise_List_View);

        // Read in the list of workouts currently stored for the user
        final List<Workout> prevWorkouts = new Workout_Reader().read(this, Constants.STANDARD_FILE);
        System.out.println(prevWorkouts);
        workout_names = new ArrayList<String>();
        for(int i = 0; i < prevWorkouts.size(); i++) {
            String name = prevWorkouts.get(i).getWorkout_name();
            workout_names.add(name);
        }

        //Adapter for saved spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, workout_names
        );

        // Assign the dropdown items in the workout spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_workout_spinner.setAdapter(adapter);
        select_workout_spinner.setSelection(0);


        //when start workout button is clicked.
        start_workout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = select_workout_spinner.getSelectedItem().toString();
                for(Workout temp: prevWorkouts) {
                    if (temp.getWorkout_name() == name) {
                        Start_Workout_Activity.this.updateDisplay(temp);
                    }
                }
            }
        });



        //create workout in ActivityMain only once



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
                startActivity(new Intent(getApplicationContext(), HomePageAboutActivity.class));
                return true;
            //case R.id.help_page_about:
            //    startActivity(new Intent(getApplicationContext(), StartWorkoutAboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDisplay(Workout temp) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        exercise_name_TextView.setText(temp.getWorkout_name());

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
        itemListView.setAdapter(adapter);
    }
}
