package com.msalim.uganisha;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by Matthew on 2/6/2016.
 */
public class doSMS
{
    private String composeSMS(int latitude, int longitude, String type, int rating, String comment)
    {
        return Integer.toString(latitude) + "\\" + Integer.toString(longitude)
                + "\\" + type + "\\" + Integer.toString(rating) + "\\" + comment;
    }
    private void sendSMS(String message)
    {
        SmsManager text = SmsManager.getDefault();
        text.sendTextMessage("+14124448071", null, message, null, null);
    }

    public void compose(int latitude, int longitude, String type, int rating, String comment)
    {
        String message = composeSMS(latitude, longitude, type, rating, comment);
        Log.d("pie", "your awesome");
        sendSMS(message);
    }
}
