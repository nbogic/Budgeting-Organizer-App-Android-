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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class activity_home_accounts extends AppCompatActivity implements View.OnClickListener {

    //accounts layout buttons
    private ImageButton accounts_home;
    private ImageButton accounts_expenses;
    private ImageButton accounts;
    private ImageButton accounts_budget;
    private Button accounts_create;
    private User user;
    private static final String TAG = "activity_account_accounts";
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";
    private DatePickerDialog.OnDateSetListener OnDateSetListener;
    private TextView add_date3;
    private Button account_add;
    private Button account_cancel;
    private Button account_income_add;
    private Spinner account_spinner;
    private Spinner account_spinner2;
    private EditText income_amount;
    private EditText bank_name;
    private EditText account_balance;
    private String selection;
    private String date;
    private int income_year, income_month, income_day;

    private LinearLayout mContainerView;
    private View myView;
    private ImageView account_background;
    private List<Accounts> mlist;
    private RecyclerView.Adapter adapter;

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
                account_background.setAlpha(90);
                mContainerView.addView(myView);
                break;

            case R.id.income_add_date:
                //initialise calendar
                Calendar calendar = Calendar.getInstance();

                //get calendar days, months, and year
                income_year = calendar.get(Calendar.YEAR);
                income_month = calendar.get(Calendar.MONTH);
                income_day = calendar.get(Calendar.DAY_OF_MONTH);

                //create dialog, change appearance, include variables created above
                DatePickerDialog exp_dialog = new DatePickerDialog(activity_home_accounts.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, OnDateSetListener, income_year, income_month, income_day);
                exp_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
                exp_dialog.show();

                //wait for user input
                OnDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int inc_year, int inc_month, int inc_day) {
                        inc_month++;
                        String income_date = inc_day + "/" + inc_month + "/" + inc_year;
                        System.out.println(income_date);
                        //show the date that the  user has chosen
                        //save in date variable, will be used in the creation of the expense class
                        date = income_date;
                    }
                };
                break;

            case R.id.account_add:
                account_spinner = (Spinner) myView.findViewById(R.id.accounts_spinner);
                income_amount = (EditText) myView.findViewById(R.id.income_amount);;
                bank_name = (EditText) myView.findViewById(R.id.bank_name);;
                account_balance = (EditText) myView.findViewById(R.id.edit_balance);;
                int validation_counter = 0;

                if (income_amount.getText().toString().equals("") || equals("0")) {
                    show_alert("You left a field empty!", "Please enter your income.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if (bank_name.equals("") || bank_name.equals(null)) {
                    show_alert("You left a field empty!", "Please enter the name of your bank.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if (account_balance.equals("") || account_balance.equals(null)) {
                    show_alert("You left a field empty!", "Please enter a budget amount.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if(validation_counter == 3) {
                    Accounts user_account = new Accounts(bank_name.getText().toString(), account_spinner.getSelectedItem().toString(), selection, account_balance.getText().toString(), Long.valueOf(income_amount.getText().toString()));
                    user.accounts.add(user_account);
                    accounts_account_load(user_account);
                    mlist.add(user_account);
                    mContainerView.removeAllViews();
                    account_background.setAlpha(200);
                    adapter.notifyDataSetChanged();
                } else {
                    show_alert("There was a issue.", "Please return and check that all fields are filled.");
                }

                break;
            case R.id.account_cancel:
                mContainerView.removeAllViews();
                account_background.setAlpha(200);

        }
        }


    //write function, overwrites the entire file with a new list
    public List<User> accounts_account_write(List<User> new_user) {
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
    public List<User> accounts_account_load(Accounts acc) {
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
                    exp_user_gen.get(i).accounts = user.accounts;

                    //call for writing, updated list as a parameter
                    accounts_account_write(exp_user_gen);
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
        alert.getWindow().setBackgroundDrawableResource(android.R.color.holo_green_light);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_accounts);

        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");
    //   System.out.println("Details from home screen ----- username: " + user.user_name + "password: " + user.password + "email: " + user.email + "first name: " + user.first_name);

        mContainerView = (LinearLayout) findViewById(R.id.view_account);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.layout_accounts_create, null);

        account_background = findViewById(R.id.background);

        account_add = (Button) myView.findViewById(R.id.account_add);
        account_add.setOnClickListener(this);

        account_cancel = (Button) myView.findViewById(R.id.account_cancel);
        account_cancel.setOnClickListener(this);

        account_income_add = (Button) myView.findViewById(R.id.income_add_date);
        account_income_add.setOnClickListener(this);

        account_spinner2 = (Spinner) myView.findViewById(R.id.accounts_spinner2);
        add_date3 = (TextView) myView.findViewById(R.id.add_date3);

        RecyclerView recyclerView = findViewById(R.id.rv_account);
        mlist = new ArrayList<>();
        for(int i = 0; i < user.accounts.size(); i++) {
            mlist.add(user.accounts.get(i));
        }
        adapter = new Account_Adapter(this, mlist, user);
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

        //check spinner selection, if the user chooses something other than a daily income, a option to choose a specific day will be visible
        account_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selection = parent.getItemAtPosition(position).toString();
                if(selection.equals("Weekly") || selection.equals("Fortnightly") || selection.equals("Monthly") || selection.equals("Yearly"))
                {
                    account_income_add.setVisibility(VISIBLE);
                } else { account_income_add.setVisibility(INVISIBLE);}
            }
            public void onNothingSelected(AdapterView<?> parent)
            {


            }
        });

    }
    }




