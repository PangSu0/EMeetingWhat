package com.example.emeetingwhat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.emeetingwhat.Data.AccountDetailData;

import java.util.List;

public class BankSpinnerAdapter extends BaseAdapter {


    Context context;
    List<AccountDetailData> mData;
    LayoutInflater inflater;


    public BankSpinnerAdapter(Context context, List<AccountDetailData> mData){
        this.context = context;
        this.mData = mData;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if(mData!=null) return mData.size();
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.view_banklist_item2, parent, false);
        }

        if(mData!=null){
            //데이터세팅
            String text1 = mData.get(position).getBankName();
            String text2 = mData.get(position).getAccountNumber();
            ((TextView)convertView.findViewById(R.id.textView_list_bankName)).setText(text1);
            ((TextView)convertView.findViewById(R.id.textView_list_accountNumber)).setText(text2);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.view_banklist_item, parent, false);
        }
        //데이터세팅
        String text1 = mData.get(position).getBankName();
        String text2 = mData.get(position).getAccountNumber();
        ((TextView)convertView.findViewById(R.id.textView_list_bankName)).setText(text1);
        ((TextView)convertView.findViewById(R.id.textView_list_accountNumber)).setText(text2);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        AccountDetailData accountData = new AccountDetailData();
        accountData.setAccountId(mData.get(position).getAccountId());
        accountData.setBankName(mData.get(position).getBankName());
        accountData.setAccountNumber(mData.get(position).getAccountNumber());
        return accountData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}