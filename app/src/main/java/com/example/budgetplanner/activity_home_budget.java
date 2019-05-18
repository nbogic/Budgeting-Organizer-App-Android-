package com.example.budgetplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class activity_home_budget extends AppCompatActivity implements View.OnClickListener {

    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";
    private static final String TAG = "activity_home_budget";

    //budget layout buttons
    private ImageButton budget_home;
    private ImageButton budget_expenses;
    private ImageButton budget_accounts;
    private ImageButton budget;
    private Button budget_add;
    private Button budget_create;
    private Button budget_add_date;
    private Button budget_cancel;

    private User user;
    private EditText budget_name;
    private Spinner budget_category;
    private Spinner budget_add_expense;
    private Spinner budget_add_account;
    private ImageView budget_background;
    private ImageView budget_background2;

    private ListView list_accounts;
    private ListView list_expenses;

    private Button clear_expenses;
    private Button clear_accounts;
    private View myView;

    private DatePickerDialog.OnDateSetListener OnDateSetListener;
    private Spinner budget_addcategory;
    private EditText budget_duration;
    private String budget_date = "";
    private EditText budget_amount;
    ArrayList<String> list_expense_items;
    ArrayAdapter<String> expense_adapter;

    ArrayList<String> list_account_items;
    ArrayAdapter<String> account_adapter;
    private int expense_counter = 1;
    private int account_counter = 1;

    private LinearLayout view;
    private LinearLayout mContainerView;
    private List<Budget> mlist;
    private Budget_Adapter adapter;
    private int budget_year, budget_month, budget_day;

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
                budget_background.setAlpha(90);
                mContainerView.addView(myView);

                break;

            case R.id.budget_add:
                budget_name = (EditText) myView.findViewById(R.id.budget_name);
                budget_category = (Spinner) myView.findViewById(R.id.budget_category);
                budget_amount = (EditText) myView.findViewById(R.id.budget_amount);
                int validation_counter = 0;

                if (budget_name.getText().toString().equals("") || equals("0")) {
                    show_alert("You left a field empty!", "Please enter a budget name.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if (budget_date.equals("") || budget_date.equals(null)) {
                    show_alert("You left a field empty!", "Please enter a date.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if (budget_amount.equals("") || budget_amount.equals(null)) {
                    show_alert("You left a field empty!", "Please enter a budget amount.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if(validation_counter == 3) {

                    Budget user_budget = new Budget(budget_name.getText().toString(), budget_date, Long.valueOf(budget_amount.getText().toString()), budget_category.getSelectedItem().toString());
                    for(int i = 0; i < list_expense_items.size(); i++) {
                        if(user.expenses.get(i).destination.equals(list_expense_items.get(i)))
                            user_budget.add_expense(user.expenses.get(i));
                    }

                    for(int i = 0; i < list_account_items.size(); i++) {
                        if(user.accounts.get(i).income_bankname.equals(list_account_items.get(i)))
                            user_budget.add_account(user.accounts.get(i));
                    }

                    user.budgets.add(user_budget);
                    budgets_account_load();
                    mlist.add(user_budget);
                    mContainerView.removeAllViews();
                    budget_background.setAlpha(200);
                    adapter.notifyDataSetChanged();
                    break;
                } else {
                    show_alert("There was a issue.", "Please return and check that all fields are filled.");
                }


            case R.id.budget_cancel:
                mContainerView.removeAllViews();
                budget_background.setAlpha(200);

                break;
            case R.id.budget_clear:
                list_expense_items.clear();
                clear_expenses.setVisibility(INVISIBLE);
                expense_adapter.notifyDataSetChanged();


            case R.id.budget_clear2:
                list_account_items.clear();
                clear_accounts.setVisibility(INVISIBLE);
                account_adapter.notifyDataSetChanged();

            case R.id.button_add_date:
                //initialise calendar
                Calendar calendar = Calendar.getInstance();

                //get calendar days, months, and year
                budget_year = calendar.get(Calendar.YEAR);
                budget_month = calendar.get(Calendar.MONTH);
                budget_day = calendar.get(Calendar.DAY_OF_MONTH);

                //create dialog, change appearance, include variables created above
                DatePickerDialog exp_dialog = new DatePickerDialog(activity_home_budget.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, OnDateSetListener, budget_year, budget_month, budget_day);
                exp_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                exp_dialog.show();

                //wait for user input
                OnDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int exp_year, int exp_month, int exp_day) {
                        exp_month++;
                        String exp_date = exp_day + "/" + exp_month + "/" + exp_year;
                        budget_date = exp_date;
                    }
                };
                break;
        }
    }

    public List<User> budgets_account_write(List<User> new_user) {
        //testing for feedback
        System.out.println("Created!");
        FileOutputStream file_out;
        File file = new File(login_file);
        try {
            file_out = new FileOutputStream(file);
            ObjectOutputStream user_out = new ObjectOutputStream(file_out);
            user_out.writeObject(new_user);
            user_out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new_user;
    }

    //modified reading function, made to overwrite the current file with the inclusion of a new expense object
    public List<User> budgets_account_load() {
        List<User> exp_user_gen = new ArrayList<User>();
        try {
            FileInputStream fis = new FileInputStream (new File(login_file));
            ObjectInputStream ooo = new ObjectInputStream(fis);
            exp_user_gen = (List<User>) ooo.readObject();

            //find matching account using username
            for(int i = 0; i < exp_user_gen.size(); i++) {
                if(user.user_name.equals(exp_user_gen.get(i).user_name)) {
                    //testing
                    //match has been found, function now assigns the specific user account with the updated user created within this activity
                    exp_user_gen.get(i).budgets = user.budgets;

                    //call for writing, updated list as a parameter
                    budgets_account_write(exp_user_gen);
                    break;
                }
            }
            ooo.close();
            //exceptions
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return exp_user_gen;
    }

    public void load_spinner() {
        List<String> expenses_spinner_values = new ArrayList<String>();
        List<String> accounts_spinner_values = new ArrayList<String>();
        accounts_spinner_values.add("Accounts");
        expenses_spinner_values.add("Expenses");

        for(int i = 0; i < user.expenses.size(); i++)  {
            expenses_spinner_values.add(user.expenses.get(i).destination);
        }

        for(int x = 0; x < user.accounts.size(); x++)  {
            accounts_spinner_values.add(user.accounts.get(x).income_bankname + " - " + user.accounts.get(x).account_type);
        }
        ArrayAdapter<String> expense_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, expenses_spinner_values);
        ArrayAdapter<String> account_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, accounts_spinner_values);

        expense_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        budget_add_expense.setAdapter(expense_adapter);
        budget_add_account.setAdapter(account_adapter);

    }

    private void show_alert(String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(description);
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.holo_orange_light);

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

        mContainerView = (LinearLayout) findViewById(R.id.view_budget);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.layout_budgets_create, null);

        RecyclerView recyclerView = findViewById(R.id.rv_budget);
        mlist = new ArrayList<>();
        for(int i = 0; i < user.budgets.size(); i++) {
            mlist.add(user.budgets.get(i));
        }
        adapter = new Budget_Adapter(this, mlist, user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        budget_home = (ImageButton) findViewById(R.id.home);
        budget_home.setOnClickListener(this);

        budget_expenses = (ImageButton) findViewById(R.id.home_expenses);
        budget_expenses.setOnClickListener(this);

        budget_background = findViewById(R.id.background);

        budget = (ImageButton) findViewById(R.id.budget);
        budget.setOnClickListener(this);

        budget_accounts = (ImageButton) findViewById(R.id.home_accounts);
        budget_accounts.setOnClickListener(this);

        budget_cancel = (Button) myView.findViewById(R.id.budget_cancel);
        budget_cancel.setOnClickListener(this);

        budget_create = (Button) findViewById(R.id.budget_create);
        budget_create.setOnClickListener(this);

        budget_add_date = (Button) myView.findViewById(R.id.button_add_date);
        budget_add_date.setOnClickListener(this);

        budget_add = (Button) myView.findViewById(R.id.budget_add);
        budget_add.setOnClickListener(this);

        budget_add_expense = (Spinner) myView.findViewById(R.id.budget_addexpense);
        budget_add_account = (Spinner) myView.findViewById(R.id.budget_addaccount);

        clear_expenses = (Button) myView.findViewById(R.id.budget_clear);
        clear_expenses.setOnClickListener(this);

        clear_accounts = (Button) myView.findViewById(R.id.budget_clear2);
        clear_accounts.setOnClickListener(this);

        list_expenses =  (ListView) myView.findViewById(R.id.list_expenses);
        list_accounts = (ListView) myView.findViewById(R.id.list_accounts);

        list_expense_items = new ArrayList<String>();
        expense_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_expense_items);
        list_expenses.setAdapter(expense_adapter);

        list_account_items = new ArrayList<String>();
        account_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_account_items);
        list_accounts.setAdapter(account_adapter);

        load_spinner();

        budget_add_expense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                System.out.println("Expense spinner selected..");

                if (expense_counter > 1) {
                    clear_expenses.setVisibility(VISIBLE);
                    String selection;
                    selection = parent.getItemAtPosition(position).toString();
                    if(selection != "Expenses")
                        list_expense_items.add(selection);
                    expense_adapter.notifyDataSetChanged();
                }

                expense_counter = expense_counter + 1;
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                System.out.println("Expense spinner selected..");

                expense_counter = expense_counter + 1;

            }
        });

        budget_add_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (account_counter > 1) {
                    clear_accounts.setVisibility(VISIBLE);
                    String selection;
                    selection = parent.getItemAtPosition(position).toString();
                    if (selection != "Accounts") {
                        list_account_items.add(selection);
                        account_adapter.notifyDataSetChanged();
                    }
                }
                account_counter = account_counter + 1;

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

                account_counter = account_counter + 1;

            }
        });

    }
    }

