package edu.calvin.calvinfitness;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Create_User_Activity extends AppCompatActivity {

    private EditText user_id;
    private String new_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__user_);

        user_id = (EditText) findViewById(R.id.User_ID);
        Button create_user = (Button) findViewById(R.id.button);
        final EditText enter_password = (EditText) findViewById(R.id.password);
        final EditText confirm_password = (EditText) findViewById(R.id.confirmPassword);

        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enter_password.getText().toString().equals(confirm_password.getText().toString())) {
                    new_username = user_id.getText().toString();
                    System.out.println(new_username);
                    Constants.USERNAME = new_username;
                    new PostUserTask().execute(createURLaddUser());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Context context1 = getApplicationContext();
                    Toast toast1 = Toast.makeText(context1, "Passwords are not the same", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }
        });
    }

    /*
     * Formats a URL for the webservice specified in the string resources.
     *
     * @param none
     * @return URL formatted for the application calvinfitness server
     */
    private URL createURLaddUser() {
        try {
            String urlString;

            urlString = "http://cs262.cs.calvin.edu:8081/fitness/users";

            return new URL(urlString);

        } catch (Exception e) {
            Toast.makeText(this, "Failed to make URL", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /*
     * Inner class for POSTING new Users
     */
    private class PostUserTask extends AsyncTask<URL, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(URL... params) {
            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            JSONArray result = null;
            try {
                // Hard-code a new player using JSON.
                JSONObject jsonData = new JSONObject();
                System.out.println(new_username);
                jsonData.put("Username", new_username);
                System.out.println(jsonData);
                // Open the connection as usual.
                connection = (HttpURLConnection) params[0].openConnection();
                // Configure the connection for a POST, including outputing streamed JSON data.
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setFixedLengthStreamingMode(jsonData.toString().length());
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(jsonData.toString());
                out.flush();
                out.close();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonText.append(line);
                    }
                    //Log.d(TAG, jsonText.toString());
                    if (jsonText.charAt(0) == '[') {
                        result = new JSONArray(jsonText.toString());
                    } else if (jsonText.toString().equals("null")) {
                        result = new JSONArray();
                    } else {
                        result = new JSONArray().put(new JSONObject(jsonText.toString()));
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
                return result;
        }
        @Override
        protected void onPostExecute(JSONArray user) {
            Context context1 = getApplicationContext();
            Toast new_toast = Toast.makeText(context1, "User has been created", Toast.LENGTH_LONG);
            new_toast.show();
        }
    }
}
