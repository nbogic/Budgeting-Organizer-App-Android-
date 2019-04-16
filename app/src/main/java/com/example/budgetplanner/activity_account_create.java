package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_account_create extends AppCompatActivity implements View.OnClickListener {

    private Button intro_cancel;
    private Button intro_create;
    private Button intro_next;
    private Button intro_back;

    private String email;
    private String user_name;
    protected String password;

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
                System.out.println("There are missing fields!");
                break;

            case R.id.intro_next:
                EditText intro_username = (EditText) findViewById(R.id.edit_intro_username);
                EditText intro_password = (EditText) findViewById(R.id.edit_intro_password);
                //EditText intro_password2 = (EditText) findViewById(R.id.edit_intro_password2);
                EditText intro_email = (EditText) findViewById(R.id.edit_intro_email);

                email = intro_email.getText().toString();
                user_name = intro_username.getText().toString();
                password = intro_password.getText().toString();

                intent = new Intent(this, activity_account_create2.class);
                intent.putExtra("user_name", user_name);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                break;

            case R.id.intro_back:
                setContentView(R.layout.layout_account_create);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_create);

        intro_cancel = (Button) findViewById(R.id.intro_cancel);
        intro_cancel.setOnClickListener(this);

        intro_create = (Button) findViewById(R.id.intro_create);
        intro_create.setOnClickListener(this);

        intro_next = (Button) findViewById(R.id.intro_next);
        intro_next.setOnClickListener(this);

    }
}
