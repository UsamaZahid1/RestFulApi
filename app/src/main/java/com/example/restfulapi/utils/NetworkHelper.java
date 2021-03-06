package com.example.restfulapi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo info=manager.getActiveNetworkInfo();

            return info!=null && info.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }

    }
}
