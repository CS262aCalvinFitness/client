package edu.calvin.calvinfitness;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * This activity shows the user the previous workouts that they have completed. This activity is shown
 *      when the "See Previous Workouts" button is clicked on MainActivity.
 *
 * @param: none
 * @return: none
 */
public class See_Previous_Workouts_Activity extends AppCompatActivity {

    //Define Variables
    private Spinner past_workout_spinner;
    private List<Exercise> exerciseList;
    private ListView itemsListView;
    private List<String> workout_names;
    private TextView workout_name_TextView;
    private Context context = this;

    /*
     * onCreate() overrides the default onCreate() and sets activity_see__previous__workouts
     *         to be the layout.
     *
     * It sets the variables in the two dropdown menus in the activity.
     *
     * @param: savedInstanceState
     * @return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the appropriate content (.xml) for the content
        setContentView(R.layout.activity_see__previous__workouts_);
        setTitle("View Past Workout Results");

        //new GetSharedWorkoutsTask().execute(createURL());

        // Read in the list of completed workouts currently stored for the user
        final List<Workout> prevWorkouts = new Workout_Reader().read(this, Constants.COMPLETED_FILE);
        System.out.println(prevWorkouts);
        workout_names = new ArrayList<String>();
        for(int i = 0; i < prevWorkouts.size(); i++) {
            String name = prevWorkouts.get(i).getWorkout_name();
            workout_names.add(name);
        }

        /*
        // Read in the list of shared workouts currently stored for the user
        final List<Workout> sharedWorkouts = new Workout_Reader().read(this, Constants.SHARE_FILE);
        System.out.println(sharedWorkouts);
        for(int i = 0; i < sharedWorkouts.size(); i++) {
            String name = sharedWorkouts.get(i).getWorkout_name();
            workout_names.add(name);
        }
        */

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

        // Get access to the View Workout button and set the onClickListener()
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

                /*
                for (Workout temp: sharedWorkouts) {
                    if (temp.getWorkout_name() == name) {
                        See_Previous_Workouts_Activity.this.updateDisplay(temp);
                    }
                }
                */

            }
        });
    }

    /*
     * Formats a URL for the webservice specified in the string resources.
     *
     * @param none
     * @return URL formatted for http://cs262.cs.calvin.edu:8081/fitness/sharedworkouts/
     *
     * Altered from Lab06
     */
    private URL createURL() {
        try {
            String urlString;

            urlString = "http://cs262.cs.calvin.edu:8081/fitness/sharedworkouts/2";

            return new URL(urlString);

        } catch (Exception e) {
            Toast.makeText(this, "Failed to make URL", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /*
     * GetSharedWorkoutsTask class established connection with the web server,
     *      and gets the JSON information from the server.
     */
    private class GetSharedWorkoutsTask extends AsyncTask<URL, Void, JSONObject> {

        /*
         * doInBackground() connects to the appropriate server when an instance of GetSharedWorkoutsTask
         *      is created (is done in the background obviously)
         *
         * @param: URL... params
         * @return JSONObject
         */
        @Override
        protected JSONObject doInBackground(URL... params) {
            HttpURLConnection connection = null;
            StringBuilder result = new StringBuilder();
            try {
                connection = (HttpURLConnection) params[0].openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return new JSONObject(result.toString());
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                assert connection != null;
                connection.disconnect();
            }
            return null;
        }
        /*
        * onPostExecute() calls the necessary methods if a workout was returned from
        *      the doInBackground() method above
        *
        * @param: JSONObject workout
        * @return: none
        */
        @Override
        protected void onPostExecute(JSONObject workout) {
            if (workout != null) {

                convertJSON(workout);

            } else {
                Toast.makeText(See_Previous_Workouts_Activity.this, "Failed to connect to service", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    * Converts the JSON workout information to a workout and saves the workouts to the shared_file
    *
    * @param workout
    * @return: none
    *
    * Altered from Lab06
    */
    private void convertJSON(JSONObject workout) {

        try {
            Workout workout_one = new Workout(workout.getString("workout_name"));
            JSONArray exercises = workout.getJSONArray("exercise_list");
            for (int i = 0; i < exercises.length(); i++) {
                JSONObject object = exercises.getJSONObject(i);
                Exercise temp_exercise = new Exercise(object.getString("Name"), object.getInt("Reps"), object.getInt("Sets"),
                            object.getInt("Weight"));
                workout_one.addExercise(temp_exercise);
            }
            workout_one.saveWorkout(context, Constants.SHARE_FILE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
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

    /*
     * onOptionsItemSelected performs an action if an menu item is selected
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
                startActivity(new Intent(getApplicationContext(), ViewPastWorkoutHelpActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * updateDisplay() updates the ListView in the xml file to display all the exercises from the selected
     *      workout from the dropdown menu when the user presses the view past workout button
     *
     * @param: none
     * @return: none
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
