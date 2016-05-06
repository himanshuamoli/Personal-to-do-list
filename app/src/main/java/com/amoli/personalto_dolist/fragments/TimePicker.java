package com.amoli.personalto_dolist.fragments;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import com.amoli.personalto_dolist.MainActivity;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Himanshu on 5/4/2016.
 */
public  class TimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    MainActivity context;
    public static int hour,minute;
    public static String time=null;
   public TimePicker(MainActivity context){
        this.context=context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        byte flag=0;
        hour=hourOfDay;
        this.minute=minute;
        String time=null;
        if(hourOfDay==0){
            hourOfDay=12;

        }
       else if(hourOfDay==12)
            flag=1;
       else if(hourOfDay>12) {
            hourOfDay = hourOfDay - 12;
            flag=1;
        }
        if(minute<10)
         time=hourOfDay+":0"+minute;
        else
         time=hourOfDay+":"+minute;
        if(flag==1)
            time=time+"PM";
        else
         time=time+"AM";
        context.time.setText(time);
        this.time=time;
    }
}