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
 * Created by mitchstark on 10/24/16.
 */

public class Workout_Reader {
    public List<Workout> read(Context context) {
        Gson gson = new GsonBuilder().create();
        List<Workout> workouts = new ArrayList<>();
        try {
            FileInputStream reader = context.openFileInput("workouts.json");
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
            System.out.println("Unsuccessful reading of [workouts.json]: " + e.toString());
        }
        return workouts;

    }
}
