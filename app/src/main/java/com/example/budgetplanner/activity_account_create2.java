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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class activity_account_create2 extends AppCompatActivity implements View.OnClickListener, Serializable {

    private Button intro_cancel;
    private Button intro_create;
    private Button intro_back;

    private String user_name;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String pin_code;

    public List<User> intro_user = new ArrayList();
    private static final String login_file = "intro_users.txt";

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

                intent = new Intent(this, activity_home.class);
                intro_account_create();
                startActivity(intent);
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

    //writes the created user object into a file in the form of a list
    //returns the aforementioned list as a return type
    public List<User> intro_account_write(String File) {
        //testing for feedback
        System.out.println("Created!");
        FileOutputStream file_out;
        File file = new File(getFilesDir(), File);
        try {
            file_out = new FileOutputStream(file);
            ObjectOutputStream user_out = new ObjectOutputStream(file_out);
            user_out.writeObject(intro_user);
            user_out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return intro_user;
    }

    /* this function is used to create the user's account using the inputted text, it is called once the 'create' button is pressed
    as there lies an issue with multiple layouts for one activity, the second layout for the remaining information is inflated
    to capture current information, the edit texts are assigned in this function
    before the function can return a TRUE value, the validation function must also return TRUE
    if the function returns FALSE, then a error message will be thrown out to alert the user */
    public Boolean intro_account_create() {

        //validation occurs
        if (intro_account_validate(user_name, password, pin_code, first_name, last_name, email).equals(true)) {
            User user = new User(user_name, password, pin_code, first_name, last_name, email);
            intro_user.add(user);
            intro_account_write(login_file);

            //testing for feedback
            System.out.println("Following details - User name " + user.user_name + "Password " + user.password + "Email " + user.email + "First name: " + user.first_name + "Last name: " + user.last_name);
            System.out.println("Following details from intro_user - User name " + intro_user.get(0).user_name + "Password " + intro_user.get(0).password + "Email " + intro_user.get(0).email + "First name: " + intro_user.get(0).first_name + "Last name: " + intro_user.get(0).last_name);
            return true;
        } else {
            return false;
        }
    }

    /* this function is intended to validate a set of strings and return a boolean value based on result given
    parameters: generics
    if a string contains no characters or is of a null value, the function will return FALSE
    if this is not the case, then a TRUE value will be returned */
    public Boolean intro_account_validate(String s1, String s2, String s3, String s4, String
            s5, String s6) {
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

    //loads the object list from the account file
    //return type is the inner function created list
    public List<User> intro_account_load(String File) {
        FileInputStream fis = null;
        List<User> user_gen = new ArrayList<User>();
        try {
            fis = openFileInput(File);
            ObjectInputStream ooo = new ObjectInputStream(fis);
            user_gen = (List<User>) ooo.readObject();
            ooo.close();

            //exceptions
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
        
        //assigns intro_user to the latest version of the file
        //this is to avoid data loss/overwriting when the application is exited, it will retain user details
        intro_user = intro_account_load(login_file);

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
