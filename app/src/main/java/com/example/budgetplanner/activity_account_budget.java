package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class activity_account_budget extends AppCompatActivity implements View.OnClickListener {
    private EditText budget_name;
    private Spinner budget_category;
    private Spinner budget_addexpense;
    private Spinner budget_addaccount;

    private Spinner budget_addcategory;
    private EditText budget_duration;
    private String budget_date;
    private EditText budget_amount;

    private User user;

    private static final String TAG = "activity_account_expenses";
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.budget_add:
                intent = new Intent(this, activity_account_create.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;

            case R.id.budget_cancel:
                intent = new Intent(this, activity_account_create.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_budget);

        budget_addexpense = (Spinner) findViewById(R.id.budget_addexpense);
        budget_addaccount = (Spinner) findViewById(R.id.budget_addaccount);

        //get the user object that was passed from the previous activity (login)
        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");

        load_spinner();

    }

    public void load_spinner() {
        List<String> expenses_spinner_values = new ArrayList<String>();
        List<String> accounts_spinner_values = new ArrayList<String>();

        for(int i = 0; i < user.expenses.size(); i++)  {
            expenses_spinner_values.add(user.expenses.get(i).destination);
        }

        for(int x = 0; x < user.accounts.size(); x++)  {
            accounts_spinner_values.add(user.accounts.get(x).account_name);
            System.out.println("Account nameff: " + user.accounts.get(x).account_name );

    }

        ArrayAdapter<String> expense_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, expenses_spinner_values);
        ArrayAdapter<String> account_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, accounts_spinner_values);

        expense_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        budget_addexpense.setAdapter(expense_adapter);
        budget_addaccount.setAdapter(account_adapter);
    }
}
