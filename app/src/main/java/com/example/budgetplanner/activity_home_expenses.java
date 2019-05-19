package com.example.budgetplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

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

/**
 * This screen shows all the user's saved expenses, and allows functionality for new expenses to be created, and old ones to be deleted
 */
public class activity_home_expenses extends AppCompatActivity implements View.OnClickListener {

    /**
     * btnExpenseCreate - lanuches the expense creation fragment within the activity
     * login_file - account directory, carried over from previous activities
     * btnAddDate - add a date to the expense
     * containerView - expense creation fragment will be inflated within this view
     */

    private Button btnExpenseCreate;
    private View vMyView;
    private User user;
    private static final String TAG = "activity_home_expenses";
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";
    private DatePickerDialog.OnDateSetListener OnDateSetListener;
    private Button btnAddDate;
    private Button btnAdd;
    private Button btnCancel;
    /**
     * background image for this screen
     */
    private ImageView bgExpense;
    private Spinner spnCategory;
    private Spinner spnAccount;
    private EditText etExpenseAmount;
    private EditText etExpenseDestination;
    private Switch swSwitch;
    private String date = "";

    private LinearLayout containerView;
    private List<Expenses> expensesList;

    private Expense_Adapter adapter;
    BottomNavigationView nav_expenses;
    private Intent intent_pass;

    /**
     * integers reserved for getting the user's chosen date for the expense to be active from
     */
    private int expense_year, expense_month, expense_day;

    /**
     * onClick - listen for button clicks and perform the appropriate function
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        User user_intent;
        switch (view.getId()) {
            case R.id.expenses_create:
                /**
                 * add a new view to containerView (create expense card), and darken the background to promote visibility
                 */
                bgExpense.setAlpha(90);
                containerView.addView(vMyView);
                break;

            case R.id.expense_add:
                /**
                 * get IDs from the elements within vMyView
                 */
                etExpenseAmount = vMyView.findViewById(R.id.edit_amount);
                etExpenseDestination = vMyView.findViewById(R.id.edit_destination);
                spnCategory = vMyView.findViewById(R.id.expense_spinner2);
                swSwitch = vMyView.findViewById(R.id.switch_recurring);

                /**
                 * temporary expense create to contain new user information
                 */
                Expenses user_expense;
                /**
                 * get the expense amount, convert to a String
                 */
                String Cost = etExpenseAmount.getText().toString();

                /**
                 * Validation occurs, all conditional statements must not be true in order to increase validation counter
                 * Validation counter must be a certain number before the expense can be finalized
                 * If a condition is not met, then a dialogue box will pop up on the user's screen
                 */
                Integer validation_counter = 0;

