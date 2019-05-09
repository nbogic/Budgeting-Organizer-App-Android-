package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class activity_home extends AppCompatActivity implements View.OnClickListener, Serializable {

    //home layout buttons
    private Button home;
    private Button home_profile;
    private Button home_expenses;
    private Button home_accounts;
    private Button home_budget;
    private TextView home_name;

    private User user;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home:
                intent = new Intent(this, activity_home.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);

                break;
            case R.id.home_expenses:
                intent = new Intent(this, activity_home_expenses.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;

            case R.id.home_accounts:
                intent = new Intent(this, activity_home_accounts.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;

            case R.id.home_budget:
                intent = new Intent(this, activity_home_budget.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;

            case R.id.home_profile_button:
                intent = new Intent(this, activity_account_personal.class);
                intent.putExtra("Home_User", user);
//                System.out.println("Details from home screen ----- username: " + user_intent.user_name + "password: " + user_intent.password + "email: " + user_intent.email + "first name: " + user_intent.first_name);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        //get the user object that was passed from the previous activity (login)
        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("User");
        //change the TextView to display the user's first name using the new user object, welcome message
        home_name = (TextView) findViewById(R.id.home_name);
        if(user == null) {
            home_name.setText("Welcome back!");
        } else { home_name.setText("Welcome back " + user.first_name + "!"); }

        //linking buttons to their respective ids
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);

        home_expenses = (Button) findViewById(R.id.home_expenses);
        home.setOnClickListener(this);

        home_accounts = (Button) findViewById(R.id.home_accounts);
        home.setOnClickListener(this);

        home_budget = (Button) findViewById(R.id.home_budget);
        home.setOnClickListener(this);

        home_profile = (Button) findViewById(R.id.home_profile_button);
        home.setOnClickListener(this);
    }
}
