package com.example.budgetplanner;

import java.io.Serializable;

public class Accounts implements Serializable {

    private static final long serialVersionUID = -426918219129328097L;

    public String account_type;
    public String income_bankname;
    public String income_ocurrence;
    public String account_balance;
    public Long account_amount;

    public Accounts(String a_bankname, String a_account_type, String a_income_ocurrence, String a_account_balance, Long a_income_amount) {
        account_type = a_account_type;
        income_bankname = a_bankname;
        income_ocurrence = a_income_ocurrence;
        account_balance = a_account_balance;
        account_amount = a_income_amount;
    }


    public void deposit(Long amount) {
        Long total = Long.valueOf(account_balance) + amount;
        account_balance =  total.toString();
    }
}
