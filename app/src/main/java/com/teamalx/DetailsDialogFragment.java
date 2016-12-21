package com.teamalx;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.icteuro.teamalx.R;
import com.teamalx.Utils.AAPBDHttpClient;
import com.teamalx.Utils.AlertMessage;
import com.teamalx.Utils.AllURL;
import com.teamalx.Utils.AppConstant;
import com.teamalx.Utils.BusyDialog;
import com.teamalx.Utils.TouchImageView;
import com.teamalx.model.NewsInfo;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailsDialogFragment extends DialogFragment {

    private Context con;
    private View view;
    private ImageView imgBac,imgDefault;
    private TouchImageView imgDetail;
    private WebView webDjela;
    TextView tvTitle,tvDetails,tvDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.help_dialog, container, true);
        con = getActivity();
        initUi();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initUi() {

        imgBac = (ImageView) view.findViewById(R.id.imgBac);
        imgDefault = (ImageView) view.findViewById(R.id.imgDefault);
        imgDetail = (TouchImageView) view.findViewById(R.id.imgDetail);
        tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        tvDetails = (TextView)view.findViewById(R.id.tvDetails);
        tvDate = (TextView)view.findViewById(R.id.tvDate);

//        tvDhakaJela.setMovementMethod(new ScrollingMovementMethod());

        imgBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        requestOrderHistory(AllURL.newsDetailsUrls(AppConstant.newsId));
    }

    private void requestOrderHistory(final String url) {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, "Alert!","Please check Internet!");
            return;
        }

//        final BusyDialog busyNow = new BusyDialog(con, true,false);
//        busyNow.show();
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
//                    if(busyNow!=null){
//                        busyNow.dismis();
//                    }
                }

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

//                        if (busyNow != null) {
//                            busyNow.dismis();
//                        }
                        try {
                            Log.e("Response", ">>" + new String(response));


                            if (!TextUtils.isEmpty(new String(response))) {

                                Gson gson = new Gson();
                                NewsInfo details = gson.fromJson(new String(response), NewsInfo.class);
                                if(details!=null){
                                    if (!TextUtils.isEmpty(details.getFeatured_image())){
                                        imgDefault.setVisibility(View.GONE);
                                        Picasso.with(getActivity()).load(details.getFeatured_image()).error(R.drawable.icturo).into(imgDetail);
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
