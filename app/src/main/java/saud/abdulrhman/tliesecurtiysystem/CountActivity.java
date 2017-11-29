package saud.abdulrhman.tliesecurtiysystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import saud.abdulrhman.tliesecurtiysystem.notification.CheckDataFromServer;

public class CountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        final String check = prefs.getString("CHECK", "0");
        final TextView textView = (TextView) findViewById(R.id.text_check);
        textView.setText(check);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
         @Override
                    public void onReceive(Context context, Intent intent) {
                        String id = intent.getStringExtra(CheckDataFromServer.EXTRA_ID);
                        textView.setText(id);
                    }
                }, new IntentFilter(CheckDataFromServer.ACTION_LOCATION_BROADCAST)
        );
    }
}
