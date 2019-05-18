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

import java.util.List;

public class Expense_Adapter extends RecyclerView.Adapter<Expense_Adapter.myViewHolder> {


Context mContext;
List<Expenses> mData;
    User user;


public Expense_Adapter(Context mContext, List<Expenses> mData, User pass_user) {
    this.mContext = mContext;
    this.mData = mData;
    user = pass_user;

}

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View v = inflater.inflate(R.layout.layout_expense_card, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, final int i) {
    myViewHolder.expense_cost.setText("$" + mData.get(i).cost);
    myViewHolder.destination.setText(mData.get(i).destination);
        myViewHolder.category.setText(mData.get(i).category);
        myViewHolder.account.setText(mData.get(i).account.income_bankname);
        myViewHolder.next_payment.setText("Next payment: " + mData.get(i).date);

        myViewHolder.delete_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(i);
                notifyItemChanged(i);
                notifyItemRangeRemoved(i, 1);
                user.expenses.remove(i);
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
        TextView expense_cost;
        TextView destination;
        TextView category;
        TextView account;
        TextView next_payment;
        Button delete_expense;

        public myViewHolder(View itemView) {
            super(itemView);
            expense_cost = itemView.findViewById(R.id.expense);
            destination = itemView.findViewById(R.id.destination);
            category = itemView.findViewById(R.id.category);
            account = itemView.findViewById(R.id.expense_account);
            next_payment = itemView.findViewById(R.id.expense_days);
            delete_expense = itemView.findViewById(R.id.button_delete);
        }

    }

}
