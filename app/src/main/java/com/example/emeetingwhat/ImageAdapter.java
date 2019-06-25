package com.example.emeetingwhat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends ArrayAdapter<String>
{
    ImageAdapter(Context context,String[] items)
    {
        super(context,R.layout.image_layout, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View view = listInflater.inflate(R.layout.image_layout, parent,false);
        String item = getItem(position);
        TextView textView = view.findViewById(R.id.textView);
        ImageView imageView = view.findViewById(R.id.imageView);
        textView.setText(item);
        imageView.setImageResource(R.mipmap.ic_launcher);
        return view;
    }
}
