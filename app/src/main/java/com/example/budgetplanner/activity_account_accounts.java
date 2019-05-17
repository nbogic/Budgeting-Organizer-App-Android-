package com.example.budgetplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

public class activity_account_accounts extends AppCompatActivity implements View.OnClickListener  {

    private static final String TAG = "activity_account_accounts";
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";
    private DatePickerDialog.OnDateSetListener OnDateSetListener;

    private TextView add_date3;

    private Button account_add;
    private Button account_cancel;
    private Button account_income_add;

    private EditText account_name;
    private Spinner account_spinner;
    private Spinner account_spinner2;
    private EditText income_amount;
    private EditText bank_name;
    private EditText account_balance;
    private User user;

    private String selection;

    private String date;

    private int income_year, income_month, income_day;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.income_add_date:
                //initialise calendar
                Calendar calendar = Calendar.getInstance();

                //get calendar days, months, and year
                income_year = calendar.get(Calendar.YEAR);
                income_month = calendar.get(Calendar.MONTH);
                income_day = calendar.get(Calendar.DAY_OF_MONTH);

                //create dialog, change appearance, include variables created above
                DatePickerDialog exp_dialog = new DatePickerDialog(activity_account_accounts.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, OnDateSetListener, income_year, income_month, income_day);
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
                        add_date3.setText(income_date);
                        //save in date variable, will be used in the creation of the expense class
                        date = income_date;
                    }
                };
                break;
            case R.id.account_add:
                account_name = (EditText) findViewById(R.id.edit_destination);;
                account_spinner = (Spinner) findViewById(R.id.accounts_spinner);
                income_amount = (EditText) findViewById(R.id.income_amount);;
                bank_name = (EditText) findViewById(R.id.bank_name);;
                account_balance = (EditText) findViewById(R.id.edit_amount);;

                Accounts user_account = new Accounts(account_name.getText().toString(), account_spinner.getSelectedItem().toString(), selection, account_balance.getText().toString(), bank_name.getText().toString());
                user.accounts.add(user_account);
                accounts_account_load(user_account);

                //return to the home activity
                Intent intent;
                for(int i = 0; i < user.accounts.size(); i++) {
                System.out.println("Account name: " + user.accounts.get(i).account_name + "Account type: " +  user.accounts.get(i).account_type + "Income amount: " + user.accounts.get(i).income_bankname + "Account balance: " + user.accounts.get(i).account_balance); }
                intent = new Intent(this, activity_home.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_accounts);

        //get the user object that was passed from the previous activity (login)
        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");

        account_add = (Button) findViewById(R.id.account_add);
        account_add.setOnClickListener(this);

        account_cancel = (Button) findViewById(R.id.account_cancel);
        account_cancel.setOnClickListener(this);

        account_income_add = (Button) findViewById(R.id.income_add_date);
        account_income_add.setOnClickListener(this);

        account_spinner2 = (Spinner) findViewById(R.id.accounts_spinner2);
        add_date3 = (TextView) findViewById(R.id.add_date3);

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
