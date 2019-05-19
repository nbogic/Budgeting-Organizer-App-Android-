package com.example.budgetplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
/**
 * After a successful login, the user will reach the home screen activity, contains the navigation bar/menu and summaries of user info
 */
public class activity_home extends AppCompatActivity implements View.OnClickListener, Serializable {

    /**
     * btnProfile - takes the user to a screen that displays their account details and offers functions for modification
     * tvHomeName - displays the user's first name within a greeting ("Welcome back x!")
     * Expense count - shows the amount of expenses on this user, total shows the combined values of all expenses. Account count/budget count follow the same principles.
     */
    private Button btnProfile;
    private TextView tvHomeName;
    private TextView tvExpenseCount;
    private TextView tvExpenseTotal;
    private TextView tvAccountCount;
    private TextView tvAccountTotal;
    private TextView tvBudgetCount;
    private TextView tvBudgetTotal;
    private Intent intent_pass;
    private User user;

    /**
     * nav_home - bottom navigation bar
     */
    BottomNavigationView nav_home;

    /**
     * onClick - listen for button clicks and perform the appropriate function
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.home_profile_button:
                intent = new Intent(this, activity_account_personal.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
        }
    }

    /**
     * setIntent - assigns a class (activity) to a intent, used in the listener function
     * @param param_class - class that is to be assigned to the variable intent_pass
     */
    public void setIntent(Class param_class) {
        intent_pass = new Intent(this, param_class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        /**
         * assign the navigation view, change colours to null to relfect original colours values set in the menu files
         */
        nav_home = (BottomNavigationView) findViewById(R.id.home_nav);
        nav_home.setItemIconTintList(null);
        nav_home.setItemTextColor(null);
        /**
         * onOnNavigationItemSelectedListener - listen for clicks in the navigation view, take the user to the desired screen
         */
        nav_home.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                Class change_activity;
                switch (item.getItemId()) {
                    case R.id.home:
                        change_activity = activity_home.class;
                        setIntent(change_activity);
                        intent_pass.putExtra("Home_User", user);
                        startActivity(intent_pass);
                        break;

                    case R.id.home_expenses:
                        change_activity = activity_home_expenses.class;
                        setIntent(change_activity);
                        intent_pass.putExtra("Home_User", user);
                        startActivity(intent_pass);
                        break;

                    case R.id.home_accounts:
                        change_activity = activity_home_accounts.class;
                        setIntent(change_activity);
                        intent_pass.putExtra("Home_User", user);
                        startActivity(intent_pass);
                        break;

                    case R.id.home_budget:
                        change_activity = activity_home_budget.class;
                        setIntent(change_activity);
                        intent_pass.putExtra("Home_User", user);
                        startActivity(intent_pass);
                        break;
                }
                return false;
            }
        });

        /**
         * get the user object that was passed from the previous activity (login)
         * assign the returned object to the current user object
         */
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("Home_User");

        tvHomeName = findViewById(R.id.home_name);
        tvExpenseCount = findViewById(R.id.expense_number);
        tvExpenseTotal = findViewById(R.id.expense_number2);
        tvAccountCount = findViewById(R.id.account_number);
        tvAccountTotal = findViewById(R.id.account_number2);
        tvBudgetCount = findViewById(R.id.budget_number);
        tvBudgetTotal = findViewById(R.id.budget_number2);

        /**
         * if user data (expenses, accounts, budgets) is null, then alternative values will be assigned to the TextViews
         * to avoid exception throwing
         */
        if(user.expenses != null) {
            int mExpenses = user.expenses.size();
            long mExp_Total = 0;

            tvExpenseCount.setText(mExpenses + " expenses");
            for (int i = 0; i < user.expenses.size(); i++) {
                mExp_Total = user.expenses.get(i).cost + mExp_Total;
            }
            tvExpenseTotal.setText("$" + String.valueOf(mExp_Total));
        } else {
            tvExpenseCount.setText("Go create some expenses!");
            tvExpenseTotal.setText("");

        }

        if(user.accounts != null) {
            int mAccounts = user.accounts.size();
            long mAcc_Total = 0;
            for(int i = 0; i < user.accounts.size(); i++) {
                if(user.accounts.get(i).account_balance != "") {
                    mAcc_Total = Long.valueOf(user.accounts.get(i).account_balance) + mAcc_Total;
                }
            }
            tvAccountCount.setText(mAccounts + " accounts");
            tvAccountTotal.setText("$" + mAcc_Total);
        }

        if(user.budgets != null) {
            int mBudgets = user.budgets.size();
            tvBudgetCount.setText(mBudgets + " budgets");
            long mBgt_Total = 0;
            for(int i = 0; i < user.budgets.size(); i++) {
                mBgt_Total = Long.valueOf(user.budgets.get(i).amount) + mBgt_Total;
            }
            tvBudgetTotal.setText("$" + mBgt_Total);

        }

        /**
         * greeting message
         */
        if(user == null) {
            tvHomeName.setText("Welcome back!");
        } else { tvHomeName.setText("Welcome back " + user.first_name + "!"); }

    }
}
