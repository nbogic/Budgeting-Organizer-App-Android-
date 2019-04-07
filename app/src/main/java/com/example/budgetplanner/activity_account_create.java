package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_account_create extends AppCompatActivity implements View.OnClickListener {

    private Button intro_cancel;
    private Button intro_create;
    private Button intro_next;
    private Button intro_back;

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

    public void account_create() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_create);
        //inflated view to get the ID from the second layout's button
        View layout_account_create2 = getLayoutInflater().inflate(R.layout.layout_account_create2, null);

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
