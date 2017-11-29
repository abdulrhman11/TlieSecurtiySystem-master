package saud.abdulrhman.tliesecurtiysystem.notification;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.toolbox.*;
import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class CheckDataFromServer extends Service {

    public static final String
            ACTION_LOCATION_BROADCAST = CheckDataFromServer.class.getName(),
            EXTRA_ID = "extra_id";

    public CheckDataFromServer() {
    }

    private Handler cHandler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.apply();
        LetsUpdate();
        return START_STICKY;
    }

    private void LetsUpdate() {
        cHandler.postDelayed(UpdateMe, 10000);
    }
    private Runnable UpdateMe = new Runnable() {
        @Override
        public void run() {
            SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
            final String check = prefs.getString("CHECK", "0");

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://tilesecurity.xyz/loginApp/CheckAlertList.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray Alert = new JSONArray(response);

                                JSONObject object = Alert.getJSONObject(0);
                                String id = object.getString("id");
                                String alert = object.getString("alert");
                                String time = object.getString("time");
                                Log.d("CHECK", "id : " + id + "\n" + "alert: " + alert + "\n" + "time: " + time);
                                if (!check.equals(id)) {
                                    SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                                    sendBroadcastMessage(id, alert, time);
                                    editor.putString("CHECK", id);
                                    editor.apply();
                                    NewMessageNotification.notify(alert, getApplicationContext());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("CHECK", error.getLocalizedMessage());
                }
            });
            queue.add(stringRequest);
            LetsUpdate();
        }
    };

    private void sendBroadcastMessage(String id, String alert, String time) {
            Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
            intent.putExtra(EXTRA_ID, id);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}