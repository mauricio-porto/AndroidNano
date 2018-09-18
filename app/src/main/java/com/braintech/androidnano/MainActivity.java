package com.braintech.androidnano;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private AndroidWebServer androidWebServer;
    private static boolean isStarted = false;
    private TextView textViewIpAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewIpAccess = (TextView) findViewById(R.id.textViewIpAccess);
        textViewIpAccess.setText(getIpAccess());
        startAndroidWebServer();
    }

    @Override
    protected void onDestroy() {
        stopAndroidWebServer();
        super.onDestroy();
    }

    private boolean startAndroidWebServer() {
        if (!isStarted) {
            int port = 8000;
            try {
                if (port == 0) {
                    throw new Exception();
                }
                androidWebServer = new AndroidWebServer(port);
                androidWebServer.start();
                isStarted = true;
                Log.d(TAG, "Server iniciado");
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Server causou excecao");
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean stopAndroidWebServer() {
        if (isStarted && androidWebServer != null) {
            androidWebServer.stop();
            isStarted = false;
            Log.d(TAG, "Server parado");
            return true;
        }
        return false;
    }

    private String getIpAccess() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return "http://" + formatedIpAddress + ":8000";
    }

}
