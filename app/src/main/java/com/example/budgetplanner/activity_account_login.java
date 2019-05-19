package com.example.budgetplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allows the user to log-in into the application (be brought to the home screen) with their existing account in the app's file system
 */
public class activity_account_login extends AppCompatActivity implements View.OnClickListener {
    /**
     * btnLoginHome - the user clicks this button after entering their account details, and will bring them to the home screen (if the information passes validation successfully)
     * user_name, password - variables to contain the user's input when entering their details
     */
    private Button btnLoginHome;
    private String user_name;
    private String password;

    /**
     * user - temporary object that is used to assign the returned user to, passed along in a intent to the home screen
     */
    private User user;

    /**
     * return_user - temporary list that is used to contain retrieved accounts from reading the account file
     * login_file - the directory of the file that contains all user accounts, assigned to a String
     */
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";
    public List<User> return_user = new ArrayList<User>();

    /**
     * onClick - listen for button clicks and perform the appropriate function
     * default: load all the accounts from the account file and validate user input
     */
    @Override
    public void onClick(View view) {
        /**
         * login_username, login_password - get the current values of the EditTexts
         */
        EditText login_username = findViewById(R.id.login_username);
        EditText login_password = findViewById(R.id.login_password);
        user_name = login_username.getText().toString();
        password = login_password.getText().toString();

        /**
         * Function call (accountLoad) - file reading function is called with the String values attained from the EditTexts
         */
        if (accountLoad(user_name, password).equals(Boolean.TRUE)) {
            Intent intent = new Intent(this, activity_home.class);
            intent.putExtra("Home_User", user);
            startActivity(intent);
        }
    }

    /**
     * accountLoad - this function loads a file (the account file), assigns the contents to a list, and then performs a loop
     * to match the param values (user and pass) to a account within the list (return user)
     * @param inputted_user - String value to compare with a item of the list (user)
     * @param inputted_pass - String value to compare with a item of the list (user)
     * @return return BOOL - Returns TRUE if a match has been found between the Strings and a account, or FALSE if the opposite has occurred
     */
    public Boolean accountLoad(String inputted_user, String inputted_pass) {
        try {
            FileInputStream fis = new FileInputStream(new File(login_file));
            ObjectInputStream ooo = new ObjectInputStream(fis);
            return_user = (List<User>) ooo.readObject();

            for (int i = 0; i < return_user.size(); i++) {
                /**
                 * If a match is found, then the loop will break and a new user object will be attached to the intent, and the user will be taken to the home screen
                 */
                if (inputted_user.equals(return_user.get(i).user_name) || inputted_pass.equals(return_user.get(i).password)) {
                    user = return_user.get(i);
                    return true;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        showMessage("Something went wrong!", "Incorrect username or password entered. Please try again.");
        EditText login_password = findViewById(R.id.login_password);
        login_password.setText("");

        return false;
    }

    /**
     * showMessage - dialog creator, creates customized messages/alerts delivered to the user to inform them about a validation error or other inconsistency
     * @param title - title of the message box, should be a one line brief description
     * @param description - a description of the error in detail, instructing the user what to do to avoid this alert showing again
     * @return void
     */
    private void showMessage(String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(description);
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_login);

        btnLoginHome = findViewById(R.id.login_signin);
        btnLoginHome.setOnClickListener(this);
    }
}
