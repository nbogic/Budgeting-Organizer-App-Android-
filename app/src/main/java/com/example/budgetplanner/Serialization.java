package com.example.budgetplanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Serialization {
    private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";

    public List<User> write_user(List<User> param_user) {
        FileOutputStream file_out;
        File file = new File(login_file);
        try {
            file_out = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(file_out);
            output.writeObject(param_user);
            output.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return param_user;
    }

    public List<User> load_user(User param_user) {
        List<User> user = new ArrayList<User>();
        try {
            FileInputStream fis = new FileInputStream (new File(login_file));
            ObjectInputStream ooo = new ObjectInputStream(fis);
            user = (List<User>) ooo.readObject();
            //find matching account using username
            for(int i = 0; i < user.size(); i++) {
                if(param_user.user_name.equals(user.get(i).user_name)) {
                    //match has been found, function now assigns the specific user account with the updated user created within this activity
                    user.get(i).accounts = param_user.accounts;
                    user.get(i).budgets = param_user.budgets;
                    user.get(i).expenses = param_user.expenses;
                    write_user(user);
                    break;
                }
            }
            ooo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}
