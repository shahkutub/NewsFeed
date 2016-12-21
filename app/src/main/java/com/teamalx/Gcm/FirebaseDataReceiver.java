package com.teamalx.Gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.teamalx.Utils.AppConstant;

/**
 * Created by User on 12/20/2016.
 */
public class FirebaseDataReceiver extends WakefulBroadcastReceiver {

    private final String TAG = "FirebaseDataReceiver";
    Activity activity;
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "I'm in!!!");

        context = activity;
//
//        Bundle dataBundle = intent.getBundleExtra("data");
//       // Log.d(TAG, dataBundle.toString());
//        Log.e(TAG, dataBundle.toString());

         intent = new Intent("title");

        String data = intent.getStringExtra("title");
        Log.e(TAG, intent.toString());


        if(activity.getIntent().getExtras()!=null){
            for(String key : activity.getIntent().getExtras().keySet()){

                    AppConstant.newsId = activity.getIntent().getExtras().getString(key);

            }
        }

//        //put whatever data you want to send, if a
//        // ny
//        intent.putExtra("data", newsId);
//        //send broadcast
//
//        context.sendBroadcast(intent);

    }
}