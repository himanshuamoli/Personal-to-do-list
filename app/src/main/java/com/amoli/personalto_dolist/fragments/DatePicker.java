package com.amoli.personalto_dolist.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;

import com.amoli.personalto_dolist.MainActivity;

import java.util.Calendar;

/**
 * Created by Himanshu on 5/4/2016.
 */
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    MainActivity context;
    public static int year,month,day;
    public static String date=null;
    public DatePicker(MainActivity context){
        this.context=context;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year=year;
        month=monthOfYear;
        String monthName=null;
        switch(month){
            case 1:monthName="January";
                   break;
            case 2:monthName="February";
                   break;
            case 3:monthName="March";
                break;
            case 4:monthName="April";
                break;
            case 5:monthName="May";
                break;
            case 6:monthName="June";
                break;
            case 7:monthName="July";
                break;
            case 8:monthName="August";
                break;
            case 9:monthName="September";
                break;
            case 10:monthName="October";
                break;
            case 11:monthName="November";
                break;
            case 12:monthName="December";
                break;
        }
        day=dayOfMonth;
        String date=dayOfMonth+" "+monthName+", "+year;
        context.date.setText(date);
        this.date=date;

    }
}
