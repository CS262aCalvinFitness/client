package edu.calvin.calvinfitness;

/*
 * Created by Mitch Stark and Ethan Clark on 11/7/16.
 *
 * Constants contains the names of the three files that are used to store workouts for this application
 *      It also contains constant variables such as USER_ID for the Find Shared Workouts activity,
 *      USERNAME and USER_ID_DATABASE to store the current users information, WORKOUT_ID to be used when
 *      sharing a workout, and CURRENT_EXERCISE to keep track when sharing exercises for the shared workout.
 */

public class Constants {
    static final String STANDARD_FILE = "workouts.json";
    static final String COMPLETED_FILE = "completed_workouts.json";
    static final String SHARE_FILE = "shared_workouts.json";
    static Integer USER_ID = -1;
    static String USERNAME;
    static Integer USER_ID_DATABASE;
    static Integer WORKOUT_ID;
}
