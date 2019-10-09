package com.example.dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class Alert {
    private Activity startActivity = null;
    private Context startContext = null;
    private String title = null, message = null;

    public Alert(Activity startActivity, String title, String message) {
        this.startActivity = startActivity;
        this.title = title;
        this.message = message;
    }

    public Alert(Context startContext, String title, String message) {
        this.startContext = startContext;
        this.title = title;
        this.message = message;
    }

    public void alert() {
        AlertDialog.Builder alert = null;
        if(startContext == null) {
            alert = new AlertDialog.Builder(startActivity);
        } else if(startActivity == null) {
            alert = new AlertDialog.Builder(startContext);
        }
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.show();
    }
}