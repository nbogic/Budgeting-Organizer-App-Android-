package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.TRUE;

public class activity_account_budget extends AppCompatActivity implements View.OnClickListener {
    private EditText budget_name;
    private Spinner budget_category;
    private Spinner budget_addexpense;
    private Spinner budget_addaccount;

    private ListView list_accounts;
    private ListView list_expenses;

    private Button clear_expenses;
    private Button clear_accounts;

    private Spinner budget_addcategory;
    private EditText budget_duration;
    private String budget_date;
    private EditText budget_amount;
    ArrayList<String> list_expense_items;
    ArrayAdapter<String> expense_adapter;

    ArrayList<String> list_account_items;
    ArrayAdapter<String> account_adapter;


    private int expense_counter = 1;
    private int account_counter = 1;

    private User user;

    private static final String TAG = "activity_account_expenses";
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";



    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.budget_add:
                budget_name = (EditText) findViewById(R.id.edit_destination);;
                budget_category = (Spinner) findViewById(R.id.budget_category);
                budget_duration = (EditText) findViewById(R.id.budget_duration);;
                budget_date = "testing";
                budget_amount = (EditText) findViewById(R.id.budget_amount);;

                Budget user_budget = new Budget("this is name", "this is duration", "hjjhhj", "this is amount", "np");
              /*  for(int i = 0; i < list_expense_items.size(); i++) {
                    if(user.expenses.get(i).destination.equals(list_expense_items.get(i)))
                        System.out.println("Expense has been added:");

                    user_budget.add_expense(user.expenses.get(i));
                }

                for(int i = 0; i < list_account_items.size(); i++) {
                    if(user.accounts.get(i).account_name.equals(list_account_items.get(i)))
                        user_budget.add_account(user.accounts.get(i));
                    System.out.println("acc has been added:");

                }
                */
                user.budgets.add(user_budget);
             System.out.println("Budget accounts - account name: " + user_budget.name);
                System.out.println("Budget accounts - account namewww: " + user_budget.expenses.get(0).destination);


                intent = new Intent(this, activity_home.class);
                intent.putExtra("Home_User", user);
//                for(int i = 0; i < user.budgets.size(); i++) {
                 //   System.out.println("Budget accounts - account name: " + user.budgets.get(i).accounts.get(i).account_name);

              //  }

               // startActivity(intent);
                break;

            case R.id.budget_cancel:
                intent = new Intent(this, activity_home.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;
            case R.id.budget_clear:
                list_expense_items.clear();
                clear_expenses.setVisibility(INVISIBLE);
                expense_adapter.notifyDataSetChanged();


            case R.id.budget_clear2:
                list_account_items.clear();
                clear_accounts.setVisibility(INVISIBLE);
                account_adapter.notifyDataSetChanged();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_budget);

        budget_addexpense = (Spinner) findViewById(R.id.budget_addexpense);
        budget_addaccount = (Spinner) findViewById(R.id.budget_addaccount);

        clear_expenses = (Button) findViewById(R.id.budget_clear);
        clear_expenses.setOnClickListener(this);

        clear_accounts = (Button) findViewById(R.id.budget_clear2);
        clear_accounts.setOnClickListener(this);

        //get the user object that was passed from the previous activity (login)
        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");

        list_expenses =  (ListView) findViewById(R.id.list_expenses);
        list_accounts = (ListView) findViewById(R.id.list_accounts);

        list_expense_items = new ArrayList<String>();
        expense_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_expense_items);
        list_expenses.setAdapter(expense_adapter);

        list_account_items = new ArrayList<String>();
        account_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_account_items);
        list_accounts.setAdapter(account_adapter);

        load_spinner();

        budget_addexpense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                System.out.println("Expense spinner selected..");

                if (expense_counter > 1) {
                    clear_expenses.setVisibility(VISIBLE);
                    String selection;
                    selection = parent.getItemAtPosition(position).toString();
                    if(selection != "Choose your expenses:")
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

        budget_addaccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (account_counter > 1) {
                    clear_accounts.setVisibility(VISIBLE);
                    String selection;
                    selection = parent.getItemAtPosition(position).toString();
                    if (selection != "Choose your accountss:") {
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

    //write function, overwrites the entire file with a new list
    public List<User> expenses_account_write(List<User> new_user) {
        //testing for feedback
        FileOutputStream file_out;
        File file = new File(login_file);
        try {
            file_out = new FileOutputStream(file);
            ObjectOutputStream user_out = new ObjectOutputStream(file_out);
            user_out.writeObject(new_user);
            user_out.flush();
            user_out.close();
            expenses_account_load(TRUE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new_user;
    }

    //modified reading function, made to overwrite the current file with the inclusion of a new expense object
    public List<User> expenses_account_load(Boolean READ) {
        System.out.println("test");
        List<User> exp_user_gen = new ArrayList<User>();
        try {
            FileInputStream fis = new FileInputStream (new File(login_file));
            ObjectInputStream ooo = new ObjectInputStream(fis);
            exp_user_gen = (List<User>) ooo.readObject();
            //find matching account using username
            if(READ.equals(false)) {
                for(int i = 0; i < exp_user_gen.size(); i++) {
                    if (user.user_name.equals(exp_user_gen.get(i).user_name)) {
                        //testing
                        System.out.println("User in intent rn: "  + user.user_name + "vs" + exp_user_gen.get(i).user_name);
                        exp_user_gen.get(i).user_name = user.user_name;
                        exp_user_gen.get(i).expenses = user.expenses;
                        exp_user_gen.get(i).first_name = user.first_name;
                        exp_user_gen.get(i).last_name = user.last_name;
                        exp_user_gen.get(i).password = user.password;

                        //call for writing, updated list as a parameter
                        expenses_account_write(exp_user_gen);
                        break;
                    }
                } } else if (READ.equals(true)) {
                for(int i = 0; i < exp_user_gen.size(); i++) {
                    //testing will be removed later, lists all accounts and their expenses
                    System.out.println("User [#"  + i + "]" + "-------" + "Username: " + exp_user_gen.get(i).user_name + "Password: " + exp_user_gen.get(i).password + "First name: " + exp_user_gen.get(i).first_name + "Last name: " + exp_user_gen.get(i).last_name + "Pincode: " + exp_user_gen.get(i).pin_code + "\n");
                    for(int x = 0; x < exp_user_gen.get(i).expenses.size(); x++)
                        System.out.println("User [#"  + i + "]" + "-------" + "Expense (Category) " + exp_user_gen.get(i).expenses.get(x).category + "Expense (Recurring): " + exp_user_gen.get(i).expenses.get(x).recurring + "Expense (Date): " + exp_user_gen.get(i).expenses.get(x).date + "Expense (Destination): " + exp_user_gen.get(i).expenses.get(x).destination + "------ \n"  );
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
        accounts_spinner_values.add("Choose your accounts:");
        expenses_spinner_values.add("Choose your expenses:");

        for(int i = 0; i < user.expenses.size(); i++)  {
            expenses_spinner_values.add(user.expenses.get(i).destination);
        }

        for(int x = 0; x < user.accounts.size(); x++)  {
            accounts_spinner_values.add(user.accounts.get(x).account_name);
            System.out.println("Account name: " + user.accounts.get(x).account_name );

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
