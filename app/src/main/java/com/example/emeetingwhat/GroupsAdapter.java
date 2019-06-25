package com.example.emeetingwhat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emeetingwhat.Data.GroupDetailData;

import java.util.ArrayList;

class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.CustomViewHolder> {

    private ArrayList<GroupDetailData> mList = null;
    private Activity context = null;


    public GroupsAdapter(Activity context, ArrayList<GroupDetailData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_groupId;
        protected TextView tv_name;
        protected TextView tv_createDate;


        public CustomViewHolder(View view) {
            super(view);
            this.tv_groupId = (TextView) view.findViewById(R.id.textView_list_groupId);
            this.tv_name = (TextView) view.findViewById(R.id.textView_list_name);
            this.tv_createDate = (TextView) view.findViewById(R.id.textView_list_createDate);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_grouplist_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.tv_groupId.setText(" " + mList.get(position).getGroupId());
        viewholder.tv_name.setText(" " + mList.get(position).getName());
        viewholder.tv_createDate.setText(" " + mList.get(position).getCreateDate());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}