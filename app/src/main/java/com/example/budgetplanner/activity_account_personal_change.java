package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
/**
 * The purpose of this screen changes depending on the contents of the intent, it can either be used to change the password or pin code of the user
 */
public class activity_account_personal_change extends AppCompatActivity implements View.OnClickListener, Serializable {

    /**
     * btnConfirm - saves the changes and writes to file
     * btnCancel - cancels the changes and returns to the previous screen
     */
    private Button btnConfirm;
    private Button btnCancel;

    private TextView tvChangeTitle;
    private TextView tvEnterCurrent;
    private TextView tvEnterNew;
    private TextView tvEnterNew2;

    private User home_user2;
    private EditText etOldPass;
    private EditText etNew;
    private EditText etNew2;

    public String option1;

    /**
     * onClick - listen for button clicks and perform the appropriate function
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home:
                intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;
            case R.id.accounts_expenses:
                intent = new Intent(this, activity_home_expenses.class);
                startActivity(intent);
                break;
            case R.id.home_accounts:
                intent = new Intent(this, activity_home_accounts.class);
                startActivity(intent);
                break;
            case R.id.home_budget:
                intent = new Intent(this, activity_home_budget.class);
                startActivity(intent);
                break;
            case R.id.personal_confirm:
                /**
                 * decides which information will be tested based on the data gathered from the intent created from the previous activity
                 */
                if(option1.equals("password")) {
                    /**
                     * Function call (changeCredentials) - call function and inform it to perform the block of code relating to password changing
                     */
                    changeCredentials("password_confirm");
                }
                if(option1.equals("pincode")) {
                    /**
                     * Function call (changeCredentials) - call function and inform it to perform the block of code relating to pin code changing
                     */
                    changeCredentials("pincode_confirm");
                }
                break;
            case R.id.personal_cancel:
                intent = new Intent(this, activity_account_personal.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * changeCredentials - changes the TextView and function implementation based on a String parameter (pincode or password)
     * @param option - String parameter, requires to contain one of two options (password or pincode) for the function to complete its purpose
     * @return void
     */
    public void changeCredentials(String option) {
        switch (option) {
            /**
             * password_confirm, get the user input from the views, assign it to strings, validation check to see if passwords match, return to previous activity with newly updated user object (password)
             */
            case "password_confirm":
                etOldPass = findViewById(R.id.edit_current);
                etNew = findViewById(R.id.edit_new);
                etNew2 = findViewById(R.id.edit_new2);

                String old_password, new_password, new_password2;
                old_password = etOldPass.getText().toString();
                new_password = etNew.getText().toString();
                new_password2 = etNew2.getText().toString();
                /**
                 * validation check to see if new and old passwords match
                 */
                if(old_password.equals(home_user2.password) || new_password.equals(new_password2)) {
                    home_user2.password = new_password;
                    Intent intent = new Intent(this, activity_account_personal.class);
                    intent.putExtra("home_user_new", home_user2);
                    startActivity(intent);
                }
                break;

            /**
             * pincode_confirm, get the user input from the views, assign it to strings, validation check to see if passwords match, return to previous activity with newly updated user object (password)
             */
            case "pincode_confirm":
                etOldPass = findViewById(R.id.edit_current);
                etNew = findViewById(R.id.edit_new);
                etNew2 = findViewById(R.id.edit_new2);

                String old_pin, new_pin;
                String new_pin2;
                old_pin = etOldPass.getText().toString();
                new_pin = etNew.getText().toString();
                new_pin2 = etNew2.getText().toString();
                /**
                 * validation check to see if new and old pin codes match
                 */
              if(old_pin.equals(home_user2.pin_code) || new_pin.equals(new_pin2)) {
                    home_user2.pin_code = new_pin;
                    Intent intent = new Intent(this, activity_account_personal.class);
                    intent.putExtra("home_user_new", home_user2);
                    startActivity(intent);
                }
                break;
            /**
             * set labels to refer to the password
             */
            case "password":
                tvChangeTitle.setText("Change your password");
                tvEnterCurrent.setText("Enter your current password");
                tvEnterNew.setText("Enter a new password");
                tvEnterNew2.setText("Enter the password again");
                break;
            /**
             * set labels to refer to the pin code
             */
            case "pincode":
                tvChangeTitle.setText("Change your pincode");
                tvEnterCurrent.setText("Enter your current pincode");
                tvEnterNew.setText("Enter a new pincode");
                tvEnterNew2.setText("Enter the pincode again");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_personal_change);

        btnConfirm = findViewById(R.id.personal_confirm);
        btnConfirm.setOnClickListener(this);

        btnCancel = findViewById(R.id.personal_cancel);
        btnCancel.setOnClickListener(this);

        tvChangeTitle = findViewById(R.id.home_change_title);
        tvEnterCurrent = findViewById(R.id.home_change_current);
        tvEnterNew = findViewById(R.id.home_change_new);
        tvEnterNew2 = findViewById(R.id.home_change_new2);

        Intent intent = getIntent();

        /**
         * get the option string from previous activity, can only be one of two different values
         */
        option1 = intent.getStringExtra("Option1");
        home_user2 = (User) intent.getSerializableExtra("home_user2");

        /**
         * use this function to set all the textviews to their relevant labels (password/pincode)
         */
        changeCredentials(option1);



    }
}
