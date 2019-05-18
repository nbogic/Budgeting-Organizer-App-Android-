package com.example.budgetplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class activity_home_expenses extends AppCompatActivity implements View.OnClickListener {

    //expenses layout buttons
    private ImageButton expenses_home;
    private ImageButton expenses;
    private ImageButton expenses_accounts;
    private ImageButton expenses_budget;
    private Button expenses_create;
    private RecyclerView recyclerView;

    private View myView;
    private User user;
    private static final String TAG = "activity_account_expenses";
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";
    private DatePickerDialog.OnDateSetListener OnDateSetListener;
    private Button button_add_date;
    private Button button_add;
    private Button button_cancel;
    private ImageView expense_background;
    private Spinner category_spinner;
    private Spinner account_spinner;
    private EditText expense_amount;
    private EditText expense_destination;
    private Switch expense_recurring;
    private TextView add_date2;
    private String date = "";

    private LinearLayout view;
    private LinearLayout mContainerView;
    private List<Expenses> mlist;
    private Expense_Adapter adapter;

    ArrayList<String> list_account_items;
    ArrayAdapter<String> account_adapter;

    private int expense_year, expense_month, expense_day;

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
                intent = new Intent(this, activity_home_expenses.class);
                user_intent = user;
                expense_background.setAlpha(90);
                mContainerView.addView(myView);
                break;

            case R.id.expense_add:
                expense_amount = (EditText) myView.findViewById(R.id.edit_amount);
                expense_destination = (EditText) myView.findViewById(R.id.edit_destination);
                category_spinner = (Spinner) myView.findViewById(R.id.expense_spinner2);
                expense_recurring = (Switch) myView.findViewById(R.id.switch_recurring);

                Expenses user_expense;
                //get the expense amount, convert to string
                String Cost = expense_amount.getText().toString();
                Integer validation_counter = 0;

                if (expense_amount.getText().toString().equals("") || equals("0")) {
                    show_alert("You left a field empty!", "Please enter a amount.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if (expense_destination.getText().toString().equals("") || expense_destination.getText().toString() == null) {
                    show_alert("You left a field empty!", "Please enter a destination.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if (date.equals("") || date.equals(null)) {
                    show_alert("You left a field empty!", "Please enter a date.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }


                if (validation_counter.equals(3)) {
                    String account = account_spinner.getSelectedItem().toString();

                    for(int i = 0; i < user.accounts.size(); i++) {
                        if(account.equals(user.accounts.get(i).income_bankname)) {
                            user_expense = new Expenses(expense_destination.getText().toString(), category_spinner.getSelectedItem().toString(), date, expense_recurring.getShowText(), user.accounts.get(i), Long.valueOf(expense_amount.getText().toString()));
                            user.expenses.add(user_expense);
                            expenses_account_load(FALSE);
                            mlist.add(user_expense);
                            mContainerView.removeAllViews();
                            expense_background.setAlpha(200);
                            adapter.notifyDataSetChanged();
                            break;
                        } else {
                            show_alert("You left a field empty!", "Please select a account.");
                        }
                    }

                } else {
                    show_alert("There was a issue.", "Please return and check that all fields are filled.");
                }
                break;

            case R.id.expense_cancel:
                mContainerView.removeAllViews();
                expense_background.setAlpha(200);
                break;

            case R.id.button_add_date:
                //initialise calendar
                Calendar calendar = Calendar.getInstance();

                //get calendar days, months, and year
                expense_year = calendar.get(Calendar.YEAR);
                expense_month = calendar.get(Calendar.MONTH);
                expense_day = calendar.get(Calendar.DAY_OF_MONTH);

                //create dialog, change appearance, include variables created above
                DatePickerDialog exp_dialog = new DatePickerDialog(activity_home_expenses.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, OnDateSetListener, expense_year, expense_month, expense_day);
                exp_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                exp_dialog.show();

                //wait for user input
                OnDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int exp_year, int exp_month, int exp_day) {
                        exp_month++;
                        String exp_date = exp_day + "/" + exp_month + "/" + exp_year;
                        //show the date that the  user has chosen
                        //save in date variable, will be used in the creation of the expense class
                        date = exp_date;
                    }
                };
                break;
        }
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
        alert.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

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
            FileInputStream fis = new FileInputStream(new File(login_file));
            ObjectInputStream ooo = new ObjectInputStream(fis);
            exp_user_gen = (List<User>) ooo.readObject();
            //find matching account using username
            if (READ.equals(false)) {
                for (int i = 0; i < exp_user_gen.size(); i++) {
                    if (user.user_name.equals(exp_user_gen.get(i).user_name)) {
                        //testing
                        System.out.println("User in intent rn: " + user.user_name + "vs" + exp_user_gen.get(i).user_name);
                        exp_user_gen.get(i).user_name = user.user_name;
                        exp_user_gen.get(i).expenses = user.expenses;
                        exp_user_gen.get(i).first_name = user.first_name;
                        exp_user_gen.get(i).last_name = user.last_name;
                        exp_user_gen.get(i).password = user.password;

                        //call for writing, updated list as a parameter
                        expenses_account_write(exp_user_gen);
                        break;
                    }
                }
            } else if (READ.equals(true)) {
                for (int i = 0; i < exp_user_gen.size(); i++) {
                    //testing will be removed later, lists all accounts and their expenses
                    System.out.println("User [#" + i + "]" + "-------" + "Username: " + exp_user_gen.get(i).user_name + "Password: " + exp_user_gen.get(i).password + "First name: " + exp_user_gen.get(i).first_name + "Last name: " + exp_user_gen.get(i).last_name + "Pincode: " + exp_user_gen.get(i).pin_code + "\n");
                    for (int x = 0; x < exp_user_gen.get(i).expenses.size(); x++)
                        System.out.println("User [#" + i + "]" + "-------" + "Expense (Category) " + exp_user_gen.get(i).expenses.get(x).category + "Expense (Recurring): " + exp_user_gen.get(i).expenses.get(x).recurring + "Expense (Date): " + exp_user_gen.get(i).expenses.get(x).date + "Expense (Destination): " + exp_user_gen.get(i).expenses.get(x).destination + "------ \n");
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
        List<String> account_values = new ArrayList<String>();
        account_values.add("Accounts");
        for(int x = 0; x < user.accounts.size(); x++)  {
            account_values.add(user.accounts.get(x).income_bankname);
        }
        ArrayAdapter<String> account_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, account_values);

        account_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_spinner.setAdapter(account_adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_expenses);

        mContainerView = (LinearLayout) findViewById(R.id.view_expense);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.layout_expenses_create, null);

        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User) intent.getSerializableExtra("Home_User");

        //  Window w = getWindow();
        //  w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        expense_background = findViewById(R.id.background);
        account_spinner = myView.findViewById(R.id.expense_spinner2);

        RecyclerView recyclerView = findViewById(R.id.rv_expense);
        mlist = new ArrayList<>();
        for (int i = 0; i < user.expenses.size(); i++) {
            mlist.add(user.expenses.get(i));
        }
        adapter = new Expense_Adapter(this, mlist, user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button_add = (Button) myView.findViewById(R.id.expense_add);
        button_add.setOnClickListener(this);

        button_add_date = (Button) myView.findViewById(R.id.button_add_date);
        button_add_date.setOnClickListener(this);

        button_cancel = (Button) myView.findViewById(R.id.expense_cancel);
        button_cancel.setOnClickListener(this);

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

        load_spinner();

    }
}
