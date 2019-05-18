package com.example.budgetplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.view.View.INVISIBLE;

public class Budget_Adapter extends RecyclerView.Adapter<Budget_Adapter.myViewHolder> {


Context mContext;
List<Budget> mData;
User user;


public Budget_Adapter(Context mContext, List<Budget> mData, User pass_user) {
    this.mContext = mContext;
    this.mData = mData;
    user = pass_user;
}

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View v = inflater.inflate(R.layout.layout_budget_card, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, final int i) {
        String days_remaining = "";
        Calendar calendar = Calendar.getInstance();
        try {
            int current_year = calendar.get(Calendar.YEAR);
            int current_month = calendar.get(Calendar.MONTH);
            int current_day = calendar.get(Calendar.DAY_OF_MONTH);
            String current_date = current_day + "/" + current_month + "/" + current_year;
            String budget_date = mData.get(i).date;
            SimpleDateFormat user_date = new SimpleDateFormat("dd/MM/yyyy");
            Date a_date = user_date.parse(budget_date);
            Date b_date = user_date.parse(current_date);
            long diff = a_date.getTime() - b_date.getTime();
           days_remaining = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " days remaining "  );
     } catch(ParseException ex){

  }
         myViewHolder.budget_amount.setText("$" + mData.get(i).amount.toString());
            myViewHolder.budget_remaining_days.setText(days_remaining);
            Integer exp_size = user.budgets.get(i).expenses.size();
            String string_size = exp_size.toString();
            myViewHolder.budget_expense_count.setText(string_size + " expenses");
            Integer acc_size = user.budgets.get(i).accounts.size();
            String string_size2 = acc_size.toString();
            myViewHolder.budget_account_count.setText(string_size2 + " accounts");

        myViewHolder.budget_category.setText(mData.get(i).category);


        myViewHolder.delete_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(i);
                notifyItemChanged(i);
                notifyItemRangeRemoved(i, 1);
                user.budgets.remove(i);
                Serialization save = new Serialization();
                save.load_user(user);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
    TextView budget_category;
        TextView budget_amount;
        TextView budget_remaining_days;
        TextView budget_expense_count;
        TextView budget_account_count;
        Button delete_budget;

        public myViewHolder(View itemView) {
            super(itemView);
            budget_amount = itemView.findViewById(R.id.budget_amount);
            budget_remaining_days = itemView.findViewById(R.id.days);
            budget_expense_count = itemView.findViewById(R.id.expenses_number);
            budget_account_count = itemView.findViewById(R.id.accounts_number);
            budget_category = itemView.findViewById(R.id.category);
            delete_budget = itemView.findViewById(R.id.button_delete2);
        }

    }



}
