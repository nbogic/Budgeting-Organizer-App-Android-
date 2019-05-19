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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Second screen of the account creation process, here the user will enter personal details and finalize their account creation
 */
public class activity_account_create2 extends AppCompatActivity implements View.OnClickListener, Serializable {

    /**
     * btnCancel - returns the user to home screen, no account is created
     * btnCreate - account is created after values go through validation
     * btnBack - sends the user back to the previous screen to change their entries
     */
    private Button btnCancel;
    private Button btnCreate;
    private Button btnBack;

    private String user_name;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String pin_code;

    /**
     * intro_user - a list to contain user files
     */
    public List<User> intro_user = new ArrayList();

    /**
     * login_file - text file that will store a list of active users
     */
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";

    /**
     * onClick - listen for button clicks and perform the appropriate function
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.intro_cancel:
                intent = new Intent(this, activity_intro.class);
                startActivity(intent);
                break;
            case R.id.intro_create:
                /**
                 * get the values from the EditTexts and assign them to Strings
                 */
                EditText intro_firstname = findViewById(R.id.edit_intro_firstname);
                EditText intro_lastname = findViewById(R.id.edit_intro_lastname);
                EditText intro_pin = findViewById(R.id.edit_intro_pin);
                pin_code = intro_pin.getText().toString();
                first_name = intro_firstname.getText().toString();
                last_name = intro_lastname.getText().toString();

                /**
                 * Call function (accountCreate) - load the user file, retrieve the list, add the new account to the list,
                 * overwrite the file with the updated list (return_user)
                 */
                if(accountCreate());
            {
                System.out.println("Da hell");

                intent = new Intent(this, activity_intro.class);
                startActivity(intent);
                break;
            }

            case R.id.intro_back:
                /**
                 * Return to previous screen
                 */
                intent = new Intent(this, activity_account_create.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * accountSave - writes the created user object into a file in the form of a list, returns the aforementioned list as a return type to ensure the function has multiple uses/purposes
     * @param "none"
     * @return List<User>
     */
    public List<User> accountSave() {
        /**
         * create a new file, with the directory set to 'String login_file'
         */
        FileOutputStream file_out;
        File file = new File(login_file);
        try {
            file_out = new FileOutputStream(file);
            ObjectOutputStream user_out = new ObjectOutputStream(file_out);
            /**
             * Overwrite the file with the most current version of the user list
             */
            user_out.writeObject(intro_user);
            user_out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return intro_user;
    }

    /**
     * accountCreate - this function is used to create the user's account using the inputted text, it is called once the 'create' button is pressed
     * before the function can return a TRUE value, the validation function must also return TRUE
     * if the function returns FALSE, then a error message will be thrown out to alert the user
     * @param "none"
     * @return Boolean*/
    public Boolean accountCreate() {
        /**
         * Function call (accountValidate) - validates all the accumulated values
         */
        String wtfisgoingon = accountValidate(user_name, password, pin_code, first_name, last_name, email).toString();

        System.out.println("Is this for fuckin real?! " + wtfisgoingon);

        if (accountValidate(user_name, password, pin_code, first_name, last_name, email)) {
            User user = new User(user_name, password, pin_code, first_name, last_name, email);
            intro_user.add(user);
            accountSave();
            return true;
        } else {
            return false;
        }
    }

    /**
     * accountValidate - this function is intended to validate a set of strings and return a boolean value based on result given
     * @param user_name, @password, @pin_code, first_name, last_name, email - strings to have their values tested and compared
     * @return Boolean VALUE - returns TRUE or FALSE based on the results gathered from validation
     * TRUE - validation pass, FALSE - validation fail
     */
    public Boolean accountValidate(String user_name, String password, String pin_code, String first_name, String
            last_name, String email) {
        /**
         * all validation criterion must be met, counter must be a certain number before the function proceeds
         */
        int counter = 0;
        /**
         * password must be at least 8 characters long
         */
        String password_length = ".{8,}";

        /**
         * checking to see if the field is empty
         */
        if (user_name.equals("") || user_name.equals(null)) {
            showMessage("Empty field!", "User name is empty.");
            counter = 0;
        } else { counter = counter + 1; }

            if (password.equals("") || password.equals(null)) {
                showMessage("Empty field!", "Password is empty.");
            counter = 0;

        } else { counter = counter + 1; }
        if (pin_code.equals("") || pin_code.equals(null)) {
            showMessage("Empty field!", "Pin code is empty.");
            counter = 0;

        } else { counter = counter + 1; }
        if (first_name.equals("") || first_name.equals(null)) {
            showMessage("Empty field!", "First name is empty.");
            counter = 0;

        } else { counter = counter + 1; }
        if (last_name.equals("") || last_name.equals(null)) {
            showMessage("Empty field!", "Last name is empty.");

            counter = 0;

        } else { counter = counter + 1; }
        if (email.equals("") || email.equals(null)) {
            showMessage("Empty field!", "Email is empty.");
            counter = 0;
        } else { counter = counter + 1; }

        /**
         * pin code validation
         */
        if(pin_code.matches("[0-9]")) {
            counter = counter + 1;
        } else {
            counter = 0;
            showMessage("Wrong data type.", "The pin code should be numbers only.");
        }

        /**
         * password length
         */
         if(password.matches(password_length)) {
            counter = counter + 1;
        } else {
            counter = 0;
            showMessage("Too few characters.", "The username should be at least 8 characters.");

         }

        if(counter == 8) {
            return true;
        } else {
            return false;
        }
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


    /**
     * accountCreate - loads the object list from the account file, return type is the inner function created list, only used within this activity
     * @param "none"
     * @return List<User>
     */
    public List<User> accountLoad() {
        List<User> user_gen = new ArrayList<User>();
        try {
            FileInputStream fis = new FileInputStream (new File(login_file));
            ObjectInputStream ooo = new ObjectInputStream(fis);
            user_gen = (List<User>) ooo.readObject();
            ooo.close();

            /**
             * exceptions
             */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user_gen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_create2);

        /**
         * assigns intro_user to the latest version of the file
         * this is to avoid data loss/overwriting when the application is exited, it will retain user details
         */
        intro_user = accountLoad();

        /**
         * get the username, password, and email that was saved in the previous activity
         * assign it to the appropriate strings, which are later used in the object's (user) creation
         */
        Intent intent = getIntent();
        user_name = intent.getExtras().getString("user_name");
        password = intent.getExtras().getString("password");
        email = intent.getExtras().getString("email");

        /**
         * assigning buttons to their IDs
         */
        btnCancel = findViewById(R.id.intro_cancel);
        btnCancel.setOnClickListener(this);

        btnCreate = findViewById(R.id.intro_create);
        btnCreate.setOnClickListener(this);

        btnBack = findViewById(R.id.intro_back);
        btnBack.setOnClickListener(this);
    }


}
