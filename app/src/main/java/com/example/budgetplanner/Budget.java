package com.example.budgetplanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Budget implements Serializable {

    private static final long serialVersionUID = -426918219129328097L;

    public String name;
    public String date;
    public Long amount;
    public String category;

    public List<Expenses> expenses = new ArrayList();
    public List<Accounts> accounts = new ArrayList();

    public Budget(String b_name, String b_date, Long b_amount, String b_category) {
        name = b_name;
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
