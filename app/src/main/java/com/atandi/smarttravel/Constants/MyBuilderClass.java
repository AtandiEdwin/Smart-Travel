package com.atandi.smarttravel.Constants;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

public class MyBuilderClass extends Application {
    public void MyBuilder(Context context, String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Smart Travel");
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
