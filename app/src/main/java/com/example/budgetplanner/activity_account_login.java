package com.example.budgetplanner;

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

public class activity_account_login extends AppCompatActivity implements View.OnClickListener {

    private Button login_signin;
    private String user_name;
    private String password;

    private static final String login_file = "users.txt";
    private List<User> Users = new ArrayList<User>();

    //loads the user file and extracts the relevant information, compares the strings to see if they match
    public void intro_account_load(String entered_username, String entered_password) {

        FileInputStream fis = null;
        try {
            fis = openFileInput(login_file);
            ObjectInputStream ooo = new ObjectInputStream(fis);
            Users = (List<User>) ooo.readObject();

            //testing to see if values are correct
            System.out.println("Username: " +Users.get(1).user_name);
            System.out.println("Password: " + Users.get(1).password);

            //save retrieved values into strings
            String retrieved_user = Users.get(1).user_name;
            String retrieved_pass = Users.get(1).password;

            //use retrieved values to see if they match
            if(entered_username.equals(retrieved_user) || entered_password.equals(retrieved_pass)) {
                Intent intent = new Intent(this, activity_home.class);
                startActivity(intent);
            }

            ooo.close();

            //exceptions
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        EditText login_username = (EditText) findViewById(R.id.login_username);
        EditText login_password = (EditText) findViewById(R.id.login_password);

        user_name = login_username.getText().toString();
        password = login_password.getText().toString();
        intro_account_load(user_name, password);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_login);

        login_signin = (Button) findViewById(R.id.login_signin);
        login_signin.setOnClickListener(this);
    }
}
