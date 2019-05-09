package com.example.budgetplanner;

import java.io.Serializable;

public class Accounts implements Serializable {

    private static final long serialVersionUID = -426918219129328097L;

    public String account_name;
    public String account_type;
    public String income_bankname;
    public String income_ocurrence;
    public String account_balance;

    public Accounts(String a_account_name, String a_account_type, String a_income_ocurrence, String a_account_balance, String a_bankname) {
        account_name = a_account_name;
        account_type = a_account_type;
        income_bankname = a_bankname;
        income_ocurrence = a_income_ocurrence;
        account_balance = a_account_balance;

    }


}
