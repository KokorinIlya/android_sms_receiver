package under_bridge.sms_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Илья Кокорин on 28.09.2017.
 */

public class SmsService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String sms_body = intent.getExtras().getString("sms_body");
        String sms_from = intent.getExtras().getString("sms_from");
        URL hp;
        int c;
        String res = "";
        InputStream input;
        URLConnection hpCon;
        try {
            hp = new URL(sms_body);
        }
        catch (Exception e) {
            return START_NOT_STICKY;
        }

        try {
            hpCon = hp.openConnection();
            hpCon.connect();
        }
        catch (Exception e) {
            return START_NOT_STICKY;
        }

        try {
            input = hpCon.getInputStream();
        }
        catch (Exception e) {
            return START_NOT_STICKY;
        }



        try {
            while (((c = input.read()) != -1)){
                res += (char) c;
            }
        }
        catch (Exception e) {
            return START_NOT_STICKY;
        }

        try {
            input.close();
        }
        catch (Exception e) {
            return START_NOT_STICKY;
        }

        SmsManager.getDefault().sendTextMessage(sms_from, null, res, null, null);

        return START_STICKY;
    }
}
