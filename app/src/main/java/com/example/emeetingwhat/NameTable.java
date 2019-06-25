package com.example.emeetingwhat;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NameTable implements Serializable {
    String[] mNameList;
    boolean[] isUsedList;
    int[] indexList;
    ArrayList<String> database;
    NameTable(ArrayList<String> database){
        this.database = database;
        isUsedList = new boolean[this.database.size()];
    }
    public void refreshList(){
        if(mNameList != null)
            mNameList = null;
        mNameList = new String[notUsedCount()+1];
        indexList = new int[notUsedCount()+1];
        int index = 0;
        mNameList[index++] = "";
        indexList[0]=-1;
        for(int i = 0 ; i < database.size(); i ++) {
            if(isUsedList[i] == false) {
                mNameList[index] = database.get(i);
                indexList[index] = i;
                index++;
            }
        }

    }
    public int notUsedCount(){
        int count = 0;
        for(int i = 0 ; i < database.size(); i ++){
            if(isUsedList[i] == false)
                count++;
        }
        return  count;
    }
    public void useName(int index)
    {
        isUsedList[indexList[index]] = true;
    }
    public void unUseName(int index)
    {
        isUsedList[indexList[index]] = false;
    }
    public int getIndex(int index){
        return indexList[index];
    }
}