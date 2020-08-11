package com.example.restfulapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restfulapi.model.CityItem;
import com.example.restfulapi.network.MyIntentService;
import com.example.restfulapi.utils.NetworkHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    RecyclerView recyclerView;
    private boolean isNetworkOk;

    public static final String JSON_URL = "http://10.0.3.2/pakinfocopy/json/itemsfeed.php";
    public static final String WEB_URL = "https://api.openweathermap.org/data/2.5/weather?q=islamabad&appid=936430e4b50ef0e23bf211f4a2a78657";
    public static final String FAKE_DATA = "https://jsonplaceholder.typicode.com/posts";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.hasExtra(MyIntentService.SERVICE_PAYLOAD)){
                CityItem[] cityItems=(CityItem[]) intent.getParcelableArrayExtra(MyIntentService.SERVICE_PAYLOAD);
                for(CityItem item:cityItems){
                    logoutput(item.getCityname()+"\n");
                }
            }else if(intent.hasExtra(MyIntentService.SERVICE_EXCEPTION)){
                String message=intent.getStringExtra(MyIntentService.SERVICE_EXCEPTION);
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                logoutput(message);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView_main);

        isNetworkOk = NetworkHelper.isNetworkAvailable(this);
        logoutput("Network " + isNetworkOk);

    }

    public void runCode(View view) {

        if (isNetworkOk) {
            Intent intent = new Intent(MainActivity.this, MyIntentService.class);
            intent.setData(Uri.parse(JSON_URL));
            startService(intent);
        } else {
            Toast.makeText(this, "Network is unavailable...", Toast.LENGTH_LONG).show();
        }
    }

    public void logoutput(String data) {
        Log.d(TAG, "logoutput: " + data);
        textView.append(data);

    }


    protected void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(MyIntentService.SERVICE_MESSAGE));
    }

    protected void onStop() {

        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    }
}
