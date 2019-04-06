package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_home_budget extends AppCompatActivity implements View.OnClickListener {

    //budget layout buttons
    private Button budget_home;
    private Button budget_expenses;
    private Button budget_accounts;
    private Button budget;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.budget_home:
                intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;
            case R.id.budget_expenses:
                intent = new Intent(this, activity_home_expenses.class);
                startActivity(intent);
                break;
            case R.id.budget_accounts:
                intent = new Intent(this, activity_home_accounts.class);
                startActivity(intent);
                break;
            case R.id.budget:
                intent = new Intent(this, activity_home_budget.class);
                startActivity(intent);
                break;
        }

        //testing
        System.out.println("Id" + view.getId());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_budget);

        budget_home = (Button) findViewById(R.id.budget_home);
        budget_home.setOnClickListener(this);

        budget_expenses = (Button) findViewById(R.id.budget_expenses);
        budget_expenses.setOnClickListener(this);

        budget = (Button) findViewById(R.id.budget);
        budget.setOnClickListener(this);

        budget_accounts = (Button) findViewById(R.id.budget_accounts);
        budget_accounts.setOnClickListener(this);
    }
}
