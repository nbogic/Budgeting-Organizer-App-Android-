package com.example.budgetplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Budget_Adapter extends RecyclerView.Adapter<Budget_Adapter.myViewHolder> {


Context mContext;
List<Budget> mData;


public Budget_Adapter(Context mContext, List<Budget> mData) {
    this.mContext = mContext;
    this.mData = mData;

}

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View v = inflater.inflate(R.layout.layout_budget_card, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
    myViewHolder.tv_title.setText(mData.get(i).name);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
    ImageView photo_profile, background_image;
    TextView tv_title, tv_nbFollowers;


        public myViewHolder(View itemView) {
            super(itemView);
            //photo_profile = itemView.findViewById(R.id.profile_img);
            tv_title = itemView.findViewById(R.id.expense_title);

        }

    }

}
