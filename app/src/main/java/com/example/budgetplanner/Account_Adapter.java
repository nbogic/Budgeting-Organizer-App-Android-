package com.example.budgetplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Account_Adapter extends RecyclerView.Adapter<Account_Adapter.myViewHolder> {

Context mContext;
List<Accounts> mData;
User user;

private static final String login_file = "/data/data/com.example.budgetplanner/files/list_users.txt";

public Account_Adapter(Context mContext, List<Accounts> mData, User pass_user) {
    this.mContext = mContext;
    this.mData = mData;
    this.user = pass_user;
}

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View v = inflater.inflate(R.layout.layout_account_card, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        myViewHolder.account_balance.setText("$" + mData.get(i).account_balance);
        myViewHolder.bank_name.setText(mData.get(i).income_bankname + " " + "-");
        myViewHolder.account_type.setText(mData.get(i).account_type);
        String income_amount = "";
        if(mData.get(i).account_amount == null) {
            income_amount = "0";
        } else {income_amount = mData.get(i).account_amount.toString();}
        myViewHolder.expenses_total.setText(user.expenses.size() + " expenses");
        myViewHolder.income_date.setText("$" + income_amount + " " + mData.get(i).income_ocurrence);

        myViewHolder.account_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewHolder.edit_deposit.setVisibility(VISIBLE);
                myViewHolder.confirm_deposit.setVisibility(VISIBLE);
            }
        });

        myViewHolder.confirm_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("this account is: " + user.accounts.get(i).account_balance);
                user.accounts.get(i).deposit(Long.valueOf(myViewHolder.edit_deposit.getText().toString()));
                adapter_account_load();
                myViewHolder.edit_deposit.setVisibility(INVISIBLE);
                myViewHolder.confirm_deposit.setVisibility(INVISIBLE);
                myViewHolder.account_balance.setText("$" + user.accounts.get(i).account_balance);
            }
        });

        myViewHolder.delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(i);
                notifyItemChanged(i);
                notifyItemRangeRemoved(i, 1);
                user.accounts.remove(i);
                adapter_account_load();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
    TextView bank_name;
    TextView account_type;
    TextView income_date;
    Button account_deposit;
    TextView expenses_total;
    EditText edit_deposit;
    Button confirm_deposit;
    TextView account_balance;
    Button delete_account;

        public myViewHolder(View itemView) {
            super(itemView);
            account_balance = itemView.findViewById(R.id.account_balance);
            bank_name = itemView.findViewById(R.id.bank_name);
            account_type = itemView.findViewById(R.id.bank_name2);
            income_date = itemView.findViewById(R.id.income_receiving);
            account_deposit = itemView.findViewById(R.id.button_deposit);
            edit_deposit  = itemView.findViewById(R.id.edit_deposit);
            confirm_deposit = itemView.findViewById(R.id.button_confirm);
            expenses_total = itemView.findViewById(R.id.income_expenses);
            delete_account = itemView.findViewById(R.id.button_delete);
        }
    }


    public List<User> adapter_account_write(List<User> new_user) {
        //testing for feedback
        System.out.println("Created!");
        FileOutputStream file_out;
        File file = new File(login_file);
        try {
            file_out = new FileOutputStream(file);
            ObjectOutputStream user_out = new ObjectOutputStream(file_out);
            user_out.writeObject(new_user);
            user_out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new_user;
    }

    //modified reading function, made to overwrite the current file with the inclusion of a new expense object
    public List<User> adapter_account_load() {
        List<User> exp_user_gen = new ArrayList<User>();
        try {
            FileInputStream fis = new FileInputStream (new File(login_file));
            ObjectInputStream ooo = new ObjectInputStream(fis);
            exp_user_gen = (List<User>) ooo.readObject();

            //find matching account using username
            for(int i = 0; i < exp_user_gen.size(); i++) {
                if(user.user_name.equals(exp_user_gen.get(i).user_name)) {
                    //testing
                    //match has been found, function now assigns the specific user account with the updated user created within this activity
                    exp_user_gen.get(i).accounts = user.accounts;

                    //call for writing, updated list as a parameter
                    adapter_account_write(exp_user_gen);
                    break;
                }
            }
            ooo.close();
            //exceptions
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return exp_user_gen;
    }

}
