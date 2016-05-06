package com.amoli.personalto_dolist;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Himanshu on 5/5/2016.
 */
public class ThirdReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        int id=intent.getIntExtra("id", 0);
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
}
