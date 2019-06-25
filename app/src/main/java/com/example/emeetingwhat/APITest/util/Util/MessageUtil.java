package com.example.emeetingwhat.APITest.util.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import and.bfop.kftc.com.useorgsampleapprenewal.App;


/**
 * Created by LeeHyeonJae on 2017-02-23.
 */
public class MessageUtil {

    /**
     * Toast 메시지 띄우기
     *
     * @param msg
     * @param duration
     */
    public static void showToast(String msg, int duration){

        Toast.makeText(App.getAppContext(), msg, duration).show();
    }

    /**
     * Toast 메시지 띄우기
     *
     * @param msg
     */
    public static void showToast(String msg){

        Toast.makeText(App.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * AlertDialog Builder 리턴
     *
     * @param title
     * @param msg
     * @param cancelable
     * @return
     */
    public static AlertDialog.Builder getDialogBuilder(String title, String msg, boolean cancelable, Context context){

        return new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setCancelable(cancelable);
    }

}
