package com.grace.budgtey;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public String REQUIRED_MSG = "Required Field";
    // check the input field has any text or not
    // return true if it contains text otherwise false
    public boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        editText.requestFocus();

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    /**
     * Show Date Picker Dialog
     *
     * @param context
     * @param onDateSetListener
     */
    public void showDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                onDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Show Time Picker Dialog
     *
     * @param context
     * @param onTimeSetListener
     */
    public void showTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                onTimeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Used to convert String type date into milliseconds
     *
     * @param dateTime
     * @param format
     * @return
     */
    public long convertStringDateTimeIntoMiliSec(String dateTime, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(dateTime);
            Log.d("time", "convertStringDateTimeIntoMiliSec: " + date.getTime());
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Used to get specific time or date according to given format
     *
     * @param format
     * @param year
     * @param month
     * @param day
     * @return
     */
    public String getSpecificDate(String format, int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day);
        return sdf.format(newDate.getTime());
    }

    /**
     * Used to get current time or date according to given format
     *
     * @return
     */
    public String getCurrentTimeOrDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }

    public String changeTimeOrDateFormat(String oldFormat, String newFormat, String time) {
        SimpleDateFormat sdfOldFormat = new SimpleDateFormat(oldFormat);
        SimpleDateFormat sdfNewFormat = new SimpleDateFormat(newFormat);
        try {
            return sdfNewFormat.format(sdfOldFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

}
