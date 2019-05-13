package com.example.budgetplanner;

import java.util.ArrayList;
import java.util.List;

public class Budget {
    public String name;
    public String duration;
    public String date;
    public String amount;
    public String category;

    public List<Expenses> expenses = new ArrayList();
    public List<Accounts> accounts = new ArrayList();

    public Budget(String b_name, String b_duration, String b_date, String b_amount, String b_category) {
        name = b_name;
        duration = b_duration;
        date = b_date;
        amount = b_amount;
        category = b_category;
    }

    public void add_expense(Expenses new_expense) {
        expenses.add(new_expense);
    }

    public void add_account(Accounts new_account) {
        accounts.add(new_account);
    }
}
