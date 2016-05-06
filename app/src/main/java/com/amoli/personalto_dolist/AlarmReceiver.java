package com.amoli.personalto_dolist;

/**
 * Created by Himanshu on 5/5/2016.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    int notifyId;
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context,"Alarm has been set",Toast.LENGTH_SHORT).show();
    /*Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    Ringtone r = RingtoneManager.getRingtone(context, notification);
    r.play();*/


        notifyId = intent.getIntExtra("id",0);

        NotificationCompat.Builder mNotify=new NotificationCompat.Builder(context);
        mNotify.setSmallIcon(R.drawable.ic_alarm);
        String title=intent.getStringExtra("title");
        String place=intent.getStringExtra("place");
        mNotify.setContentTitle(title);
        mNotify.setContentText(place);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mNotify.setSound(alarmSound);

        Intent yesReceive = new Intent(context,SecondReceiver.class);
        yesReceive.putExtra("title",title);
        yesReceive.putExtra("place",place);
        yesReceive.putExtra("id",notifyId);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(context, notifyId, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotify.addAction(R.drawable.ic_snooze,"Snooze",pendingIntentYes);

        Intent noReceive=new Intent(context,ThirdReceiver.class);
        noReceive.putExtra("id",notifyId);
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(context, notifyId, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotify.addAction(R.drawable.ic_off,"Dismiss",pendingIntentNo);

        Intent resultIntent=new Intent(context,MainActivity.class);
        resultIntent.putExtra("id",notifyId);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class); //add the to-be-displayed activity to the top of stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mNotify.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyId,mNotify.build());
    }


}