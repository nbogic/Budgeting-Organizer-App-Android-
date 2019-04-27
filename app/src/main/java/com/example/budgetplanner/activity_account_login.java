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

    private static final String login_file = "intro_users.txt";
    public List<User> return_user = new ArrayList<User>();

    @Override
    public void onClick(View view) {
        EditText login_username = (EditText) findViewById(R.id.login_username);
        EditText login_password = (EditText) findViewById(R.id.login_password);
        user_name = login_username.getText().toString();
        password = login_password.getText().toString();
        System.out.println("You entered.... Username: " + user_name + " Password: " + password);
        intro_account_load(login_file, user_name, password);
        for(int i = 0; i < return_user.size(); i++)  {
            System.out.println("Username: " +return_user.get(i).user_name);
            System.out.println("Password: " + return_user.get(i).password);

                if(user_name.equals(return_user.get(i).user_name) || password.equals(return_user.get(i).password)) {
                    Intent intent = new Intent(this, activity_home.class);
                    startActivity(intent);
                }
            }

    }

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
