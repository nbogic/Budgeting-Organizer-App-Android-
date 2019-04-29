package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class activity_account_personal extends AppCompatActivity implements View.OnClickListener, Serializable {

    private User home_user;

    private TextView personal_first_name;
    private TextView personal_last_name;
    private TextView personal_first_name2;
    private TextView personal_last_name2;
    private TextView personal_email;
    private TextView personal_username;

    private Button personal_password;
    private Button personal_pincode;

    //decide if the user will be changing their password or pincode
    private String option;

    private ImageView personal_image;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home:
                intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;
            case R.id.home_expenses:
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
                option = "password";
                intent = new Intent(this, activity_home_budget.class);
                intent.putExtra(option, "Option");
                intent.putExtra("home_user2", home_user);
                startActivity(intent);
                break;
            case R.id.personal_change_pin:
                option = "pincode";
                intent = new Intent(this, activity_home_budget.class);
                intent.putExtra(option, "Option");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_personal);

        //get the user object that was passed from the previous activity (login)
        Intent intent = getIntent();
        //assign the returned object to the current user object
        home_user = (User)intent.getSerializableExtra("Home_User");
        //change the TextView to display the user's first name using the new user object, welcome message

        //assign the textviews to their relevant ids
        personal_first_name = (TextView) findViewById(R.id.personal_firstname);
        personal_last_name = (TextView) findViewById(R.id.personal_lastname);
        personal_first_name2 = (TextView) findViewById(R.id.personal_firstname2);
        personal_last_name2 = (TextView) findViewById(R.id.personal_lastname2);
        personal_email = (TextView) findViewById(R.id.personal_email);
        personal_username = (TextView) findViewById(R.id.personal_username);

        System.out.println("Details from home screen ----- username: " + home_user.user_name + "password: " + home_user.password + "email: " + home_user.email + "first name: " + home_user.first_name);


        //include all the user's current details in the textviews
        personal_first_name.setText("First name: " + home_user.first_name);
        personal_last_name.setText("Last name: " + home_user.last_name);
        personal_first_name2.setText("First name: " + home_user.first_name);
        personal_last_name2.setText("First name: " + home_user.first_name);
        personal_email.setText("Email: " + home_user.email);
        personal_username.setText("Username " + home_user.user_name);

        personal_password = (Button) findViewById(R.id.personal_change_pass);
        personal_password.setOnClickListener(this);

        personal_pincode = (Button) findViewById(R.id.personal_change_pin);
        personal_pincode.setOnClickListener(this);
    }
}
