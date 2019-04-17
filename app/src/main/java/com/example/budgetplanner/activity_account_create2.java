package com.example.budgetplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class activity_account_create2 extends AppCompatActivity implements View.OnClickListener {

    private Button intro_cancel;
    private Button intro_create;
    private Button intro_back;

    private String user_name;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String pin_code;

    private static final String file = "users.txt";

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.intro_cancel:
                intent = new Intent(this, activity_intro.class);
                startActivity(intent);
                break;
            case R.id.intro_create:
                EditText intro_firstname = (EditText) findViewById(R.id.edit_intro_firstname);
                EditText intro_lastname = (EditText) findViewById(R.id.edit_intro_lastname);
                EditText intro_pin = (EditText) findViewById(R.id.edit_intro_pin);
                pin_code = intro_pin.getText().toString();
                first_name = intro_firstname.getText().toString();
                last_name = intro_lastname.getText().toString();


                System.out.println("First name: " + first_name + " Last name: " + last_name + " Pincode: " + pin_code);
                intent = new Intent(this, activity_home.class);

                if (intro_account_create().equals(true)) {
                    intro_account_create();
                    startActivity(intent);
                } else {
                    System.out.println("ERROR!");
                    intro_account_create();
                }
                break;

            case R.id.intro_next:
                System.out.println("Remove me");
                break;

            case R.id.intro_back:
                intent = new Intent(this, activity_account_create.class);
                startActivity(intent);
                break;

        }
    }

    //writes the user object to a file, file is later used to authenticate login details
    public void intro_account_write(Object user) {
        try {
            FileOutputStream file_out = new FileOutputStream(file);
            ObjectOutputStream user_out = new ObjectOutputStream(file_out);
            user_out.writeObject(user);
            user_out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /* this function is used to create the user's account using the inputted text, it is called once the 'create' button is pressed
    as there lies an issue with multiple layouts for one activity, the second layout for the remaining information is inflated
    to capture current information, the edit texts are assigned in this function
    before the function can return a TRUE value, the validation function must also return TRUE
    if the function returns FALSE, then a error message will be thrown out to alert the user */
    public Boolean intro_account_create() {
        //get the current values in the edit texts
        EditText intro_username = (EditText) findViewById(R.id.edit_intro_username);
        EditText intro_password = (EditText) findViewById(R.id.edit_intro_password);
        //EditText intro_password2 = (EditText) findViewById(R.id.edit_intro_password2);
        EditText intro_email = (EditText) findViewById(R.id.edit_intro_email);

        //validation occurs
        if (intro_account_validate(user_name, password, pin_code, first_name, last_name, email).equals(true)) {
            //use strings to create the user class
            System.out.println("User name: " + user_name + " Password: " + password + " Email: " + email + " First name: " + first_name + " Last name: " + last_name + " Pincode: " + pin_code);

            //User user = new User(user_name, password, pin_code, Boolean.TRUE, first_name, last_name.toString(), email);

            //testing purposes
            // System.out.println("User name " + user_name + "Password " + password + "Email " + email + "First name: " + first_name + "Last name: " + last_name);
            //  } else {
            //return false; }
           // return false;

            // }
        }
        return false;
    }

    /* this function is intended to validate a set of strings and return a boolean value based on result given
    parameters: generics
    if a string contains no characters or is of a null value, the function will return FALSE
    if this is not the case, then a TRUE value will be returned */
        public Boolean intro_account_validate (String s1, String s2, String s3, String s4, String
        s5, String s6){
            //checking to see if the field is empty

            if (s1.equals("") || s1.equals(null)) {
                System.out.println("S1 empty");
                return false;
            } else if (s2.equals("") || s2.equals(null)) {
                System.out.println("S2 empty");
                return false;
            } else if (s3.equals("") || s3.equals(null)) {
                System.out.println("S3 empty");
                return false;
            } else if (s4.equals("") || s4.equals(null)) {
                System.out.println("S4 empty");
                return false;
            } else if (s5.equals("") || s5.equals(null)) {
                System.out.println("S5 empty");
                return false;
            } else if (s6.equals("") || s6.equals(null)) {
                System.out.println("S6 empty");
                return false;
            } else {
                return true;
            }

            //checking to see if password meets requirements

            //checking to see if passwords match

            //pin code validation

            //character limits

        }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_account_create2);

            Intent intent = getIntent();
            user_name = intent.getExtras().getString("user_name");
            password = intent.getExtras().getString("email");
            email = intent.getExtras().getString("password");
            System.out.println("username: " + user_name + "password: " + password + "email: " + email);

            intro_cancel = (Button) findViewById(R.id.intro_cancel);
            intro_cancel.setOnClickListener(this);

            intro_create = (Button) findViewById(R.id.intro_create);
            intro_create.setOnClickListener(this);

            intro_back = (Button) findViewById(R.id.intro_back);
            intro_back.setOnClickListener(this);
        }

    }
