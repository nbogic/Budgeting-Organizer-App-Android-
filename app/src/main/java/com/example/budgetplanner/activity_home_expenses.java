package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_home_expenses extends AppCompatActivity implements View.OnClickListener {

    //expenses layout buttons
    private Button expenses_home;
    private Button expenses;
    private Button expenses_accounts;
    private Button expenses_budget;
    private Button expenses_create;

    private User user;

    @Override
    public void onClick(View view) {
        Intent intent;
        User user_intent;
        switch (view.getId()) {
            case R.id.expenses_home:
                intent = new Intent(this, activity_home.class);
                user_intent = user;
                intent.putExtra("Home_User", user_intent);
                startActivity(intent);
                break;

            case R.id.expenses:
                intent = new Intent(this, activity_home_expenses.class);
                user_intent = user;
                intent.putExtra("Home_User", user_intent);
                startActivity(intent);
                break;

            case R.id.expenses_accounts:
                intent = new Intent(this, activity_home_accounts.class);
                user_intent = user;
                intent.putExtra("Home_User", user_intent);
                startActivity(intent);
                break;

            case R.id.expenses_budget:
                intent = new Intent(this, activity_home_budget.class);
                user_intent = user;
                intent.putExtra("Home_User", user_intent);
                startActivity(intent);
                break;

            case R.id.expenses_create:
                intent = new Intent(this, activity_account_expenses.class);
                user_intent = user;
                intent.putExtra("Home_User", user_intent);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_expenses);

        //get the user object that was passed from the previous activity (login)
        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");


        expenses_home = (Button) findViewById(R.id.expenses_home);
        expenses_home.setOnClickListener(this);

        expenses = (Button) findViewById(R.id.expenses);
        expenses.setOnClickListener(this);

        expenses_accounts = (Button) findViewById(R.id.expenses_accounts);
        expenses_accounts.setOnClickListener(this);

        expenses_budget = (Button) findViewById(R.id.expenses_budget);
        expenses_budget.setOnClickListener(this);

        expenses_create = (Button) findViewById(R.id.expenses_create);
        expenses_create.setOnClickListener(this);
    }
}
