package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

public class activity_home extends AppCompatActivity implements View.OnClickListener {

    //home layout buttons
    private Button home;
    private Button home_expenses;
    private Button home_accounts;
    private Button home_budget;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home:
                intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;
            case R.id.home_expenses:
                intent = new Intent(this, activity_home_expenses.class);
                startActivity(intent);
                break;
            case R.id.home_accounts:
                intent = new Intent(this, activity_home_accounts.class);
                startActivity(intent);
                break;
            case R.id.home_budget:
                intent = new Intent(this, activity_home_budget.class);
                startActivity(intent);
                break;
        }

        intent = new Intent(this, activity_home_budget.class);
        startActivity(intent);

        //testing
        System.out.println("Id TESTESTSTSETSET" + view.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        //linking buttons to their respective ids
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);

        home_expenses = (Button) findViewById(R.id.home_expenses);
        home.setOnClickListener(this);

        home_accounts = (Button) findViewById(R.id.home_accounts);
        home.setOnClickListener(this);

        home_budget = (Button) findViewById(R.id.home_budget);
        home.setOnClickListener(this);



    }
}
