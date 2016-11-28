package edu.calvin.calvinfitness;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by Mitch Stark on 10/24/16.
 *
 * Workout_Reader reads in workouts from the given file name
 */

public class Workout_Reader {

    /*
     * Constructor for the Workout_Reader class
     *
     * @param: context
     * @param: file_name
     * @return: none
     */
    public List<Workout> read(Context context, String file_name) {

        Gson gson = new GsonBuilder().create();
        List<Workout> workouts = new ArrayList<>();

        try {
            FileInputStream reader = context.openFileInput(file_name);
            int content;
            String data = "";
            while ((content = reader.read()) != -1) {
                // convert to char
                data += ((char) content);
            }
            System.out.println("Read file data: " + data);
            JSONArray list = new JSONArray(data);
            for(int i = 0; i < list.length(); i++) {
                JSONObject jObj = list.getJSONObject(i);
                Workout temp = gson.fromJson(jObj.toString(), Workout.class);
                workouts.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Unsuccessful reading of [" + file_name + "]: " + e.toString());
        }

        return workouts;

    }
}
