package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private static final String login_file = "list_users.txt";
    public List<User> return_user = new ArrayList<User>();

    @Override
    public void onClick(View view) {
        EditText login_username = (EditText) findViewById(R.id.login_username);
        EditText login_password = (EditText) findViewById(R.id.login_password);
        user_name = login_username.getText().toString();
        password = login_password.getText().toString();
        //testing
        System.out.println("You entered.... Username: " + user_name + " Password: " + password + "\n");
        intro_account_load(login_file, user_name, password);
        for(int i = 0; i < return_user.size(); i++)  {
            //testing purposes, lists all accounts in the file
            System.out.println("Username: " +return_user.get(i).user_name);
            System.out.println("Password: " + return_user.get(i).password);
            System.out.println("First name: " +return_user.get(i).first_name);
            System.out.println("Last name: " + return_user.get(i).last_name);
            System.out.println("Email: " + return_user.get(i).email);
            System.out.println("Pin: " + return_user.get(i).pin_code);
            //if a match is found, then the loop will break and a new user object will be attached to the intent
                if(user_name.equals(return_user.get(i).user_name) || password.equals(return_user.get(i).password)) {
                    Intent intent = new Intent(this, activity_home.class);
                    User correct_user = new User(return_user.get(i).user_name, return_user.get(i).password, return_user.get(i).first_name, return_user.get(i).last_name, return_user.get(i).email, return_user.get(i).pin_code);
                    //send the created object to the main home screen
                    intent.putExtra("User", correct_user);
                    startActivity(intent);
                }
            }

    }
    //extended version of the loading function found in the account creation activity, takes two new parameters to test the user's input against what is stored in the file
    public List<User> intro_account_load(String File, String inputted_user, String inputted_pass) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(File);
            ObjectInputStream ooo = new ObjectInputStream(fis);
            return_user = (List<User>) ooo.readObject();
            //exceptions
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //return the user object
        return return_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_login);

        login_signin = (Button) findViewById(R.id.login_signin);
        login_signin.setOnClickListener(this);
    }
}
