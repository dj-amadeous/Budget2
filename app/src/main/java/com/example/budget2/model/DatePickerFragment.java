package com.example.budget2.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

    private Date mDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }

    /*public void onDateSet(DatePicker view, int year, int month, int day){
        Integer yearI = year;
        Integer monthI = month;
        Integer dayOfMonthI = day;

        String yearS = yearI.toString();
        String monthS = monthI.toString();
        String dayOfMonthS = dayOfMonthI.toString();

        String finalDate = yearS + monthS + dayOfMonthS;
        DateFormat format = new SimpleDateFormat("yyyyMMdd");

        try {
            this.mDate = format.parse(finalDate);
            Log.d("DATE", mDate.toString());
        } catch(ParseException pe){
            Log.d("DATE", "Date format error line 42 DatePickerFragment.java");
        }
    }*/

    public Date getmDate() {
        return mDate;
    }
}
