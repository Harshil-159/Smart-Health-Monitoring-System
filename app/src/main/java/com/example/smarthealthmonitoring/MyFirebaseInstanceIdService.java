package com.example.smarthealthmonitoring;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        Log.d("TAG", "Refreshed token: " + token);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "empty");
    }
}
