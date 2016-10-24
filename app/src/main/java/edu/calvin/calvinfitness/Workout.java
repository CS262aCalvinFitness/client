package edu.calvin.calvinfitness;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.File;

/**
 * Created by mitchstark on 10/16/16.
 * Workout class: holds information for each workout created by the user
 */

public class Workout {
    private List<Exercise> exercise_list;
    private String workout_name;
    private Date workout_date;

    //constructor that includes a specified date
    public Workout(List<Exercise> exercises, String name, Date date) {
        exercise_list = exercises;
        workout_name = name;
        workout_date = date;
    }

    //constructor that uses the current date
    public Workout(List<Exercise> exercises, String name) {
        exercise_list = exercises;
        workout_name = name;
        workout_date = Calendar.getInstance().getTime(); //sets the workout date as the current date
    }

    //constructor with only the name
    public Workout(String name) {
        exercise_list = new ArrayList<>();
        workout_name = name;
        workout_date = Calendar.getInstance().getTime();
    }

    /**
     * @param e - Excercise to be added to the list
     */
    public void addExercise(Exercise e) {
        exercise_list.add(e);
    }

    /**
     * @param e - Excercise to be removed
     */
    public void removeExercise(Exercise e) {
        if (exercise_list.contains(e)) {
            exercise_list.remove(exercise_list.indexOf(e));
        }
    }

    /**
     * @param newName - set the name of the workout
     */
    public void setName(String newName) {
        workout_name = newName;
    }

    //save, import, and export methods to be implemented here

    public void saveWorkout(Context context) {
        //create check for the exact same workout
        List<Workout> prevWorkouts = new Workout_Reader().read(context);
        String dataString = "[";
        try {
            FileOutputStream writer = context.openFileOutput("workouts.json", Context.MODE_PRIVATE);
            Gson gson = new GsonBuilder().create();
            for(Workout w : prevWorkouts) {
                dataString += gson.toJson(w) + ", ";
            }
            dataString += gson.toJson(this) + "]";
            writer.write(dataString.getBytes());

        } catch (IOException e) {
            System.out.println("***ERROR*** could not print workout. " + e.toString());
        }
    }
}
