package com.example.budgetplanner;

import java.io.Serializable;

public class User implements Serializable {

    public String user_name;
    public String password;
    public String pin_code;

    //budget class declaration here
    //account class declaration here
    //expenses class declaration here

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
