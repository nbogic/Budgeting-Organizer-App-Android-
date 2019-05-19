package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * This screen retrieves and shows the user's personal details, and offers a option to change their details (rewrite the file)
 */
public class activity_account_personal extends AppCompatActivity implements View.OnClickListener, Serializable {

    private User home_user;

    /**
     * TextViews that will display all the relevant user account information
     */
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvFirstName2;
    private TextView tvLastName2;
    private TextView tvEmail;
    private TextView tvUserName;

    private Button btnPass;
    private Button btnPin;

    /**
     * The value of this String will decide if the user will be changing their password or pin code
     */
    private String option;

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
            case R.id.personal_change_pass:
                /**
                 * Id.personal_change_pass - option will be assigned 'password' and attached to the intent
                 */
                intent = new Intent(this, activity_account_personal_change.class);
                option = "password";
                intent.putExtra("Option1", option);
                intent.putExtra("home_user2", home_user);
                startActivity(intent);
                break;
            case R.id.personal_change_pin:
                /**
                 * Id.personal_change_pin - option will be assigned 'pincode' and attached to the intent
                 */
                intent = new Intent(this, activity_account_personal_change.class);
                option = "pincode";
                intent.putExtra("home_user2", home_user);
                intent.putExtra("Option1", option);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_personal);

        /**
         * get the user object that was passed from the previous activity (login)
         * assign the returned object to the current user object
         */
        Intent intent = getIntent();
        home_user = (User)intent.getSerializableExtra("Home_User");

        /**
         * assign the TextViews to their relevant IDs
         */
        tvFirstName = findViewById(R.id.personal_firstname);
        tvLastName = findViewById(R.id.personal_lastname);
        tvFirstName2 = findViewById(R.id.personal_firstname2);
        tvLastName2 = findViewById(R.id.personal_lastname2);
        tvEmail = findViewById(R.id.personal_email);
        tvUserName = findViewById(R.id.personal_username);

        /**
         * include all the user's current details in the textviews
         */
        tvFirstName.setText("First name: " + home_user.first_name);
        tvLastName.setText("Last name: " + home_user.last_name);
        tvFirstName2.setText("First name: " + home_user.first_name);
        tvLastName2.setText("First name: " + home_user.first_name);
        tvEmail.setText("Email: " + home_user.email);
        tvUserName.setText("Username " + home_user.user_name);

        btnPass = findViewById(R.id.personal_change_pass);
        btnPass.setOnClickListener(this);

        btnPin = findViewById(R.id.personal_change_pin);
        btnPin.setOnClickListener(this);
    }
}
