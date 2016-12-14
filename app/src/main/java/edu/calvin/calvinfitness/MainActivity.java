package edu.calvin.calvinfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/*
 * This is the entry point of the app. It enters into the Main Activity, giving three options for
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
