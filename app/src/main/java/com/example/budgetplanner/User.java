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
    //account class declaration here
    public List<Expenses> expenses = new ArrayList();

    //public Boolean status;
    public String first_name;
    public String last_name;
    public String email;

    public User(String u_user_name, String u_password, String u_pin_code, String u_first_name, String u_last_name, String u_email) {
        user_name = u_user_name;
        password = u_password;
        pin_code = u_pin_code;
        first_name = u_first_name;
        last_name = u_last_name;
        email = u_email;

    }

   // public User(String first_name, String last_name, String pin_code) {
        /*offline user*/

  //  }

    //public User() {
        /*testing purposes*/
   // }

}
