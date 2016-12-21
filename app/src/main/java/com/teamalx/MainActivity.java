package com.teamalx;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icteuro.teamalx.R;
import com.teamalx.Gcm.FirebaseIDService;
import com.teamalx.Gcm.MyFirebaseMessagingService;
import com.teamalx.Utils.AAPBDHttpClient;
import com.teamalx.Utils.AppConstant;
import com.teamalx.Utils.PersistData;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    Context con;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    TextView tvToolBarTitle,tvHome,tvContact;
    private LinearLayout bivid,odhiGrohon ,jorip,deoaniMamla,orpitoSompotti,oditApotti,prochchod,ainBidhi,vumiUnnoyonKor,bondubostoIjara,
            namjari,acr,linNamjari,linBondoIjara,linVumiUnnonKor,linOditApotti,lindeoaniMamla,
            linorpitoSomppotti,linJorp,linOdigron,linBivid;

    private LinearLayout buttonView;
    ImageView menuSetting;
    Activity activity;
    private Handler handler;
    private boolean isTimerRunning;
    Runnable Update;
    Timer swipeTimer;
    public static int[] imageRSC = {R.drawable.first, R.drawable.second};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.t1);

        con = this;

        final Typeface face_bold = Typeface.createFromAsset(getApplication().getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        final Typeface face_reg = Typeface.createFromAsset(getApplication().getAssets(), "fonts/SolaimanLipi_reg.ttf");
        con.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolBarTitle = (TextView)toolbar.findViewById(R.id.tvToolBarTitle);
        tvHome = (TextView)findViewById(R.id.tvHome);
        tvContact = (TextView)findViewById(R.id.tvContact);
        tvToolBarTitle.setTypeface(face_bold);
        tvHome.setTypeface(face_bold);
        tvContact.setTypeface(face_bold);
       // setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return false;
            }
        });

//        Intent intentMessage=new Intent(con,MyFirebaseMessagingService.class);
//        startService(intentMessage);
//
//        Intent intent=new Intent(con,FirebaseIDService.class);
//        startService(intent);


        prochchod = (LinearLayout)findViewById(R.id.prochchod);
        ainBidhi = (LinearLayout)findViewById(R.id.ainBidhi);

        buttonView = (LinearLayout) findViewById(R.id.buttonView);




        ainBidhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                buttonView.setVisibility(View.GONE);
                setContentFragment(new ContactFragement(), false,"Active Work");
            }
        });

        prochchod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                buttonView.setVisibility(View.GONE);
                setContentFragment(new NewsFragement(), false,"Active Work");
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        Intent intent = getIntent();
        AppConstant.newsId = intent.getStringExtra("idNews");
        AppConstant.pushOk = intent.getStringExtra("pushOk");

        Log.e("newsId+pushOk",AppConstant.newsId+","+AppConstant.pushOk);

        setContentFragment(new NewsFragement(), false,"Active Work");



    }




    @Override
    public void setContentFragment(Fragment fragment, boolean addToBackStack, String title) {
        if (fragment == null) {
            return;
        }

        // tvToolBarTitle.setText(title);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);

        //show only if current fragment is not same as given fragment
        /*if(currentFragment != null && fragment.getClass().equals("SeguiSuFragment")){

        }else{
            return;
        }*/
        if (currentFragment != null && fragment.getClass().isAssignableFrom(currentFragment.getClass())) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.content_frame, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

        /*if (fragment == null) {
            finish();
            //Log.e(tag, "Content fragment cannot be null");
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.content_frame, fragment, ((Object) fragment).getClass().getName());

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(((Object) fragment).getClass().getName());
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();*/
    }


    @Override
    public void addContentFragment(Fragment fragment, boolean addToBackStack, String title) {
        if (fragment == null) {
            return;
        }


        //tvToolBarTitle.setText(title);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);

        if (currentFragment != null && fragment.getClass().isAssignableFrom(currentFragment.getClass())) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_frame, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }

        /*final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);
        Log.d("", "Current fragment before pop: ");
        if (fragmentManager.getBackStackEntryCount() == 0) {
            finish();
        }
        else {
            fragmentManager.popBackStack();
        }*/
    }


    public void exitFromApp() {
        final CharSequence[] items = { "NO", "YES" };
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit from app?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        return;
                    case 1:
                        // onStopRecording();

                        finish();


                        break;
                    default:
                        return;
                }
            }
        });
        builder.show();
        builder.create();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(buttonView.getVisibility()==View.GONE){
                buttonView.setVisibility(View.VISIBLE);
            }else {
                exitFromApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String smg = intent.getStringExtra("newsId");
            AppConstant.newsId = smg;




        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        AppConstant.openPush=false;
        Log.e("GCM ID : ", PersistData.getStringData(con, AppConstant.GCMID));
        tokenSend("http://ict-euro.com/apps/teamalx/json/reg_gcm");
        con.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));

        Intent intent = getIntent();
        AppConstant.newsId = intent.getStringExtra("idNews");
        AppConstant.pushOk = intent.getStringExtra("pushOk");

        Log.e("newsId+pushOk",AppConstant.newsId+","+AppConstant.pushOk);
        //setContentFragment(new NewsFragement(), false,"Active Work");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //AppConstant.openPush=true;
        unregisterReceiver(mMessageReceiver);
    }

    private void tokenSend(final String url) {
        // TODO Auto-generated method stub
//        if (!NetInfo.isOnline(con)) {
//            AlertMessage.showMessage(con, "Alert!","");
//            return;
//        }
        Log.e("URL : ", url);
        //final BusyDialog busyNow = new BusyDialog(con, true,false);
        //busyNow.show();
        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {
            String response="";

            @Override
            public void run() {

                Map<String,String> param =new HashMap();
                param.put("regId", PersistData.getStringData(con, AppConstant.GCMID));
                param.put("secretKey","ICTAppSecretKey123");


                try {
//                    AAPBDHttpClient client= AAPBDHttpClient.post(url);
//                    client.header(PersistData.getStringData(con, AppConstant.TOKEN));


                    response= AAPBDHttpClient.post(url).form(param).body();
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("MYAPP", "exception", e);
//                    if(busyNow!=null){
//                        busyNow.dismis();
//                    }
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
//                        if (busyNow != null) {
//                            busyNow.dismis();
//                        }
                        try {
                            Log.e("Response", ">>" + new String(response));


                        } catch (final Exception e) {

                            e.printStackTrace();
                        }

                    }
                });
            }
        });


    }

}
