package com.example.emeetingwhat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter
{
    LayoutInflater inflater = null;
    private ArrayList<GroupListData> m_gData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<GroupListData> _gData)
    {
        m_gData = _gData;
        nListCnt = m_gData.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.group_listview_item, parent, false);
        }

        TextView gTextTitle = (TextView) convertView.findViewById(R.id.group_title_textView);
        TextView gTextAccountOwner = (TextView) convertView.findViewById(R.id.account_owner_textView);
        ImageView gImageGroupThumbnail = (ImageView) convertView.findViewById(R.id.group_thumbnail_imageView);
        gTextTitle.setText(m_gData.get(position).getTitle());
        gTextAccountOwner.setText(m_gData.get(position).getAccountOwner());

        // TODO: 이미지 뷰 수정
        return convertView;
    }
}