package edu.calvin.calvinfitness;

import android.content.Context;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WorkoutReaderTest {
    private Context context;
    @Test
    public void write_files() throws Exception {
        Workout w1 = new Workout("Workout 1");
        w1.saveWorkout(context);

        List<Exercise> e2 = new ArrayList<>();
        e2.add(new Exercise("Exercise 1", 1, 1, 1));
        Workout w2 = new Workout(e2, "Workout 3");
        w2.saveWorkout(context);

        List<Exercise> e3 = new ArrayList<>();
        e3.add(new Exercise("Exercise 1", 1, 1, 1));
        e3.add(new Exercise("Exercise 2", 2, 2, 2));
        e3.add(new Exercise("Exercise 3", 3, 3, 3));
        Workout w3 = new Workout(e3, "Workout 3");
        w3.saveWorkout(context);
    }

    public void read_files() {
        List<Workout> test = new Workout_Reader().read(context);
        for(Workout w : test) {
            System.out.println("Workout: " + w.toString());
        }
    }

    public void clean_up() {
        try {
            FileOutputStream writer = context.openFileOutput("workouts.json", Context.MODE_PRIVATE);
            writer.write("".getBytes());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

}