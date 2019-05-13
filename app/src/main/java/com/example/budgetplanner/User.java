package com.example.budgetplanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = -426918219129328097L;

    public String user_name;
    public String password;
    public String pin_code;

    //budget class declaration here
    public List<Expenses> expenses = new ArrayList();
    public List<Accounts> accounts = new ArrayList();
    public List<Budget> budgets = new ArrayList();

    //public Boolean status;
    public String first_name;
    public String last_name;
    public String email;

    public User(String u_user_name, String u_password, String u_pin_code, String u_first_name, String u_last_name, String u_email, List<Expenses> u_expenses, List<Accounts> u_accounts) {
        user_name = u_user_name;
        password = u_password;
        pin_code = u_pin_code;
        first_name = u_first_name;
        last_name = u_last_name;
        email = u_email;
        expenses = u_expenses;
        accounts = u_accounts;

    }

   public User(String u_user_name, String u_password, String u_pin_code, String u_first_name, String u_last_name, List<Accounts> u_accounts, String u_email) {
       user_name = u_user_name;
       password = u_password;
       pin_code = u_pin_code;
       first_name = u_first_name;
       last_name = u_last_name;
       email = u_email;
       accounts = u_accounts;

   }

    public User(String u_user_name, String u_password, String u_pin_code, String u_first_name, String u_last_name, String u_email) {
        user_name = u_user_name;
        password = u_password;
        pin_code = u_pin_code;
        first_name = u_first_name;
        last_name = u_last_name;
        email = u_email;
    }


    public User() {

    }
}