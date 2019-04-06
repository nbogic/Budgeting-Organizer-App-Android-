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

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.accounts_home:
                intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;
            case R.id.accounts_expenses:
                intent = new Intent(this, activity_home_expenses.class);
                startActivity(intent);
                break;
            case R.id.accounts:
                intent = new Intent(this, activity_home_accounts.class);
                startActivity(intent);
                break;
            case R.id.accounts_budget:
                intent = new Intent(this, activity_home_budget.class);
                startActivity(intent);
                break;
        }

        intent = new Intent(this, activity_home_budget.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_accounts);

        accounts_home = (Button) findViewById(R.id.accounts_home);
        accounts_home.setOnClickListener(this);

        accounts_expenses = (Button) findViewById(R.id.accounts_expenses);
        accounts_expenses.setOnClickListener(this);

        accounts = (Button) findViewById(R.id.accounts);
        accounts.setOnClickListener(this);

        accounts_budget = (Button) findViewById(R.id.accounts_budget);
        accounts_budget.setOnClickListener(this);
    }
}



