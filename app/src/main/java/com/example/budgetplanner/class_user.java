package com.example.budgetplanner;

public class class_user {

    private String user_name;
    protected String password;
    private Integer pin_code;

    //budget class declaration here
    //account class declaration here
    //expenses class declaration here

    private Boolean status;
    private String first_name;
    private String last_name;
    private String email;

    public class_user(String user_name, String password, Integer pin_code, Boolean status, String first_name, String last_name, String email) {
        user_name = user_name;
        password = password;
        pin_code = pin_code;
        status = status;
        first_name = first_name;
        last_name = last_name;
        email = email;

    }

    public class_user(String first_name, String last_name, String pin_code) {
        /*offline user*/

    }

    public class_user() {
        /*testing purposes*/
    }

}
