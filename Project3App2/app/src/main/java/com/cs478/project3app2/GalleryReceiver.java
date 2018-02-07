package com.cs478.project3app2;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GalleryReceiver extends BroadcastReceiver {

    private static final String GALLERY_ACTION = "com.cs478.project3.gallery";

    @Override
    public void onReceive(Context context, Intent intent) {

        //Check the flag for action and start gallery activity
        if(intent.getAction() == GALLERY_ACTION){
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }


}
