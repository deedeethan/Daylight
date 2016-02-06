package com.example.deedeehan.daylight;


import android.telephony.SmsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

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
        text.sendTextMessage("+14125203163", null, message, null, null);
    }

    public void compose(int latitude, int longitude, String type, int rating, String comment)
    {
        String message = composeSMS(latitude, longitude, type, rating, comment);
        Log.d("pie", "your awesome");
        sendSMS(message);
    }


    public static class SMSBroadcastReceiver extends BroadcastReceiver {

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
                        Toast.makeText(context, "Message recieved: " + messages[0].getMessageBody(), Toast.LENGTH_LONG).show();
                        Log.d("yourawesome", messages[0].getMessageBody());
                    }
                }
            }
        }
    }
}
