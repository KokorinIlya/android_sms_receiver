package under_bridge.sms_server;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Илья Кокорин on 28.09.2017.
 */

public class SMSMonitor extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    String sms_from;
    String res = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }
            StringBuilder bodyText = new StringBuilder();
            for (int i = 0; i < messages.length; i++) {
                bodyText.append(messages[i].getMessageBody());
            }
            String body = bodyText.toString();
            Toast bodyToast = Toast.makeText(context, body, Toast.LENGTH_LONG);
            bodyToast.show();
            sms_from = messages[0].getDisplayOriginatingAddress();
            /*Intent myIntent = new Intent(context, SmsService.class);
            myIntent.putExtra("sms_body", body);
            myIntent.putExtra("sms_from", sms_from);
            context.startService(myIntent);*/

            //new DownloadWebpageTask().execute(body);

            res = "Message recieved";
            SmsManager.getDefault().sendTextMessage(sms_from, null, res, null, null);

            abortBroadcast();
        }
    }
}
