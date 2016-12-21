package com.teamalx;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.icteuro.teamalx.R;
import com.squareup.picasso.Picasso;
import com.teamalx.Utils.AAPBDHttpClient;
import com.teamalx.Utils.AlertMessage;
import com.teamalx.Utils.AllURL;
import com.teamalx.Utils.AppConstant;
import com.teamalx.Utils.BusyDialog;
import com.teamalx.Utils.TouchImageView;
import com.teamalx.model.NewsInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by User on 12/15/2016.
 */

public class NewsDetailsActivity extends AppCompatActivity {

    private Context con;
    private View view;
    private ImageView imgBac,imgDefault;
    private TouchImageView imgDetail;
    private WebView webDjela;
    TextView tvTitle,tvDetails,tvDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.help_dialog);

        con = this;
        initUi();
    }

    private void initUi() {

        imgBac = (ImageView) findViewById(R.id.imgBac);
        imgDefault = (ImageView) findViewById(R.id.imgDefault);
        imgDetail = (TouchImageView) findViewById(R.id.imgDetail);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDetails = (TextView)findViewById(R.id.tvDetails);
        tvDate = (TextView)findViewById(R.id.tvDate);

//        tvDhakaJela.setMovementMethod(new ScrollingMovementMethod());

        imgBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        Intent intent = getIntent();
        AppConstant.newsId = intent.getStringExtra("idNews");
        Log.e("NewsId details: ",AppConstant.newsId);
       // String name = intent.getStringExtra("name");
//        PendingIntent pi = PendingIntent.getBroadcast(
//                con, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        requestOrderHistory(AllURL.newsDetailsUrls(AppConstant.newsId));
    }


    private void requestOrderHistory(final String url) {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, "Alert!","Please check Internet!");
            return;
        }

        final BusyDialog busyNow = new BusyDialog(con, true,false);
        busyNow.show();
        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {
            String response="";
            @Override
            public void run() {

                Map<String,String> param =new HashMap();
//
//                param.put("api_token", PersistData.getStringData(con, AppConstant.TOKEN));
//                param.put("type","all");
//                param.put("Time","");

                try {

                    response= AAPBDHttpClient.get(url).body();
                } catch (Exception e) {

                    Log.e("MYAPP", "exception", e);
                    if(busyNow!=null){
                        busyNow.dismis();
                    }
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if (busyNow != null) {
                            busyNow.dismis();
                        }
                        try {
                            Log.e("Response", ">>" + new String(response));


                            if (!TextUtils.isEmpty(new String(response))) {

                                Gson gson = new Gson();
                                NewsInfo details = gson.fromJson(new String(response), NewsInfo.class);
                                if(details!=null){
                                    if (!TextUtils.isEmpty(details.getFeatured_image())){
                                        imgDefault.setVisibility(View.GONE);
                                        Picasso.with(con).load(details.getFeatured_image()).error(R.drawable.icturo).into(imgDetail);
                                    }else {
                                        imgDefault.setVisibility(View.VISIBLE);
                                    }

                                    tvTitle.setText(Html.fromHtml(details.getTitle()).toString());
                                    tvDetails.setText(Html.fromHtml(details.getContent()).toString());
                                    tvDate.setText(Html.fromHtml(details.getCreate_date()).toString());
                                }

                            }

                        } catch (final Exception e) {

                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }

}
