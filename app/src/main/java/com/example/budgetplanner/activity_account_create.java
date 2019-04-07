package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_account_create extends AppCompatActivity implements View.OnClickListener {

    private Button intro_cancel;
    private Button intro_create;
    private Button intro_next;
    private Button intro_back;

    private EditText intro_username;
    private EditText intro_password;
    private EditText intro_password2;
    private EditText intro_email;

    private EditText intro_firstname;
    private EditText intro_lastname;
    private EditText intro_pin;

   private View layout_account_create2 = getLayoutInflater().inflate(R.layout.layout_account_create2, null);


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
                intro_account_create();
                startActivity(intent);
                break;
            case R.id.intro_next:
                setContentView(R.layout.layout_account_create2);
                break;
            case R.id.intro_back:
                setContentView(R.layout.layout_account_create);
                break;
        }
        System.out.println("Saturday" + view.getId());
    }

    public void intro_account_create() {
        //get the current values in the edit texts
        EditText intro_username = (EditText) findViewById(R.id.edit_intro_username);
        EditText intro_password = (EditText) findViewById(R.id.edit_intro_password);

        EditText intro_password2 = (EditText) findViewById(R.id.edit_intro_password2);

        EditText intro_email = (EditText) findViewById(R.id.edit_intro_email);

        EditText intro_firstname = layout_account_create2.findViewById(R.id.edit_intro_firstname);

        EditText intro_lastname = layout_account_create2.findViewById(R.id.edit_intro_lastname);

        EditText intro_pin = layout_account_create2.findViewById(R.id.edit_intro_pin);

        //add them to strings

         String user_name = intro_username.getText().toString();
         String password = intro_password.getText().toString();
         String pin_code = intro_pin.getText().toString();
         String first_name = intro_firstname.getText().toString();
         String last_name = intro_lastname.getText().toString();
         String email = intro_email.getText().toString();

         //use strings to create the user class
         class_user user = new class_user(user_name, password, pin_code, Boolean.TRUE, first_name, last_name.toString(), email);

         //testing purposes
         System.out.println("User name " + user_name + "Password " + password + "Email " + email);
    }

    public void intro_account_validate(String s1, String s2, String s3, String s4, String s5, String s6) {
        //checking to see if the field is empty

        if(s1.equals(""))
        {
            System.out.println("S1 empty");
        }
        if(s2.equals(""))
        {
            System.out.println("S2 empty");
        }
        if(s3.equals(""))
        {
            System.out.println("S3 empty");
        }
        if(s4.equals(""))
        {
            System.out.println("S4 empty");
        }
        if(s5.equals(""))
        {
            System.out.println("S5 empty");
        }
        if(s6.equals(""))
        {
            System.out.println("S6 empty");
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

        intro_back = (Button) layout_account_create2.findViewById(R.id.intro_back);
        intro_back.setOnClickListener(this);

    }
}
