package com.teamalx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.icteuro.teamalx.R;
import com.teamalx.Gcm.FirebaseIDService;
import com.teamalx.Utils.AAPBDHttpClient;
import com.teamalx.Utils.AppConstant;


import java.util.concurrent.Executors;

public class Splash extends AppCompatActivity {

    Context con;
    Handler handler = new Handler();
    private final int SPLASH_DISPLAY_LENGTH = 300;
    //private ProgressBar progressShow;

    String news_titl = "", news_details = "", news_id = "", type = "";


    //String SENDER_ID = "257395124016";
    //GoogleCloudMessaging gcm;
    String regId = "", msg = "", response_menu = "";
    static final String TAG = "GCM_CHECK";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    private long millis;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.splash);
        con = this;

//        if(TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.GCMID)))
//        {
//            PersistData.setStringData(con,AppConstant.GCMID,"1234567890");
//        }
//
//        if(!(PersistData.getBooleanData(con,AppConstant.defaultValueSet)))
//        {
//            PersistData.setBooleanData(con,AppConstant.defaultValueSet,true);
//            PersistData.setBooleanData(con,AppConstant.notificationSettingsOn,true);
//            PersistData.setBooleanData(con,AppConstant.notificationSoundOn,true);
//            PersistData.setBooleanData(con,AppConstant.notificationVibrateOn,true);
//        }

        Intent intent = new Intent(con, FirebaseIDService.class);
        startService(intent);


        millis = System.currentTimeMillis();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(con, MainActivity.class);
                startActivity(mainIntent);


                if(mainIntent.getExtras()!=null){
                    for(String key : mainIntent.getExtras().keySet()){

                        AppConstant.newsId = mainIntent.getExtras().getString(key);

                    }
                }

                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


//        if (!NetInfo.isOnline(con)) {
////            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
////            return;
//
//            handler.postDelayed(new Runnable(){
//                @Override
//                public void run() {
//                    Intent mainIntent = new Intent(con,MainActivity.class);
//                    startActivity(mainIntent);
//                    finish();
//                }
//            }, SPLASH_DISPLAY_LENGTH);
//
//
//
//        }else{
//
//            if((PersistData.getLongData(con, AppConstant.SystemTime)+(24*60*60*1000))>millis)
//            {
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        Intent mainIntent = new Intent(con,MainActivity.class);
//                        startActivity(mainIntent);
//                        finish();
//                    }
//                }, SPLASH_DISPLAY_LENGTH);
//
//            }else
//            {
//
//                PersistData.setLongData(con,AppConstant.SystemTime,millis);
//
//               // getMenuInfo(AllURL.getMenuList());
//            }
//
//        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private boolean checkPlayServices() {
        final int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, Splash.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(Splash.TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void getMenuInfo(final String url) {


        if (!NetInfo.isOnline(con)) {
            // AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            //return;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent mainIntent = new Intent(con, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                }
            }, 100);


        }


        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {


            @Override
            public void run() {

                try {
                    response_menu = AAPBDHttpClient.get(url).body();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {


                        try {
                            Log.e("menulist", ">>" + new String(response_menu));
                            if (!TextUtils.isEmpty(new String(response_menu))) {

//                                Gson g = new Gson();
//                                AllCategory allCategory=g.fromJson(new String(response_menu),AllCategory.class);
//                                PersistData.setLongData(con, AppConstant.SystemTime, millis);

//                                if(allCategory.getStatus()==1){
//                                    PersistData.setStringData(getApplicationContext(),AppConstant.CATEGORY_RESPONSE,response_menu);
//                                    Intent mainIntent = new Intent(con,MainActivity.class);
//                                    startActivity(mainIntent);
//                                    finish();
//
//                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();

                        }
                    }
                });
            }
        });


    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String smg = intent.getStringExtra("newsId");

            AppConstant.newsId = smg;


        }
    };

//    private void showDiaLogView(final String msg) {
//        // AlertDialog.Builder adb = new AlertDialog.Builder(this);
//
//
//        try {
//            JSONObject newsObj = new JSONObject(msg);
//            news_titl = newsObj.optString("news_title");
//            news_details = newsObj.optString("news_details");
//            news_id = newsObj.optString("news_id");
//            type = newsObj.optString("type");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        final Dialog d = new Dialog(con, R.style.full_screen_dialog);
//
//        d.setContentView(R.layout.popup_news);
//
//        RelativeLayout mainLayout = (RelativeLayout) d.findViewById(R.id.mainLayout);
//        mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (type.equalsIgnoreCase("news")) {
//                    Intent intent = new Intent(con, DetailsActivity.class);
//                    intent.putExtra("content_id", news_id);
//                    intent.putExtra("is_favrt", "0");
//                    startActivity(intent);
//                    d.dismiss();
//                } else {
//                    d.dismiss();
//                }
//
//
//            }
//
//        });
//
//        TextView title = (TextView) d.findViewById(R.id.breakingNewsTitle);
//        title.setText(news_titl);
//
//        TextView description = (TextView) d.findViewById(R.id.breakingDetails);
//        description.setText(news_details);
//
//        final Typeface face_reg = Typeface.createFromAsset(getAssets(), "fonts/SolaimanLipi_reg.ttf");
//        description.setTypeface(face_reg);
//        title.setTypeface(face_reg);
//
//
//        // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)
//
//
//        WindowManager.LayoutParams wmlp = d.getWindow().getAttributes();
//        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
//        wmlp.x = 0;
//        wmlp.y = 0;
//
//        d.show();
//
//    }

    @Override
    protected void onResume() {
        super.onResume();

        //AppConstant.openPush=false;

        con.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //AppConstant.openPush=false;
        unregisterReceiver(mMessageReceiver);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Splash Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}