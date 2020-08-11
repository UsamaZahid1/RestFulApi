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
    public static final String SERVICE_EXCEPTION="SERVICE_EXCEPTION";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Uri uri=intent.getData();
        String data;
        try {
            data= HttpHelper.downloadURL(uri.toString(),"admin","lolx");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessageToUI(e);
            return;
        }
        Gson gson=new Gson();
        CityItem[] cityItems=gson.fromJson(data,CityItem[].class);

        sendMessageToUI(cityItems);

    }

    private void sendMessageToUI(Exception e) {
        Intent intent=new Intent(SERVICE_MESSAGE);
        //SERVICE_PAYLOAD is a intent extra key
        intent.putExtra(SERVICE_EXCEPTION,e.getMessage());

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMessageToUI(CityItem[] data) {

        Intent intent=new Intent(SERVICE_MESSAGE);
        //SERVICE_PAYLOAD is a intent extra key
        intent.putExtra(SERVICE_PAYLOAD,data);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


    }

}
