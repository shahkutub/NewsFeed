package com.teamalx;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.icteuro.teamalx.R;
import com.teamalx.Utils.AAPBDHttpClient;
import com.teamalx.Utils.AlertMessage;
import com.teamalx.Utils.BusyDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by User on 7/20/2016.
 */
public class ContactFragement extends BaseFragment {
    Context con;

    private TextView tvSubmit,tvTitleContact;
    private EditText etName,etEmail,etMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        final Typeface face_bold = Typeface.createFromAsset(getActivity().getApplication().getAssets(), "fonts/SolaimanLipi_Bold.ttf");

        etName = (EditText)getView().findViewById(R.id.etName);
        etEmail = (EditText)getView().findViewById(R.id.etEmail);
        etMessage = (EditText)getView().findViewById(R.id.etMessage);
        tvSubmit = (TextView) getView().findViewById(R.id.tvSubmit);
        tvTitleContact = (TextView) getView().findViewById(R.id.tvTitleContact);

        etName.setTypeface(face_bold);
        etEmail.setTypeface(face_bold);
        etMessage.setTypeface(face_bold);
        tvSubmit.setTypeface(face_bold);
        tvTitleContact.setTypeface(face_bold);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etName.getText().toString())){
                    AlertMessage.showMessage(getActivity(),"Alert","Enter name!");
                }else if(TextUtils.isEmpty(etEmail.getText().toString())){
                    AlertMessage.showMessage(getActivity(),"Alert","Enter email!");
                }else if(TextUtils.isEmpty(etMessage.getText().toString())){
                    AlertMessage.showMessage(getActivity(),"Alert","Write a message!");
                }else {
                    contactSend("http://ict-euro.com/apps/teamalx/home/contact_submit");
                }
            }
        });

    }


    private void contactSend(final String url) {
        // TODO Auto-generated method stub
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name),"");
            return;
        }
        Log.e("URL : ", url);
        final BusyDialog busyNow = new BusyDialog(con, true,false);
        busyNow.show();
        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {
            String response="";

            @Override
            public void run() {

                Map<String,String> param =new HashMap();
                param.put("contactName", etName.getText().toString());
                param.put("contactEmail", etEmail.getText().toString());
                param.put("contactMessage", etMessage.getText().toString());
                param.put("secretKey","ICTAppSecretKey123");


                try {

                    response= AAPBDHttpClient.post(url).form(param).body();
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("MYAPP", "exception", e);
                    if(busyNow!=null){
                        busyNow.dismis();
                    }
                }

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (busyNow != null) {
                            busyNow.dismis();
                        }
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
