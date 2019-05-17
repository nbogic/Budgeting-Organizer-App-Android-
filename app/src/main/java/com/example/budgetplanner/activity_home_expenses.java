package com.example.budgetplanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class activity_home_expenses extends AppCompatActivity implements View.OnClickListener {

    //expenses layout buttons
    private ImageButton expenses_home;
    private ImageButton expenses;
    private ImageButton expenses_accounts;
    private ImageButton expenses_budget;
    private Button expenses_create;
    private LinearLayout view;

    private RecyclerView recyclerView;

    private LinearLayout mContainerView;
    private View myView;

    private User user;

    @Override
    public void onClick(View view) {
        Intent intent;
        User user_intent;
        switch (view.getId()) {
            case R.id.home:
                intent = new Intent(this, activity_home.class);
                user_intent = user;
                intent.putExtra("Home_User", user_intent);
                startActivity(intent);
                break;

            case R.id.home_expenses:
                intent = new Intent(this, activity_home_expenses.class);
                user_intent = user;
                intent.putExtra("Home_User", user_intent);
                startActivity(intent);
                break;

            case R.id.home_accounts:
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
                ImageView expense_background = findViewById(R.id.background);
                expense_background.setAlpha(90);


                mContainerView.addView(myView);

                //intent.putExtra("Home_User", user_intent);
                //startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_expenses);

        mContainerView = (LinearLayout)findViewById(R.id.view_expense);
        LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.layout_expenses_create, null);

        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");

      //  Window w = getWindow();
      //  w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        RecyclerView recyclerView = findViewById(R.id.rv_expense);
        List<Expenses> mlist = new ArrayList<>();
        for(int i = 0; i < user.expenses.size(); i++) {
            mlist.add(user.expenses.get(i));
        }
        Expense_Adapter adapter = new Expense_Adapter(this, mlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenses_home = (ImageButton) findViewById(R.id.home);
        expenses_home.setOnClickListener(this);

        expenses = (ImageButton) findViewById(R.id.home_expenses);
        expenses.setOnClickListener(this);

        expenses_accounts = (ImageButton) findViewById(R.id.home_accounts);
        expenses_accounts.setOnClickListener(this);

        expenses_budget = (ImageButton) findViewById(R.id.expenses_budget);
        expenses_budget.setOnClickListener(this);

        expenses_create = (Button) findViewById(R.id.expenses_create);
        expenses_create.setOnClickListener(this);
    }
}
