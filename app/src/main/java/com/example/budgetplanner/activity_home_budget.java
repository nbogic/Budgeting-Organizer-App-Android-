package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class activity_home_budget extends AppCompatActivity implements View.OnClickListener {

    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";

    //budget layout buttons
    private ImageButton budget_home;
    private ImageButton budget_expenses;
    private ImageButton budget_accounts;
    private ImageButton budget;
    private Button budget_add;

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
            case R.id.budget:
                intent = new Intent(this, activity_home_budget.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
            case R.id.budget_create:
                intent = new Intent(this, activity_account_budget.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_budget);

        //get the user object that was passed from the previous activity (login)
        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");
        //change the TextView to display the user's first name using the new user object, welcome message
        //setup recycler view with the adapter

        RecyclerView recyclerView = findViewById(R.id.rv_budget);
        List<Budget> mlist = new ArrayList<>();
        for(int i = 0; i < user.budgets.size(); i++) {
            mlist.add(user.budgets.get(i));
        }
        RecyclerView.Adapter adapter = new Budget_Adapter(this, mlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        budget_home = (ImageButton) findViewById(R.id.home);
        budget_home.setOnClickListener(this);

        budget_expenses = (ImageButton) findViewById(R.id.home_expenses);
        budget_expenses.setOnClickListener(this);

        budget = (ImageButton) findViewById(R.id.budget);
        budget.setOnClickListener(this);

        budget_accounts = (ImageButton) findViewById(R.id.home_accounts);
        budget_accounts.setOnClickListener(this);

        budget_add = (Button) findViewById(R.id.budget_create);
        budget_add.setOnClickListener(this);
    }
}
