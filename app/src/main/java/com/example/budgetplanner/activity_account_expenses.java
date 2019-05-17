package com.example.budgetplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class activity_account_expenses extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "activity_account_expenses";
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";

    private DatePickerDialog.OnDateSetListener OnDateSetListener;
    private Button button_add_date;
    private Button button_add;
    private Button button_cancel;

    private Spinner category_spinner;
    private Spinner account_spinner;

    private EditText expense_amount;
    private EditText expense_destination;

    private Switch expense_recurring;

    private TextView add_date2;
    private User user;
    private String date;


    private int expense_year, expense_month, expense_day;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.expense_add:
                //initialise widgets
                expense_amount = (EditText) findViewById(R.id.edit_amount);;
                expense_destination = (EditText) findViewById(R.id.edit_destination);
               // category_spinner = (Spinner) findViewById(R.id.expense_spinner2);
                expense_recurring = (Switch) findViewById(R.id.switch_recurring);

                //get the expense amount, convert to string
                String Cost = expense_amount.getText().toString();

                //create the expense class now, which will later be added to the user's account
                Expenses user_expense = new Expenses(expense_destination.getText().toString(), category_spinner.getSelectedItem().toString(), date, TRUE, 3434);
                //add to the inner attribute (list of expense objects)
                user.expenses.add(user_expense);
                //load and rewrite the file with the updated details
                expenses_account_load(FALSE);

                //return to the home activity
                intent = new Intent(this, activity_home.class);
                intent.putExtra("Home_User", user);
                startActivity(intent);
                break;

            case R.id.budget_cancel:
                //no changes have occurred, return to the home screen
                intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;

            case R.id.button_add_date:
                //initialise calendar
                Calendar calendar = Calendar.getInstance();

                //get calendar days, months, and year
                expense_year = calendar.get(Calendar.YEAR);
                expense_month = calendar.get(Calendar.MONTH);
                expense_day = calendar.get(Calendar.DAY_OF_MONTH);

                //create dialog, change appearance, include variables created above
                DatePickerDialog exp_dialog = new DatePickerDialog(activity_account_expenses.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, OnDateSetListener, expense_year, expense_month, expense_day);
                exp_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
                exp_dialog.show();

                //wait for user input
                OnDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int exp_year, int exp_month, int exp_day) {
                        exp_month++;
                        String exp_date = exp_day + "/" + exp_month + "/" + exp_year;
                        //show the date that the  user has chosen
                        add_date2.setText(date);
                        //save in date variable, will be used in the creation of the expense class
                        date = exp_date;
                    }
                };
                break;
        }
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_expense);

        //get the user object that was passed from the previous activity (login)
        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User)intent.getSerializableExtra("Home_User");

        button_add = (Button) findViewById(R.id.expense_add);
        button_add.setOnClickListener(this);

        button_cancel = (Button) findViewById(R.id.budget_cancel);
        button_cancel.setOnClickListener(this);

        add_date2 = (TextView) findViewById(R.id.add_date2);

    }
}
