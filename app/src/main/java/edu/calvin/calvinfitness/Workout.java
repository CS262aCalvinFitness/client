package edu.calvin.calvinfitness;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mitchstark on 10/16/16.
 * Workout class: holds information for each workout created by the user
 */

public class Workout {
    private List<Exercise> exercise_list;
    private String workout_name;
    private Date workout_date;
    //todo : add completed boolean

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
}
