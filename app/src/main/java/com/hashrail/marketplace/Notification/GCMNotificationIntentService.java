package com.hashrail.marketplace.Notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hashrail.marketplace.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dreamworld Solutions on 22-7-2016.
 */
public class GCMNotificationIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    public static String TAG = "GCMNotificationIntentService";
    NotificationCompat.Builder builder;
    Bitmap Images;
    String message, surl;
    private NotificationManager mNotificationManager;

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(7000);
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);


        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                sendNotification((String) extras.get("msg"));
            }
        }
        GcmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {

        try {
            JSONObject jsonObject = new JSONObject(msg);
            Images = getBitmapFromURL(jsonObject.getString("image"));
            message = jsonObject.getString("msg");
            surl = jsonObject.getString("send_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent in = new Intent(this, NotiViewActivity.class);
        in.putExtra("Notif", message.toString());
        in.putExtra("SURL", surl.toString());

        //in.putExtra("url", surl);
        PendingIntent contentIntent = PendingIntent.getActivity(this, PendingIntent.FLAG_UPDATE_CURRENT,
                in, 0);

        int numMessages = 0;
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(
                this).setSmallIcon(R.mipmap.ic_launcher)

                .setContentTitle(getString(R.string.app_name))
                /*.setStyle(new NotificationCompat.BigTextStyle().bigText("dfds"))*/
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(Images)
                        .setBigContentTitle(getString(R.string.app_name))
                        .setSummaryText(message))
                .setAutoCancel(true)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_ALL)
                .setNumber(++numMessages);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}