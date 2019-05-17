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

public class activity_home_accounts extends AppCompatActivity implements View.OnClickListener {

    //accounts layout buttons
    private ImageButton accounts_home;
    private ImageButton accounts_expenses;
    private ImageButton accounts;
    private ImageButton accounts_budget;
    private Button accounts_create;

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
            case R.id.accounts_budget:
                intent = new Intent(this, activity_home_budget.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
            case R.id.accounts_create:
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

        RecyclerView recyclerView = findViewById(R.id.rv_account);
        List<Accounts> mlist = new ArrayList<>();
        for(int i = 0; i < user.accounts.size(); i++) {
            mlist.add(user.accounts.get(i));
        }
        RecyclerView.Adapter adapter = new Account_Adapter(this, mlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        accounts_home = (ImageButton) findViewById(R.id.home);
        accounts_home.setOnClickListener(this);

        accounts_expenses = (ImageButton) findViewById(R.id.home_expenses);
        accounts_expenses.setOnClickListener(this);

        accounts = (ImageButton) findViewById(R.id.home_accounts);
        accounts.setOnClickListener(this);

        accounts_budget = (ImageButton) findViewById(R.id.accounts_budget);
        accounts_budget.setOnClickListener(this);

        accounts_create = (Button) findViewById(R.id.accounts_create);
        accounts_create.setOnClickListener(this);
    }
}



