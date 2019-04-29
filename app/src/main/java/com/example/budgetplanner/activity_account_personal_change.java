package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class activity_account_personal_change extends AppCompatActivity implements View.OnClickListener, Serializable {

    //home layout buttons
    private Button home;
    private Button home_profile;
    private Button home_expenses;
    private Button home_accounts;
    private Button home_budget;

    private Button confirm;
    private Button cancel;

    private TextView change_title;
    private TextView enter_current;
    private TextView enter_new;
    private TextView enter_new2;

    private User home_user2;
    private EditText edit_old_password;
    private EditText edit_new;
    private EditText edit_new2;
    private String option;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home:
                intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;
            case R.id.home_expenses:
                intent = new Intent(this, activity_home_expenses.class);
                startActivity(intent);
                break;
            case R.id.home_accounts:
                intent = new Intent(this, activity_home_accounts.class);
                startActivity(intent);
                break;
            case R.id.home_budget:
                intent = new Intent(this, activity_home_budget.class);
                startActivity(intent);
                break;
            case R.id.personal_confirm:
                //decides which information will be tested
                if(option.equals("password")) {
                    personal_switch_option("password_confirm");
                }
                if(option.equals("pincode")) {
                    personal_switch_option("pincode_confirm");
                }
                break;
            case R.id.personal_cancel:
                intent = new Intent(this, activity_account_personal.class);
                startActivity(intent);
                break;
        }
    }

    //changes the text view and function implementation based on a string parameter (pincode or password)
    public void personal_switch_option(String option) {
        switch (option) {
            //confirm password, get the user input from the views, assign it to strings, validation check to see if passwords match, return to previous activity with newly updated user object (password)
            case "password_confirm":
                edit_old_password = (EditText) findViewById(R.id.edit_current);
                edit_new = (EditText) findViewById(R.id.edit_new);
                edit_new2 = (EditText) findViewById(R.id.edit_new2);

                String old_password, new_password, new_password2;
                old_password = edit_old_password.getText().toString();
                new_password = edit_new.getText().toString();
                new_password2 = edit_new2.getText().toString();

                if(old_password.equals(home_user2.password) || new_password.equals(new_password2)) {
                    home_user2.password = new_password;
                    Intent intent = new Intent(this, activity_account_personal.class);
                    intent.putExtra("home_user_new", home_user2);
                    startActivity(intent);
                }
                break;

            //confirm pincode, get the user input from the views, assign it to strings, validation check to see if pincdoes match, return to previous activity with newly updated user object (pincode)
            case "pincode_confirm":
                edit_old_password = (EditText) findViewById(R.id.edit_current);
                edit_new = (EditText) findViewById(R.id.edit_new);
                edit_new2 = (EditText) findViewById(R.id.edit_new2);

                String old_pin, new_pin, new_pin2;
                old_pin = edit_old_password.getText().toString();
                new_pin = edit_new.getText().toString();
                new_pin2 = edit_new2.getText().toString();

                if(old_pin.equals(home_user2.pin_code) || new_pin.equals(new_pin2)) {
                    home_user2.pin_code = new_pin;
                    Intent intent = new Intent(this, activity_account_personal.class);
                    intent.putExtra("home_user_new", home_user2);
                    startActivity(intent);
                }
                break;
                //set labels to refer to password
            case "password":
                change_title.setText("Change your password");
                enter_current.setText("Enter your current password");
                enter_new.setText("Enter a new password");
                enter_new2.setText("Enter the password again");
                break;
            //set labels to refer to pincode
            case "pincode":
                change_title.setText("Change your pincode");
                enter_current.setText("Enter your current pincode");
                enter_new.setText("Enter a new pincode");
                enter_new2.setText("Enter the pincode again");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_personal_change);

        Intent intent = getIntent();

        //get the option string from previous activity, can only be one of two different values
        option = intent.getStringExtra("Option");
        home_user2 = (User) intent.getSerializableExtra("home_user2");

        //use this function to set all the textviews to their relevant labels (password/pincode)
        personal_switch_option(option);

        confirm = (Button) findViewById(R.id.personal_confirm);
        confirm.setOnClickListener(this);

        cancel = (Button) findViewById(R.id.personal_cancel);
        cancel.setOnClickListener(this);

        change_title = (TextView) findViewById(R.id.home_change_title);
        enter_current = (TextView) findViewById(R.id.home_change_current);
        enter_new = (TextView) findViewById(R.id.home_change_new);
        enter_new2 = (TextView) findViewById(R.id.home_change_new2);

    }
}
