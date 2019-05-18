package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

public class activity_home extends AppCompatActivity implements View.OnClickListener, Serializable {

    //home layout buttons
    private ImageButton home;
    private Button home_profile;
    private ImageButton home_expenses;
    private ImageButton home_accounts;
    private ImageButton home_budget;
    private TextView home_name;

    private TextView expense_count;
    private TextView expense_total;

    private TextView account_count;
    private TextView account_total;

    private TextView budget_count;
    private TextView budget_total;


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
        user = (User)intent.getSerializableExtra("Home_User");
        //change the TextView to display the user's first name using the new user object, welcome message
        home_name = (TextView) findViewById(R.id.home_name);
        expense_count = (TextView) findViewById(R.id.expense_number);
        expense_total = (TextView) findViewById(R.id.expense_number2);
        account_count =  (TextView) findViewById(R.id.account_number);
        account_total = (TextView) findViewById(R.id.account_number2);
        budget_count = (TextView) findViewById(R.id.budget_number);
        budget_total = (TextView) findViewById(R.id.budget_number2);

        if(user.expenses != null) {
            int mExpenses = user.expenses.size();
            long mExp_Total = 0;

            expense_count.setText(mExpenses + " expenses");
            for (int i = 0; i < user.expenses.size(); i++) {
                mExp_Total = user.expenses.get(i).cost + mExp_Total;
            }
            expense_total.setText("$" + String.valueOf(mExp_Total));
        } else {
            expense_count.setText("Go create some expenses!");
            expense_total.setText("");

        }

        if(user.accounts != null) {
            int mAccounts = user.accounts.size();
            long mAcc_Total = 0;
            for(int i = 0; i < user.accounts.size(); i++) {
                if(user.accounts.get(i).account_balance != "") {
                    mAcc_Total = Long.valueOf(user.accounts.get(i).account_balance) + mAcc_Total;
                }
            }
            account_count.setText(mAccounts + " accounts");
            account_total.setText("$" + mAcc_Total);
        }

        if(user.budgets != null) {
            int mBudgets = user.budgets.size();
            budget_count.setText(mBudgets + " budgets");
            long mBgt_Total = 0;
            for(int i = 0; i < user.budgets.size(); i++) {
                mBgt_Total = Long.valueOf(user.budgets.get(i).amount) + mBgt_Total;
            }
            budget_total.setText("$" + mBgt_Total);

        }

        if(user == null) {
            home_name.setText("Welcome back!");
        } else { home_name.setText("Welcome back " + user.first_name + "!"); }

        //linking buttons to their respective ids
        home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(this);

        home_expenses = (ImageButton) findViewById(R.id.home_expenses);
        home.setOnClickListener(this);

        home_accounts = (ImageButton) findViewById(R.id.home_accounts);
        home.setOnClickListener(this);

        home_budget = (ImageButton) findViewById(R.id.home_budget);
        home.setOnClickListener(this);

        home_profile = (Button) findViewById(R.id.home_profile_button);
        home.setOnClickListener(this);
    }
}
