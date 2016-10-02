package edu.calvin.calvinfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*called when user clicks on start_workout_button*/
    public void StartWorkout(View view){
        Intent intent = new Intent(this, Start_Workout_Activity.class);
        startActivity(intent);
    }

    /*called when the user clicks on the create_Workout_button*/
    public void CreateNewWorkout(View view){
        Intent intent = new Intent(this, Create_New_Workout_Activity.class);
        startActivity(intent);
    }

    /*called when the user clicks on the past_workout_button*/
    public void PastWorkout(View view){
        Intent intent = new Intent(this, See_Previous_Workouts_Activity.class);
        startActivity(intent);
    }
}
