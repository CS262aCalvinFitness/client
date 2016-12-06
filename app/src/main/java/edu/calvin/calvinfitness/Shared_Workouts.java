package edu.calvin.calvinfitness;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
 * This activity allows the user to share a workout with another user. This activity is shown
 *      when the "Shared Workouts" button is clicked on MainActivity.
 *
 * @param: none
 * @return: none
 */
public class Shared_Workouts extends AppCompatActivity {

    // Define variables
    private final List<String> userList = new ArrayList<>();
    private Spinner users_spinner;
    private List<Exercise> exerciseList;
    private Spinner workout_spinner;
    //private final List<Workout> sharedWorkouts;
    private Context context = this;
    private ListView itemsListView;
    private TextView workout_name_TextView;

    /*
     * onCreate() overrides the default onCreate() and sets the layout to be
     *      activity_shared__workouts activity.
     *
     * @param: savedInstanceState
     * @return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared__workouts);

        //new GetUsersTask().execute(createURLusers());
        //new GetSharedWorkoutsTask().execute(createURLworkouts());

        /*
        // Read in the list of shared workouts currently stored for the user
        sharedWorkouts = new Workout_Reader().read(this, Constants.SHARE_FILE);
        System.out.println(sharedWorkouts);
        for(int i = 0; i < sharedWorkouts.size(); i++) {
            String name = sharedWorkouts.get(i).getWorkout_name();
            workout_names.add(name);
        }
        */

        itemsListView = (ListView) findViewById(R.id.shared_workout_exercise);
        workout_name_TextView = (TextView) findViewById(R.id.SharedWorkoutName);
        workout_spinner = (Spinner) findViewById(R.id.workout_spinner);
    }

    /*
     * SaveWorkout() is called when the user clicks the "Save Workout" button on this activity
     *
     * @param: view
     * @return: none
     */
    public void SaveWorkout(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Workout Saved to Start Workout List";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*
    * ViewWorkout() is called when the user clicks the "View Workout" button on this activity
    *
    * @param: view
    * @return: none
    */
    public void ViewWorkout(View view) {
        Context context = getApplicationContext();
        CharSequence text = "View Workout: " + workout_spinner.getSelectedItem().toString();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        /*
        for (Workout temp: sharedWorkouts) {
            if (temp.getWorkout_name() == name) {
                See_Previous_Workouts_Activity.this.updateDisplay(temp);
            }
         }
         */

    }

    /*
    * updateDisplay() updates the ListView in the xml file to display all the exercises from the selected
    *      workout from the dropdown menu when the user presses the view past workout button
    *
    * @param: none
    * @return: none
    */
    private void updateDisplay(Workout temp) {
        ArrayList<HashMap<String, String>> data = new ArrayList<>();

        workout_name_TextView.setText(temp.getWorkout_name());

        exerciseList = temp.getExercise_list();
        for (Exercise item : exerciseList) {
            HashMap<String, String> map = new HashMap<>();
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
    * Formats a URL for the webservice specified in the string resources.
    *
    * @param none
    * @return URL formatted for http://cs262.cs.calvin.edu:8081/fitness/sharedworkouts/
    *
    * Altered from Lab06
    */
    private URL createURLworkouts() {
        try {
            String urlString;

            urlString = "http://cs262.cs.calvin.edu:8081/fitness/sharedworkouts";

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
                Toast.makeText(Shared_Workouts.this, "Failed to connect to service", Toast.LENGTH_SHORT).show();
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
     * Formats a URL for the webservice specified in the string resources.
     *
     * @param none
     * @return URL formatted for http://cs262.cs.calvin.edu:8081/fitness/users/
     *
     * Altered from Lab06
     */
    private URL createURLusers() {
        try {
            String urlString;

            urlString = "http://cs262.cs.calvin.edu:8081/fitness/users";

            return new URL(urlString);

        } catch (Exception e) {
            Toast.makeText(this, "Failed to make URL", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /*
     * GetUsersTask class established connection with the web server,
     *      and gets the JSON information from the server.
     */
    private class GetUsersTask extends AsyncTask<URL, Void, JSONArray> {

        /*
         * doInBackground() connects to the appropriate server when an instance of GetUsersTask
         *      is created (is done in the background obviously)
         *
         * @param: URL... params
         * @return JSONArray
         */
        @Override
        protected JSONArray doInBackground(URL... params) {
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
                    return new JSONArray(result.toString());
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
        * onPostExecute() calls the necessary methods if a user was returned from
        *      the doInBackground() method above
        *
        * @param: JSONArray user
        * @return: none
        */
        @Override
        protected void onPostExecute(JSONArray user) {
            if (user != null) {

                convertJSONtoArrayList(user);
                updateDisplayUserList();

            } else {
                Toast.makeText(Shared_Workouts.this, "Failed to connect to service", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     * Converts the JSON user username information to an arraylist suitable for a dropdown menu
     *
     * @param user_obj
     * @return: none
     *
     * Altered from Lab06
     */
    private void convertJSONtoArrayList(JSONArray user_obj) {

        /* clear old user data */
        userList.clear();

        try {
            for (int i = 0; i < user_obj.length(); i++) {
                String name = user_obj.getString(1);
                userList.add(name);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * updateDisplay() adds the users just gotten from the server to the dropdown menu
     *
     * @param: none
     * @return: none
     *
     * Altered from Lab06
     */
    private void updateDisplayUserList() {

        /* Generate toast if userList is empty... */
        if (userList == null) {
            Toast.makeText(Shared_Workouts.this, "Failed to connect to service", Toast.LENGTH_SHORT).show();
        }

        //Adapter for past workouts spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, userList
        );


        // Create variables for the Spinner and the ListView of this .xml content
        users_spinner = (Spinner) findViewById(R.id.spinner);

        // Assign the dropdown items in the past workouts spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        users_spinner.setAdapter(adapter);
        users_spinner.setSelection(0);
    }
}
