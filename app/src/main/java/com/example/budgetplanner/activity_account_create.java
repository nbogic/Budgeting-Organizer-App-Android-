package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_account_create extends AppCompatActivity implements View.OnClickListener {

    private Button intro_cancel;
    private Button intro_create;

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
        }
        System.out.println("Saturday" + view.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account_create);

        intro_cancel = (Button) findViewById(R.id.intro_cancel);
        intro_cancel.setOnClickListener(this);

        intro_create = (Button) findViewById(R.id.intro_create);
        intro_create.setOnClickListener(this);


    }
}
