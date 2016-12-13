package edu.calvin.calvinfitness;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * A login screen that offers login via email/password.
 *
 * Begun with the default login activity that Android Studio provides
 *      Modified it to meet the needs of CalvinFitness
 *
 * @param: none
 * @return: none
 */
public class LoginActivity extends AppCompatActivity  {

     // A string array of credentials containing known user names and passwords.
    private static final String[] CREDENTIALS = new String[100];

    // Keep track of the login task to ensure we can cancel it if requested.
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private JSONArray users;

    // Create an index to keep track of entering in the usernames and passwords
    //      to the CREDENTIALS String Array
    private Integer index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get all the users from the CalvinFitness server
        new GetUsersTask().execute(createURLusers());

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.User_ID);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // Get access to the sign in button and set the onClickListener
        Button mUsernameSignInButton = (Button) findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        // Get access to the LoginFormView and the ProgressViewForm
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /*
     * Called when user clicks on sign in button
     *
     * @param View - the view that has been clicked
     * @return - void
     */
    public void MainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*
     * Called when the user clicks on the Create User button
     *
     * @param: View view
     * @return: none
     */
    public void CreateUser(View view) {
        Intent intent = new Intent(this, Create_User_Activity.class);
        startActivity(intent);
    }

    /*
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        Constants.USER_ID = getUserId(username);

        // Set the cancel boolean value to be false and the focusView view to be null
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        /*
         * There was an error; don't attempt login and focus the first
         * form field with an error.
         */
        if (cancel) {
            focusView.requestFocus();
        }

        /*
         * Show a progress spinner, and kick off a background task to perform the user login attempt.
         */
        else {
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    /*
     * isPasswordValid() determines if the password the user entered is valid
     *
     * @param: String password
     * @return: boolean true/false
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /*
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /*
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        // Declare two private instance variables
        private final String mUsername;
        private final String mPassword;

        // Constructor for the UserLoginTask class
        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;

        }

        /*
         * doInBackground() works in the background when the user presses the login button
         *
         * @param: params
         * @return Boolean true/false
         */
        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : CREDENTIALS) {
                if (credential == null) {
                    break;
                }
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mUsername)) {
                    Constants.USERNAME = mUsername;
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }
            return false;
        }

        /*
         * onPostExecute() calls MainActivity if user entered valid username and password
         *      Otherwise gives an error message to the user
         *
         * @param: Boolean success
         * @return: none
         */
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                finish();
                MainActivity(mUsernameView);
            } else {
                mPasswordView.setError("Incorrect Username or Password");
                mPasswordView.requestFocus();
            }
        }

        /*
         * onCancelled() checks if at any point, the user cancels the login request
         *
         * @param: none
         * @return: none
         */
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /*
     * Formats a URL for the webservice specified in the string resources.
     *
     * @param none
     * @return URL formatted for http://cs262.cs.calvin.edu:8081/fitness/users/
     *
     * Altered from Lab06
     */
    private URL createURLusers() {
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
     * GetUsersTask class established connection with the web server,
     *      and gets the JSON information from the server.
     */
    private class GetUsersTask extends AsyncTask<URL, Void, JSONArray> {

        /*
         * doInBackground() connects to the appropriate server when an instance of GetUsersTask
         *      is created (is done in the background obviously)
         *
         * @param: URL... params
         * @return JSONArray
         */
        @Override
        protected JSONArray doInBackground(URL... params) {
            HttpURLConnection connection = null;
            StringBuilder result = new StringBuilder();
            try {
                connection = (HttpURLConnection) params[0].openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    users = new JSONArray(result.toString());
                    return new JSONArray(result.toString());
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                assert connection != null;
                connection.disconnect();
            }
            return null;
        }

        /*
        * onPostExecute() calls the necessary methods if a user was returned from
        *      the doInBackground() method above
        *
        * @param: JSONArray user
        * @return: none
        */
        @Override
        protected void onPostExecute(JSONArray user) {
            if (user != null) {

                convertJSONtoArrayList(user);

            } else {
                Toast.makeText(LoginActivity.this, "Failed to connect to service", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     * Adds the JSON user username information to the CREDENTIALS string array, setting all
     *      passwords to be "password".
     *
     * @param user_obj
     * @return: none
     *
     * Altered from Lab06
     */
    private void convertJSONtoArrayList(JSONArray user_obj) {

        try {
            for (int i = 0; i < user_obj.length(); i++) {
                JSONObject user = user_obj.getJSONObject(i);
                String name = user.getString("Username");
                name = name + ":password";
                CREDENTIALS[index] = name;
                index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Integer getUserId(String username) {
        for(Integer i = 0; i < users.length(); i++) {
            try {
                if (users.getString(i).equals(username)) {
                    return i + 1;
                }
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }

        }
        return -1;
    }
}

