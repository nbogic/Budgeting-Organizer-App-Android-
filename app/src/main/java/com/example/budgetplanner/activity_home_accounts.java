package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_home_accounts extends AppCompatActivity implements View.OnClickListener {

    //accounts layout buttons
    private Button accounts_home;
    private Button accounts_expenses;
    private Button accounts;
    private Button accounts_budget;
    private Button accounts_create;

    private User user;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.accounts_home:
                intent = new Intent(this, activity_home.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
            case R.id.accounts_expenses:
                intent = new Intent(this, activity_home_expenses.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
            case R.id.accounts:
                intent = new Intent(this, activity_home_accounts.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
            case R.id.accounts_budget:
                intent = new Intent(this, activity_home_budget.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
            case R.id.create_account:
                intent = new Intent(this, activity_account_accounts.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_accounts);

        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");
    //   System.out.println("Details from home screen ----- username: " + user.user_name + "password: " + user.password + "email: " + user.email + "first name: " + user.first_name);

        accounts_home = (Button) findViewById(R.id.accounts_home);
        accounts_home.setOnClickListener(this);

        accounts_expenses = (Button) findViewById(R.id.accounts_expenses);
        accounts_expenses.setOnClickListener(this);

        accounts = (Button) findViewById(R.id.accounts);
        accounts.setOnClickListener(this);

        accounts_budget = (Button) findViewById(R.id.accounts_budget);
        accounts_budget.setOnClickListener(this);

        accounts_create = (Button) findViewById(R.id.create_account);
        accounts_create.setOnClickListener(this);
    }
}



