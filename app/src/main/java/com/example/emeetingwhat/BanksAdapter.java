package com.example.emeetingwhat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emeetingwhat.Data.AccountDetailData;

import java.util.ArrayList;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.CustomViewHolder> {

    private ArrayList<AccountDetailData> mList = null;
    private Activity context = null;

    public BanksAdapter(Activity context, ArrayList<AccountDetailData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_bankName;
        protected TextView tv_accountNumber;


        public CustomViewHolder(View view) {
            super(view);
            this.tv_bankName = (TextView) view.findViewById(R.id.textView_list_bankName);
            this.tv_accountNumber = (TextView) view.findViewById(R.id.textView_list_accountNumber);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_banklist_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.tv_bankName.setText(" " + mList.get(position).getBankName());
        viewholder.tv_accountNumber.setText(" " + mList.get(position).getAccountNumber());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}