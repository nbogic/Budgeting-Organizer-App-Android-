package com.example.budgetplanner;

public class User {

    public  String user_name;
    public String password;
    public  String pin_code;

    //budget class declaration here
    //account class declaration here
    //expenses class declaration here

    public Boolean status;
    public  String first_name;
    public String last_name;
    public  String email;

    public User(String user_name, String password, String pin_code, Boolean status, String first_name, String last_name, String email) {
        user_name = user_name;
        password = password;
        pin_code = pin_code;
        status = status;
        first_name = first_name;
        last_name = last_name;
        email = email;

    }

    public User(String first_name, String last_name, String pin_code) {
        /*offline user*/

    }

    public User() {
        /*testing purposes*/
    }

}