                if (etExpenseAmount.getText().toString().equals("") || equals("0")) {
                    showMessage("You left a field empty!", "Please enter a amount.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if (etExpenseDestination.getText().toString().equals("") || etExpenseDestination.getText().toString() == null) {
                    showMessage("You left a field empty!", "Please enter a destination.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                if (date.equals("") || date.equals(null)) {
                    showMessage("You left a field empty!", "Please enter a date.");
                    validation_counter = 0;

                } else {
                    validation_counter = validation_counter + 1;
                }

                /**
                 * if the counter is equal to 3, then the expense creation can proceed
                 */
                if (validation_counter.equals(3)) {
                    String account = spnAccount.getSelectedItem().toString();

                    /**
                     * loop through all the accounts stored within the user, if the selected account exists and is equal to the one selected by the user, then
                     * a user expense is created containing all the information gathered above
                     * The expense is added to the user's inner Expense list, and is then saved to the file.
                     * Expense is added to a updated RecyclerView, the creation window is dismissed
                     */
                    for(int i = 0; i < user.accounts.size(); i++) {
                        if(account.equals(user.accounts.get(i).income_bankname)) {
                            user_expense = new Expenses(etExpenseDestination.getText().toString(), spnCategory.getSelectedItem().toString(), date, swSwitch.getShowText(), user.accounts.get(i), Long.valueOf(etExpenseAmount.getText().toString()));
                            user.expenses.add(user_expense);
                            Serialization save = new Serialization();
                            expenses_account_load(FALSE);
                            expensesList.add(user_expense);
                            containerView.removeAllViews();
                            bgExpense.setAlpha(200);
                            adapter.notifyDataSetChanged();
                            break;
                        } else {
                            showMessage("You left a field empty!", "Please select a account.");
                        }
                    }

                } else {
                    showMessage("There was a issue.", "Please return and check that all fields are filled.");
                }
                break;

            case R.id.expense_cancel:
                /**
                 * No changes are done, removes the creation view from the screen
                 */
                containerView.removeAllViews();
                bgExpense.setAlpha(200);
                break;

            case R.id.button_add_date:
                /**
                 * initialise the calendar
                 */
                Calendar calendar = Calendar.getInstance();

                /**
                 * get calendar days, months, and year
                 */
                expense_year = calendar.get(Calendar.YEAR);
                expense_month = calendar.get(Calendar.MONTH);
                expense_day = calendar.get(Calendar.DAY_OF_MONTH);

                /**
                 * create dialog, change appearance, include variables created above
                 */
                DatePickerDialog exp_dialog = new DatePickerDialog(activity_home_expenses.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, OnDateSetListener, expense_year, expense_month, expense_day);
                exp_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                exp_dialog.show();

                /**
                 * wait for user input
                 */
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

    /**
     * showMessage - dialog creator, creates customized messages/alerts delivered to the user to inform them about a validation error or other inconsistency
     * @param title - title of the message box, should be a one line brief description
     * @param description - a description of the error in detail, instructing the user what to do to avoid this alert showing again
     * @return void
     */
    private void showMessage(String title, String description) {
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
        spnAccount.setAdapter(account_adapter);

    }

    public void setIntent(Class param_class) {
        intent_pass = new Intent(this, param_class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_expenses);

        nav_expenses = (BottomNavigationView) findViewById(R.id.expense_nav);
        nav_expenses.setItemIconTintList(null);
        nav_expenses.setItemTextColor(null);

        nav_expenses.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                Class change_activity;
                switch (item.getItemId()) {
                    case R.id.expense_home:
                        change_activity = activity_home.class;
                        setIntent(change_activity);
                        intent_pass.putExtra("Home_User", user);
                        startActivity(intent_pass);
                        break;

                    case R.id.expenses:
                        change_activity = activity_home_expenses.class;
                        setIntent(change_activity);
                        intent_pass.putExtra("Home_User", user);
                        startActivity(intent_pass);

                        break;

                    case R.id.expense_account:
                        change_activity = activity_home_accounts.class;
                        setIntent(change_activity);
                        intent_pass.putExtra("Home_User", user);
                        startActivity(intent_pass);

                        break;

                    case R.id.expense_budget:
                        change_activity = activity_home_budget.class;
                        setIntent(change_activity);
                        intent_pass.putExtra("Home_User", user);
                        startActivity(intent_pass);

                        break;
                }
                return false;
            }
        });

        containerView = findViewById(R.id.view_expense);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vMyView = inflater.inflate(R.layout.layout_expenses_create, null);

        Intent intent = getIntent();
        //assign the returned object to the current user object
        user = (User) intent.getSerializableExtra("Home_User");

        bgExpense = findViewById(R.id.background);
        spnAccount = vMyView.findViewById(R.id.expense_spinner2);

        RecyclerView recyclerView = findViewById(R.id.rv_expense);
        expensesList = new ArrayList<>();
        for (int i = 0; i < user.expenses.size(); i++) {
            expensesList.add(user.expenses.get(i));
        }
        adapter = new Expense_Adapter(this, expensesList, user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = vMyView.findViewById(R.id.expense_add);
        btnAdd.setOnClickListener(this);

        btnExpenseCreate = findViewById(R.id.expenses_create);
        btnExpenseCreate.setOnClickListener(this);

        btnAddDate = vMyView.findViewById(R.id.button_add_date);
        btnAddDate.setOnClickListener(this);

        btnCancel = vMyView.findViewById(R.id.expense_cancel);
        btnCancel.setOnClickListener(this);
        load_spinner();

    }
}
