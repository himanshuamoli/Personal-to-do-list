package com.amoli.personalto_dolist;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Himanshu on 5/5/2016.
 */
public class SecondReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id=intent.getIntExtra("id",0);
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       notificationManager.cancel(id);
        String title=intent.getStringExtra("title");
        String place=intent.getStringExtra("place");
        Calendar calendar = Calendar.getInstance();
        //int snoozeTime = mMinute + SNOOZE_MIN;
        calendar.add(Calendar.MINUTE, 1); //SNOOZE_MIN = 1;
        long snoozeTime = calendar.getTimeInMillis();
        //Build Intent and Pending Intent to Set Snooze Alarm
        Intent AlarmIntent = new Intent(context, AlarmReceiver.class);
        AlarmIntent.putExtra("title",title);
        AlarmIntent.putExtra("place",place);
        AlarmManager AlmMgr = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

        AlarmIntent.putExtra("id", id);
        PendingIntent Sender = PendingIntent.getBroadcast(context, id, AlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlmMgr.set(AlarmManager.RTC_WAKEUP, snoozeTime, Sender);
        Toast.makeText(context,"Snooze set for 1 minute from now",Toast.LENGTH_LONG).show();
    }
}
