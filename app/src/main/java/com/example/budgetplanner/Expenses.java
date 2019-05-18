package com.example.budgetplanner;

import android.accounts.Account;

import java.io.Serializable;

public class Expenses implements Serializable {

    private static final long serialVersionUID = -426918219129328097L;

    public String destination;
    public String category;
    public String date;
    public Boolean recurring;
    public Accounts account;
    public long cost;

    public Expenses(String e_destination, String e_category, String e_date, Boolean e_recurring, Accounts e_account, long e_cost) {
        destination = e_destination;
        category = e_category;
        date = e_date;
        recurring = e_recurring;
        account = e_account;
        cost = e_cost;

    }

    public Expenses(String e_destination, String e_category, String e_date, Boolean e_recurring, long e_cost) {
        destination = e_destination;
        category = e_category;
        date = e_date;
        recurring = e_recurring;
        cost = e_cost;

    }

}
