package edu.calvin.calvinfitness;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

        //new GetUsersTask().execute(createURL());
    }

    /*
     * ShareWorkout() is called when the user clicks the "Share Workout" button on this activity
     *
     * @param: view
     * @return: none
     */
    public void ShareWorkout(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Workout Shared";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*
     * Formats a URL for the webservice specified in the string resources.
     *
     * @param none
     * @return URL formatted for http://cs262.cs.calvin.edu:8081/fitness/users/
     *
     * Altered from Lab06
     */
    private URL createURL() {
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
        * @param: JSONArray player
        * @return: none
        */
        @Override
        protected void onPostExecute(JSONArray user) {
            if (user != null) {

                convertJSONtoArrayList(user);
                updateDisplay();

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
    private void updateDisplay() {

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
