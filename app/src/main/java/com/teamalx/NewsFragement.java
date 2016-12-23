package com.teamalx;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icteuro.teamalx.R;
import com.teamalx.Utils.AAPBDHttpClient;
import com.teamalx.Utils.AlertMessage;
import com.teamalx.Utils.AllURL;
import com.teamalx.Utils.AppConstant;
import com.teamalx.Utils.BusyDialog;
import com.teamalx.model.NewsInfo;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;


/**
 * Created by User on 7/20/2016.
 */
public class NewsFragement extends BaseFragment {
    Context con;
    private List<NewsInfo> posts;
    private ListView listUjelaOfficer;
    SwipeRefreshLayout pullToRefresh;
    private int limit = 8;
    int position;
    HistoryAdapter historyAdapter;
    String data,id;
    private ProgressBar prog;
    public static final String JSON_URL = "http://ict-euro.com/apps/teamalx/json/get_news_json?limit=8";
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.upojela_officer, container, false);
    }


    @SuppressLint("NewApi")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();

        prog = (ProgressBar)getView().findViewById(R.id.prog);
        listUjelaOfficer = (ListView)getView().findViewById(R.id.listUjelaOfficer);
        pullToRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub

                refreshContent();

            }
        });



        if(!TextUtils.isEmpty(AppConstant.pushOk)){
                DetailsDialogFragment motamotDialogFragment = new DetailsDialogFragment();
                motamotDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
                motamotDialogFragment.show(getActivity().getFragmentManager(), "");

        }else {
            requestOrderHistory(AllURL.newsUrls(String.valueOf(limit)));

//            Animation anim = AnimationUtils.loadAnimation(
//                    con, android.R.anim.slide_out_right
//            );
//            anim.setDuration(500);
//            listUjelaOfficer.getChildAt(position).startAnimation(anim );
//
//            new Handler().postDelayed(new Runnable() {
//
//                public void run() {
//
//                    historyAdapter.notifyDataSetChanged();
//
//                }
//
//            }, anim.getDuration());
        }




    }

    private void refreshContent(){

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                pullToRefresh.setRefreshing(false);
                limit+=8;
                requestOrderHistory(AllURL.newsUrls(String.valueOf(limit)));
            }
        }, 5000);

    }

    private void requestOrderHistory(final String url) {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, "Alert","Please check Internet");
            return;
        }

        Log.e("url", ">>" + new String(url));
//        final BusyDialog busyNow = new BusyDialog(con, true,true);
//        busyNow.show();
        prog.setVisibility(View.VISIBLE);
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
                    prog.setVisibility(View.GONE);
                }

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

//                        if (busyNow != null) {
//                            busyNow.dismis();
//                        }
                        prog.setVisibility(View.GONE);
                        try {
                            Log.e("Response", ">>" + new String(response));


                            if (!TextUtils.isEmpty(new String(response))) {

                                Gson gson = new Gson();
                                String jsonOutput = response;
                                Type listType = new TypeToken<List<NewsInfo>>(){}.getType();
                                posts = (List<NewsInfo>) gson.fromJson(jsonOutput, listType);
//
//                                Gson g = new Gson();
//                                mOrderResponse = g.fromJson(new String(response), UpojelaOfficerResponse.class);
                                if (posts.size()>0) {

                                    historyAdapter = new HistoryAdapter(con);
                                    listUjelaOfficer.setAdapter(historyAdapter);
                                    historyAdapter.notifyDataSetChanged();                         }
                           }

                        } catch (final Exception e) {

                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }


    private class HistoryAdapter extends ArrayAdapter<NewsInfo> {
        Context context;

        public HistoryAdapter(Context context) {
            super(context, R.layout.raw_upjela_nirbahi, posts);

            this.context = context;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            position = position;
            View v = convertView;

            if (v == null) {
                final LayoutInflater vi = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.raw_upjela_nirbahi, null);




            }

            if( position<posts.size()){

                final NewsInfo query = posts.get(position);


                final RelativeLayout layNews = (RelativeLayout)v.findViewById(R.id.layNews);
                final TextView tvNewsTitle = (TextView)v.findViewById(R.id.tvNewsTitle);
                final TextView tvNewsContent = (TextView)v.findViewById(R.id.tvNewsContent);
                final TextView tvDate = (TextView)v.findViewById(R.id.tvDate);
                final TextView tvAuthor = (TextView)v.findViewById(R.id.tvAuthor);
                final Typeface face_bold = Typeface.createFromAsset(getActivity().getApplication().getAssets(), "fonts/SolaimanLipi_Bold.ttf");
                tvNewsTitle.setTypeface(face_bold);
                tvNewsContent.setTypeface(face_bold);
                tvDate.setTypeface(face_bold);
                tvAuthor.setTypeface(face_bold);

                tvNewsTitle.setText(Html.fromHtml(query.getTitle()).toString());
                tvNewsContent.setText(Html.fromHtml(query.getContent()).toString());
                tvDate.setText(Html.fromHtml(query.getCreate_date()).toString());
                layNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppConstant.newsId= query.getId();

                        DetailsDialogFragment motamotDialogFragment = new DetailsDialogFragment();
                        motamotDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
                        motamotDialogFragment.show(getActivity().getFragmentManager(), "");

                    }
                });

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim);
                v.startAnimation(animation);



            }


            return v;
        }
    }


}
