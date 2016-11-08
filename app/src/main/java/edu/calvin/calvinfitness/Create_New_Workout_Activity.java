package edu.calvin.calvinfitness;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/*
* NOTES FOR PERSON DOING SAVE!!!!!
* add the list of exercises to a workout and save the workout to a gson.
*
*
* */
/*
This activity gives the user the option to create a new workout. This activity shows up when the
"Create New Workout" button is clicked from the MainActivity.
 */
public class Create_New_Workout_Activity extends AppCompatActivity {

    // Declare variables
    private Spinner Set_spinner;
    private List <String> set_list = new ArrayList<String>();
    private Spinner Rep_spinner;
    private List <String> rep_list = new ArrayList<String>();
    private Spinner Weight_spinner;
    private List <String> weight_list = new ArrayList<String>();
    private String nameOfExercise;
    private int weightOfExercise;
    private String weightText;
    private int setOfExercise;
    private int repsOfExercise;
    private List <Exercise> listOfExc = new ArrayList<Exercise>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__new__workout_);
        Intent intent = getIntent();
        setTitle("Create New Workout");
        //set spinner to spinner widgets
        Rep_spinner = (Spinner) findViewById(R.id.Number_of_reps_spinner);
        Set_spinner = (Spinner) findViewById(R.id.num_of_sets_spinner);
        Weight_spinner = (Spinner) findViewById(R.id.weight_spinner);
       //sets defualt value for spinners
        rep_list.add(0, "Reps");
        set_list.add(0, "Sets");
        weight_list.add(0, "Weight");
        // add items to rep list and set list
        for(int i = 1; i <= 25; i++){
            set_list.add(i,Integer.toString(i));
            rep_list.add(i, Integer.toString(i));
        }
        //add items to weight list
        for(int i = 1;i <= 500; i++ ){
            weight_list.add(i, Integer.toString(i));
        }

        //Adapter for set spinner
        ArrayAdapter<String> Set_adapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, set_list
        );
        //Adapter for rep spinner
        ArrayAdapter<String> Rep_adapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, rep_list
        );
        //Adapter for weight spinner
        ArrayAdapter<String> Weight_adapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, weight_list
        );

        // Assign the dropdown items in the set spinner
        Set_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Set_spinner.setAdapter(Set_adapter);
        Set_spinner.setSelection(0);
        // Assign the dropdown items in the rep spinner
        Rep_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Rep_spinner.setAdapter(Rep_adapter);
        Rep_spinner.setSelection(0);
        // assign the dropdown items in the weight spinner
        Weight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Weight_spinner.setAdapter(Weight_adapter);
        Weight_spinner.setSelection(0);
        // buttons and text fields being connected to vars
        Button addToWorkout = (Button) findViewById(R.id.Add_exercise_button);
        final EditText exerciseName = (EditText) findViewById(R.id.Exercise_name_edit);
        EditText workoutName = (EditText) findViewById(R.id.WorkoutName_Edit);
        final TextView errorText = (TextView) findViewById(R.id.error_text_field);
        final TextView exerciseInWorkout = (TextView) findViewById(R.id.Exercises_Text_Field);
        errorText.setVisibility(View.INVISIBLE);
        addToWorkout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the user did not select or fill in a field then the error message is displayed
                if(Rep_spinner.getSelectedItemPosition() == 0 || Set_spinner.getSelectedItemPosition() == 0 ||
                        Weight_spinner.getSelectedItemPosition() == 0){
                    errorText.setVisibility(View.VISIBLE);
                } else{
                    //gets the name of the exercise
                    nameOfExercise = exerciseName.getText().toString();
                    //gets the weight set and rep count
                    weightOfExercise = Weight_spinner.getSelectedItemPosition();
                    repsOfExercise = Rep_spinner.getSelectedItemPosition();
                    setOfExercise = Set_spinner.getSelectedItemPosition();
                    //adds the exercise to a list of exercises so that adding to a workout is easy
                    listOfExc.add(new Exercise(nameOfExercise, repsOfExercise, setOfExercise,weightOfExercise));
                    //hides error text if it was previusly displayed
                    errorText.setVisibility(View.INVISIBLE);
                    //puts the exercise into the textView field so user can see previusly entered exercises
                    exerciseInWorkout.append(nameOfExercise + " " + setOfExercise + " x " + repsOfExercise +
                            " " + weightOfExercise +"lbs. \n" );
                    //resets the fields making it easier on the user
                    exerciseName.setText("");
                    Weight_spinner.setSelection(0);
                    Rep_spinner.setSelection(0);
                    Set_spinner.setSelection(0);
                }
            }
        });

        final Context context = this;
        Button button = (Button) findViewById(R.id.save_new_workout);
        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText editText = (EditText)findViewById(R.id.WorkoutName_Edit);
                Workout w = new Workout(listOfExc, editText.getText().toString());
                w.saveWorkout(context, Constants.STANDARD_FILE);

                Context context = getApplicationContext();
                CharSequence text = "Workout Saved";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent mainIntent = new Intent(context, MainActivity.class);
                startActivity(mainIntent);
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
                startActivity(new Intent(getApplicationContext(), CreateWorkoutAboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
