package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_account_create extends AppCompatActivity implements View.OnClickListener {

    private Button intro_cancel;
    private Button intro_create;
    private Button intro_next;
    private Button intro_back;

    ///private EditText intro_username;
    //private EditText intro_password;
    private EditText intro_password2;
   // private EditText intro_email;

    private EditText intro_firstname;
    private EditText intro_lastname;
    private EditText intro_pin;

    private String email;
    private String user_name;
    protected String password;

    //Layout layout = findViewById(R.layout.layout_account_create2);
   // View Button = getResources().getResourceEntryName(intro_back.getId());

    @Override
    public void onClick(View view) {
        Intent intent;
        //Intent intent2;
        switch (view.getId()) {
            case R.id.intro_cancel:
                intent = new Intent(this, activity_intro.class);
                startActivity(intent);
                break;
            case R.id.intro_create:
                intent = new Intent(this, activity_home.class);
                if(intro_account_create().equals(true)) {intro_account_create(); startActivity(intent);}
                else {System.out.println("ERROR!");}
                break;
            case R.id.intro_next:
                EditText intro_username = (EditText) findViewById(R.id.edit_intro_username);
                EditText intro_password = (EditText) findViewById(R.id.edit_intro_password);
                //EditText intro_password2 = (EditText) findViewById(R.id.edit_intro_password2);
                EditText intro_email = (EditText) findViewById(R.id.edit_intro_email);
                email = intro_email.getText().toString();
                user_name = intro_username.getText().toString();
                password = intro_password.getText().toString();
                setContentView(R.layout.layout_account_create2);
                System.out.println("Email: " + email + "User: " + user_name + "password: " + password);
                break;
            case R.id.intro_back:
                setContentView(R.layout.layout_account_create);
                break;
        }
        System.out.println("Saturday" + view.getId());
    }

    public Boolean intro_account_create() {
        //get the current values in the edit texts
        EditText intro_username = (EditText) findViewById(R.id.edit_intro_username);
        EditText intro_password = (EditText) findViewById(R.id.edit_intro_password);

        //EditText intro_password2 = (EditText) findViewById(R.id.edit_intro_password2);

        EditText intro_email = (EditText) findViewById(R.id.edit_intro_email);

        View layout_account_create2 = getLayoutInflater().inflate(R.layout.layout_account_create2, null);

        EditText intro_firstname = layout_account_create2.findViewById(R.id.edit_intro_firstname);

        EditText intro_lastname = layout_account_create2.findViewById(R.id.edit_intro_lastname);

        EditText intro_pin = layout_account_create2.findViewById(R.id.edit_intro_pin);

        //add them to strings
         String pin_code = intro_pin.getText().toString();
         String first_name = intro_firstname.getText().toString();
         String last_name = intro_lastname.getText().toString();

         //validation occurs
         if(intro_account_validate(user_name, password, pin_code, first_name, last_name, email).equals(true)) {
             //use strings to create the user class
             User user = new User(user_name, password, pin_code, Boolean.TRUE, first_name, last_name.toString(), email);

             //testing purposes
             System.out.println("User name " + user_name + "Password " + password + "Email " + email);
             return true;
         } else { System.out.println("ERROR!"); return false; }
    }

    public Boolean intro_account_validate(String s1, String s2, String s3, String s4, String s5, String s6) {
        //checking to see if the field is empty

        if(s1.equals("") || s1.equals(null))
        {
            System.out.println("S1 empty");
            return false;
        }
        else if(s2.equals("") || s2.equals(null))
        {
            System.out.println("S2 empty");
            return false;
        }
        else if(s3.equals("") || s3.equals(null))
        {
            System.out.println("S3 empty");
            return false;
        }
        else if(s4.equals("") || s4.equals(null))
        {
            System.out.println("S4 empty");
            return false;
        }
        else if(s5.equals("") || s5.equals(null))
        {
            System.out.println("S5 empty");
            return false;
        }
        else if(s6.equals("") || s6.equals(null))
        {
            System.out.println("S6 empty");
            return false;
        } else {
            return true;
        }

        //checking to see if password meets requirements

        //checking to see if passwords match

        //pin code validation

        //character limits

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_create);
        //inflated view to get the ID from the second layout's button

        intro_cancel = (Button) findViewById(R.id.intro_cancel);
        intro_cancel.setOnClickListener(this);

        intro_create = (Button) findViewById(R.id.intro_create);
        intro_create.setOnClickListener(this);

        intro_next = (Button) findViewById(R.id.intro_next);
        intro_next.setOnClickListener(this);

        View layout_account_create2 = getLayoutInflater().inflate(R.layout.layout_account_create2, null);

        intro_back = (Button) layout_account_create2.findViewById(R.id.intro_back);
        intro_back.setOnClickListener(this);

    }
}
