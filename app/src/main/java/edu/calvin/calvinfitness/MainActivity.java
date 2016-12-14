package edu.calvin.calvinfitness;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * This is the entry point of the app after the user login. It enters into the Main Activity, giving three options for
 *      where to go, as well as a welcome label.
 *
 * @param: none
 * @return: none
 */
public class MainActivity extends AppCompatActivity {

    /*
     * onCreate() overrides the default onCreate() and it sets the activity_main layout
     *
     * @param: savedInstanceState
     * @return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetUsersTask().execute(createURLusers());

        /*
        // This code allows us to remove the workouts stored on the local emulator
        try {
            FileOutputStream writer = openFileOutput(Constants.COMPLETED_FILE, Context.MODE_PRIVATE);
            writer.write("".getBytes());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        */
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

                for (Integer i = 0; i < user.length(); i++) {
                    try {
                        JSONObject User = user.getJSONObject(i);
                        String username = User.getString("Username");
                        if (username.equals(Constants.USERNAME)) {
                            Constants.USER_ID_DATABASE = User.getInt("ID");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                Toast.makeText(MainActivity.this, "Failed to connect to service", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     * Called when user clicks on start_workout_button
     *
     * @param View - the view that has been clicked
     * @return - void
     */
    public void StartWorkout(View view){
        Intent intent = new Intent(this, Start_Workout_Activity.class);
        startActivity(intent);
    }

    /*
     * Called when the user clicks on the create_Workout_button
     *
     * @param View - the view that has been clicked
     * @return - void
     */
    public void CreateNewWorkout(View view){
        Intent intent = new Intent(this, Create_New_Workout_Activity.class);
        startActivity(intent);
    }

    /*
     * Called when the user clicks on the past_workout_button
     *
     * @param View - the view that has been clicked
     * @return - void
     */
    public void PastWorkout(View view){
        Intent intent = new Intent(this, See_Previous_Workouts_Activity.class);
        startActivity(intent);
    }

    /*
     * Called when the user clicks on the past_workout_button
     *
     * @param View - the view that has been clicked
     * @return - void
     */
    public void SharedWorkout(View view){
        Intent intent = new Intent(this, Shared_Workouts.class);
        startActivity(intent);
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
                startActivity(new Intent(getApplicationContext(), HomePageHelpActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
