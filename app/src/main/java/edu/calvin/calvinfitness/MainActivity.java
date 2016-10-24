package edu.calvin.calvinfitness;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Exchanger;

/*
This is the entry point of the app. It enters into the Main Activity, giving three options for
where to go, as well as a welcome label.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Workout test = new Workout("Test Workout");
//        test.saveWorkout(this);
        readSavedWorkouts();
    }

    /*called when user clicks on start_workout_button
    * @param View - the view that has been clicked
    * @return - void
    */
    public void StartWorkout(View view){
        Intent intent = new Intent(this, Start_Workout_Activity.class);
        startActivity(intent);
    }

    /*called when the user clicks on the create_Workout_button
    * @param View - the view that has been clicked
    * @return - void
    */
    public void CreateNewWorkout(View view){
        Intent intent = new Intent(this, Create_New_Workout_Activity.class);
        startActivity(intent);
    }

    /*called when the user clicks on the past_workout_button
    * @param View - the view that has been clicked
    * @return - void
    */
    public void PastWorkout(View view){
        Intent intent = new Intent(this, See_Previous_Workouts_Activity.class);
        startActivity(intent);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void readSavedWorkouts() {
        try {
            FileInputStream reader = openFileInput("workouts.json");
            int content;
            String data = "";
            while ((content = reader.read()) != -1) {
                // convert to char and display it
                data += ((char) content);
            }
            System.out.println("Read file data: " + data);
            JSONArray list = new JSONArray(data);
            for(int i = 0; i < list.length(); i++) {
                JSONObject j = list.getJSONObject(i);
                //create workout here with json object toString
            }
        } catch (Exception e) {
            System.out.println("Unsuccessful reading of [workouts.json]: " + e.toString());
        }

    }
}
