package edu.calvin.calvinfitness;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * Created by Mitch Stark on 10/16/16.
 *
 * Workout class: holds information for each workout created by the user
 */

public class Workout {
    private List<Exercise> exercise_list;
    private String workout_name;
    private Date workout_date;
    private Boolean completed;

    /*
     * Constructor that takes List of exercises, name, and date
     *
     * @param: exercises
     * @param: name
     * @param: date
     * @return: none
     */
    public Workout(List<Exercise> exercises, String name, Date date) {
        exercise_list = exercises;
        workout_name = name;
        workout_date = date;
        completed = false;
    }

    /*
     * Constructor that takes List of exercises and name
     *
     * @param: exercises
     * @param: name
     * @return: none
     */
    public Workout(List<Exercise> exercises, String name) {
        exercise_list = exercises;
        workout_name = name;
        workout_date = Calendar.getInstance().getTime(); //sets the workout date as the current date
        completed = false;
    }

    /*
    * Constructor that takes List of exercises
    *
    * @param: name
    * @return: none
    */
    public Workout(String name) {
        exercise_list = new ArrayList<>();
        workout_name = name;
        workout_date = Calendar.getInstance().getTime();
        completed = false;
    }

    /*
     * getCompleted() checks if the workout has been completed
     *
     * @param: none
     * @return: completed (true or false)
     */
    public Boolean getCompleted() {return completed;}

    /*
     * toggleCompleted() changes the workout completed boolean to true or false
     *
     * @param: none
     * @return: none
     */
    public void toggleCompleted() {completed = !completed;}


    /*
     * addExercise() adds an Exercise object to the exercise_list
     *
     * @param: e - Exercise to be added to the list
     * @return: none
     */
    public void addExercise(Exercise e) {
        exercise_list.add(e);
    }

    /*
     * removeExercise() removes an Exercise object from the exercise_list
     *
     * @param: e - Exercise to be removed
     * @return: none
     */
    public void removeExercise(Exercise e) {
        if (exercise_list.contains(e)) {
            exercise_list.remove(exercise_list.indexOf(e));
        }
    }

    /*
     * getExercise_List() returns the list of exercises
     *
     * @param: none
     * @return: exercise_list
     */
    public List<Exercise> getExercise_list() {
        return exercise_list;
    }

    /*
     * setName() changes the name of the workout
     *
     * @param: newName
     * @return: none
     */
    public void setName(String newName) {
        workout_name = newName;
    }

    /*
     * getWorkout_name() returns the name of the workout
     *
     * @param: none
     * @return: workout_name
     */
    public String getWorkout_name() {
        return this.workout_name;
    }


    /*
     * saveWorkout() saves the workout to the given file_name located in the local device/emulator
     *
     * @param: context
     * @param: file_name
     * @return: none
     */
    public void saveWorkout(Context context, String file_name) {
        //create check for the exact same workout
        System.out.println("Made it here");
        List<Workout> prevWorkouts = new Workout_Reader().read(context, file_name);
        System.out.println("Passed the test");
        String dataString = "[";
        try {
            FileOutputStream writer = context.openFileOutput(file_name, Context.MODE_PRIVATE);
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

    /*
     * setDate() sets the date that the workout was completed
     *
     * @param: none
     * @return: none
     */
    public void setDate() {
        workout_date = Calendar.getInstance().getTime();
    }

    /*
     * getDate() returns the date that the workout was completed
     *
     * @param: none
     * @return: workout_date
     */
    public Date getDate() {return workout_date;}
}
