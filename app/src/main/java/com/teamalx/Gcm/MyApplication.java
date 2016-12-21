package com.teamalx.Gcm;

import android.app.Application;
import android.content.Intent;

/**
 * Created by wlaptop on 9/3/2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent=new Intent(getApplicationContext(),FirebaseIDService.class);
        startService(intent);
    }
}
