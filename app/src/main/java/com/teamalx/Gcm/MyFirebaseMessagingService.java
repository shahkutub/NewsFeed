package com.teamalx.Gcm;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.icteuro.teamalx.R;
import com.teamalx.MainActivity;
import com.teamalx.NewsDetailsActivity;
import com.teamalx.Utils.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wlaptop on 9/3/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";


    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    private Context con;
    Activity activity;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    String newsid = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        con = activity;
        con = this;
        Log.e(TAG, "From: " + remoteMessage.getFrom());



        if (remoteMessage == null){
            return;
        }



        Log.e(TAG, "Notification click: " + remoteMessage.getNotification().getClickAction());
//
//        if (activity.getIntent().getExtras() != null) {
//            for (String newsid : activity.getIntent().getExtras().keySet()) {
//                String value = activity.getIntent().getExtras().getString(newsid);
//                Log.e("Background push",""+value);
//            }
//        }
        Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        Log.e(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());

        Log.e(TAG, "data Body: " + remoteMessage.getData().toString());
        //Log.e(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());
       // Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());



            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());

                newsid = json.optString("newsid");
//                AppConstant.pushOk="true";
//                AppConstant.newsId = newsid;
                updateMyActivity(con,newsid);
                sendNotificationFcm(remoteMessage.getNotification().getTitle(),newsid);
                Log.e("newsid",""+newsid);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            AppConstant.openPush=true;
            updateMyActivity(con,newsid);
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());

                newsid = json.optString("newsid");
//                AppConstant.pushOk="true";
//                AppConstant.newsId = newsid;
                updateMyActivity(con,newsid);
                sendNotificationFcm(remoteMessage.getNotification().getTitle(),newsid);
                Log.e("newsid",""+newsid);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }


        }


    }





    private void sendNotificationFcm(String messageBody,String idNews) {


        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra("idNews", idNews);
        intent.putExtra("pushOk", "true");

//        AppConstant.newsId = idNews;
//        AppConstant.pushOk = "true";
        Log.e("pushOk",""+AppConstant.pushOk);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        updateMyActivity(con,newsid);
    }


    static void updateMyActivity(Context context, String newsId) {
        // PersistData.setBooleanData(context, AppManager.PENDINGSTATUS,true);
        Intent intent = new Intent("unique_name");
        //put whatever data you want to send, if a
        // ny
        intent.putExtra("newsId", newsId);
        //send broadcast

        context.sendBroadcast(intent);
    }


}
