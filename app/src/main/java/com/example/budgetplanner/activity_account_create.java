package com.example.budgetplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * First screen of the account creation process, user enters their username, password, and email before proceeding to the next screen
 */
public class activity_account_create extends AppCompatActivity implements View.OnClickListener {

    /**
     * btnNextScreen - takes the user to the second account creation screen, carrying over the values created in this activity
     * email - email of the user
     * user_name - chosen user name of the user
     * password - the user will need to enter their password twice for validation purposes
     */
    private Button btnNextScreen;
    private String email;
    private String user_name;
    protected String password;
    protected String password_confirm;

    /**
     * onClick - listen for button clicks and perform the appropriate function
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.intro_next:
                /**
                 * get all the current input from the text boxes
                 */
                EditText intro_username = findViewById(R.id.edit_intro_username);
                EditText intro_password = findViewById(R.id.edit_intro_password);
                EditText intro_password2 = findViewById(R.id.edit_intro_password2);
                EditText intro_email = findViewById(R.id.edit_intro_email);

                /**
                 * assigned to the strings initliaised earlier
                 */
                email = intro_email.getText().toString();
                user_name = intro_username.getText().toString();
                password = intro_password.getText().toString();
                password_confirm = intro_password2.getText().toString();


                /**
                 * minor validation check to see if passwords match, user will need to retry if the password does not match
                 */
                if(password.equals(password_confirm) && email != "" && user_name != "") {
                    /**
                     * passwords match, and the app can proceed
                     * a intent is created, and the strings are attached to it (so it can be used in the next activity)
                     */
                    intent = new Intent(this, activity_account_create2.class);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("password", password);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    break;
                } else if(password.equals(null) || password.equals("")){
                    showMessage("Passwords do not match.", "Please re-enter your details");
                    break; } else {
                    showMessage("Passwords do not match.", "Please re-enter your details"); //the next activity is not launched due to the passwords inputted being empty or dissimilar
                }
            case R.id.intro_back:
                setContentView(R.layout.layout_account_create);
                break;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_create);

        btnNextScreen = findViewById(R.id.intro_next);
        btnNextScreen.setOnClickListener(this);
    }
}
