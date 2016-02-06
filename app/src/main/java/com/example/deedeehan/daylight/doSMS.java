package com.example.deedeehan.daylight;


import android.telephony.SmsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/6/2016.
 */
public class doSMS
{
    public Values values = new Values();

    public static String remainder = new String();

    private String composeSMS(double latitude, double longitude, String type, int rating, String comment)
    {
        return Integer.toString((int)(latitude * 10000)) + "@$" + Integer.toString((int)(longitude * 10000))
                + "@$" + type + "@$" + Integer.toString(rating) + "@$" + comment;
    }
    private void sendSMS(String message)
    {
        SmsManager text = SmsManager.getDefault();
        text.sendTextMessage("+14125203163", null, message, null, null);
    }

    public void compose(double latitude, double longitude, String type, int rating, String comment)
    {
        String message = composeSMS(latitude, longitude, type, rating, comment);
        Log.d("pie", "your awesome");
        sendSMS(message);
    }


    public class SMSBroadcastReceiver extends BroadcastReceiver {

        private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
        private static final String TAG = "SMSBroadcastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Intent recieved: " + intent.getAction());

            if (intent.getAction().equals(SMS_RECEIVED)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[])bundle.get("pdus");
                    final SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    }
                    if (messages.length > -1) {
                       parseText(messages[0].getMessageBody());

                    }
                }
            }
        }
    }

    public void parseText(String text)
    {
        values.clear();

        while (true) {
            remainder = remainder.concat(text);
            if (remainder.isEmpty()) {return;}
            int first = remainder.indexOf('[');
            int startindex;
            if (remainder.charAt(first + 1) == '[') {
                startindex = first + 1;
            } else {
                startindex = first;
            }

            remainder = remainder.substring(startindex+1);

            int endpos = remainder.indexOf(']');
            if (endpos == -1) {
                return;
            }

            int segmentLength = endpos +2;

            // let's parse! 1. double latitude, 2. double longitude,
            // 3. String type, 4. double rating (1sf), 5. Str array (hope)
            double latitude, longitude, rating;
            int temp_lat, temp_long;
            String type, current;
            ArrayList<String> comments = new ArrayList();
            int end, length;

            end = (remainder.indexOf(','));
            current = remainder.substring(0, end);
            temp_lat = Integer.parseInt(current);
            latitude = (double) temp_lat / 10000;


            remainder = remainder.substring(end+2);
            end = (remainder.indexOf(','));
            current = remainder.substring(0, end);
            temp_long = Integer.parseInt(current);
            longitude = (double) temp_long / 10000;

            // string type
            remainder = remainder.substring(end+2);
            end = (remainder.indexOf(','));
            type = remainder.substring(1, end - 1); // remove quotes

            //double rating
            remainder = remainder.substring(end+2);
            end = (remainder.indexOf(','));
            current = remainder.substring(0, end);
            rating = Double.parseDouble(current);

            // array of str comments
            while (remainder.indexOf("')") != end)
            remainder = remainder.substring(end+3);
            end = (remainder.indexOf("', "));
            if (end == -1) {
                end = (remainder.indexOf("')"));
                current = remainder.substring(1, end);
                comments.add(current);

            }
            // write to filestream
            values.addValue(latitude, longitude, type,
            rating, comments);
            remainder = remainder.substring(segmentLength);

        }
    }
}
