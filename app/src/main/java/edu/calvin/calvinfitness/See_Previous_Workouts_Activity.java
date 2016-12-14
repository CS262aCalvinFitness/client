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
import java.io.DataOutputStream;
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
    private boolean empty_list = false;
    private String name;
    private List<Workout> prevWorkouts;

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

        // Read in the list of completed workouts currently stored for the user
        prevWorkouts = new Workout_Reader().read(this, Constants.COMPLETED_FILE);
        System.out.println(prevWorkouts);
        workout_names = new ArrayList<String>();
        for(int i = 0; i < prevWorkouts.size(); i++) {
            String name = prevWorkouts.get(i).getWorkout_name();
            workout_names.add(name);
        }

        // Set variable to check if the dropdown menu will be empty
        if (workout_names.isEmpty()) {
            empty_list = true;
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

        // Get access to the View Workout button and set the onClickListener()
        Button view_workout_button = (Button) findViewById(R.id.view_workout_button);
        view_workout_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (empty_list) {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "No workout selected", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    String name = past_workout_spinner.getSelectedItem().toString();
                    for (Workout temp : prevWorkouts) {
                        if (temp.getWorkout_name().equals(name)) {
                            See_Previous_Workouts_Activity.this.updateDisplay(temp);
                        }
                    }
                }
            }
        });

        // Get access to the Share Workout button and set the onClickListener()
        Button share_workout_button = (Button) findViewById(R.id.share_workout_button);
        share_workout_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Get the name of the selected workout
                name = past_workout_spinner.getSelectedItem().toString();

                // Share the appropriate workout to the database server
                new PostWorkoutTask().execute(createURL());

                // Take the user back to the MainActivity screen
                Context context = getApplicationContext();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
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

    /*
     * Formats a URL for the webservice cs262.cs.calvin.edu:8081/fitness/sharedworkouts
     *
     * @param: none
     * @return: URL formatted for the course fitness server
     */
    private URL createURL() {
        try {
            String urlString = "http://cs262.cs.calvin.edu:8081/fitness/sharedworkouts";
            return new URL(urlString);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to make URL", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /*
     * Inner class for POSTing a new, hard-coded workout to the course server asynchronously
     *
     * @param: URL and JSONArray
     */
    private class PostWorkoutTask extends AsyncTask<URL, Void, JSONArray> {

        /*
         * doInBackground is the tasks performed when an instance of PostWorkoutTask is created
         *
         * @param: URL
         * @return: JSONArray
         */
        @Override
        protected JSONArray doInBackground(URL... params) {

            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            JSONArray result = null;

            try {

                // Hard-code a new workout using JSON.
                JSONObject jsonWorkout = new JSONObject();
                for (Workout workout: prevWorkouts) {
                    if (workout.getWorkout_name().equals(name)) {
                        jsonWorkout.put("workout_name", workout.getWorkout_name());
                        jsonWorkout.put("userID", Constants.USER_ID_DATABASE);
                        break;
                    }
                }

                // Open the connection as usual.
                connection = (HttpURLConnection) params[0].openConnection();
                // Configure the connection for a POST, including outputing streamed JSON data.
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setFixedLengthStreamingMode(jsonWorkout.toString().length());
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(jsonWorkout.toString());
                out.flush();
                out.close();

                // Handle the response from the (Lab09) server as usual.
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonText.append(line);
                    }
                    //Log.d(TAG, jsonText.toString());
                    if (jsonText.charAt(0) == '[') {
                        result = new JSONArray(jsonText.toString());
                    } else if (jsonText.toString().equals("null")) {
                        result = new JSONArray();
                    } else {
                        result = new JSONArray().put(new JSONObject(jsonText.toString()));
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        /*
         * onPostExecute prompts for a Toast and then proceeds to create a new instance of
         *      PostExerciseTask to share each exercise to the server.
         *
         * @param: JSONArray
         * @return: none
         */
        @Override
        protected void onPostExecute(JSONArray workout) {

            // Create a Toast to provide information to the user
            Context context1 = getApplicationContext();
            Toast toast1 = Toast.makeText(context1, "Workout Shared", Toast.LENGTH_LONG);
            toast1.show();

            // Get the workout ID that was returned from the server in order to use for sharing the workout's exercises
            try {
                JSONObject temp = workout.getJSONObject(0);
                Constants.WORKOUT_ID = temp.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Iterate through the workouts exercise list, sharing each one to the database server
            for (Workout workout1: prevWorkouts) {
                if (workout1.getWorkout_name().equals(name)) {
                    for (Exercise exercise: workout1.getExercise_list()) {
                        new PostExerciseTask(exercise).execute(createURLexercise());
                    }
                }
            }
        }

    }

    /*
     * Formats a URL for the webservice cs262.cs.calvin.edu:8081/fitness/sharedworkouts/exercise
     *
     * @param: none
     * @return: URL formatted for the course fitness server
     */
    private URL createURLexercise() {
        try {
            String urlString = "http://cs262.cs.calvin.edu:8081/fitness/sharedworkouts/exercise";
            return new URL(urlString);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to make URL", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /*
     * Inner class for POSTing a new, hard-coded workout to the course server asynchronously
     *
     * @param: URL, JSONArray
     */
    private class PostExerciseTask extends AsyncTask<URL, Void, JSONArray> {

        final Exercise exercise_temp;

        public PostExerciseTask(Exercise exercise) {
            super();
            exercise_temp = exercise;
        }

        /*
         * doInBackground is the tasks performed when an instance of PostExerciseTask is created
         *
         * @param: URL
         * @return: JSONArray
         */
        @Override
        protected JSONArray doInBackground(URL... params) {

            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            JSONArray result = null;

            try {
                // Hard-code a new exercise using JSON.
                JSONObject jsonExercise = new JSONObject();
                jsonExercise.put("name", exercise_temp.getName());
                jsonExercise.put("reps", exercise_temp.getReps());
                jsonExercise.put("sets", exercise_temp.getSets());
                jsonExercise.put("weights", exercise_temp.getWeights());
                jsonExercise.put("workout_ID", Constants.WORKOUT_ID);

                // Open the connection as usual.
                connection = (HttpURLConnection) params[0].openConnection();
                // Configure the connection for a POST, including outputing streamed JSON data.
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setFixedLengthStreamingMode(jsonExercise.toString().length());
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(jsonExercise.toString());
                out.flush();
                out.close();

                // Handle the response from the (Lab09) server as usual.
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonText.append(line);
                    }
                    //Log.d(TAG, jsonText.toString());
                    if (jsonText.charAt(0) == '[') {
                        result = new JSONArray(jsonText.toString());
                    } else if (jsonText.toString().equals("null")) {
                        result = new JSONArray();
                    } else {
                        result = new JSONArray().put(new JSONObject(jsonText.toString()));
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        /*
         * onPostExecute simply outputs the exercise JSONArray that was returned from the server
         *
         * @param: JSONArray
         * @return: none
         */
        @Override
        protected void onPostExecute(JSONArray exercise) {
            System.out.println(exercise);
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
                startActivity(new Intent(getApplicationContext(), HelpFindingWorkouts.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
