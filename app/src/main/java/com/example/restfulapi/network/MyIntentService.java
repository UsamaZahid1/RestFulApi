package com.example.restfulapi.network;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.restfulapi.model.CityItem;
import com.example.restfulapi.utils.HttpHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class MyIntentService extends IntentService {

    public static final String SERVICE_PAYLOAD="SERVICE_PAYLOAD";
    public static final String SERVICE_MESSAGE="SERVICE_MESSAGE";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Uri uri=intent.getData();
        String data;
        try {
            data= HttpHelper.downloadURL(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            data=e.getMessage();
        }
//        Gson gson=new Gson();
//        CityItem[] cityItems=gson.fromJson(data,CityItem[].class);

        sendMessageToUI(data);



    }

    private void sendMessageToUI(String data) {

        Intent intent=new Intent(SERVICE_MESSAGE);
        //SERVICE_PAYLOAD is a intent extra key
        intent.putExtra(SERVICE_PAYLOAD,data);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


    }

}
