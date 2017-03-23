package com.salah.ufindem.fcm;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.salah.ufindem.AppConfig;

public class UfindemFirebaseInstanceIDService extends FirebaseInstanceIdService implements AppConfig {

    public static final String REGISTER_FCM = "RegisterFCM";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "FCM Token Refresh: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Intent registrationComplete = new Intent();
        registrationComplete.setAction(REGISTER_FCM);
        registrationComplete.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
