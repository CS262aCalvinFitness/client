package edu.calvin.calvinfitness;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/*
This is the entry point of the app. It enters into the Main Activity, giving three options for
where to go, as well as a welcome label.
 */
public class MainActivity extends AppCompatActivity {

    //private List<Exercise> exercise_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  exercise_list.add(new Exercise("Squat", 12, 3, 100));
        //Workout workout_example = new Workout(exercise_list, "Example Workout");

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
            case R.id.help_page_about:
                startActivity(new Intent(getApplicationContext(), HomePageHelpActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
