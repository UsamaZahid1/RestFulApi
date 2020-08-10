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

import com.example.restfulapi.network.MyIntentService;
import com.example.restfulapi.utils.NetworkHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView textView;
    private boolean isNetworkOk;

    public static final String JSON_URL = "http://10.0.2.2/pakinfo/json/itemsfeed.php";
    public static final String WEB_URL = "https://api.openweathermap.org/data/2.5/weather?q=islamabad&appid=936430e4b50ef0e23bf211f4a2a78657";
    public static final String FAKE_DATA = "https://jsonplaceholder.typicode.com/posts";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(MyIntentService.SERVICE_PAYLOAD);
            Logoutput(data);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        isNetworkOk = NetworkHelper.isNetworkAvailable(this);
        Logoutput("Network " + isNetworkOk);


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

    public void Logoutput(String data) {
        Log.d(TAG, "logoutput: " + data);
        textView.setText(data);

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
