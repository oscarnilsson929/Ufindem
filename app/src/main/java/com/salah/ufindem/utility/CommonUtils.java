package com.salah.ufindem.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.salah.ufindem.AppConfig;

public class CommonUtils implements AppConfig {
    private final static long ALERT_TIME = 2000;

    private static CommonUtils commonUtils = null;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public static CommonUtils getInstance() {
        if (commonUtils == null) {
            commonUtils = new CommonUtils();
        }

        return commonUtils;
    }

    // SharedPreference
    private SharedPreferences getSharedPreferences(Context mContext) {
        return mContext.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(Context mContext) {
        sharedPreferences = getSharedPreferences(mContext);
        return sharedPreferences.edit();
    }

    public String getStringFromSharedPreference(Context mContext, String key) {
        sharedPreferences = getSharedPreferences(mContext);
        return sharedPreferences.getString(key, null);
    }

    public void setStringToSharedPreference(Context mContext, String key, String val) {
        editor = getEditor(mContext);
        editor.putString(key, val);
        editor.apply();
    }

//    Check if Google Play Services is Available
    public boolean isGooglePlayServicesAvailable(Context mContext) {
        int requestCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext);

        if (ConnectionResult.SUCCESS != requestCode) {
            //Check type of error
            if (GoogleApiAvailability.getInstance().isUserResolvableError(requestCode)) {
                showAlertDialog(mContext, "Google Play Service is not installed/enabled in this device!");
                //So notification
                GoogleApiAvailability.getInstance().showErrorNotification(mContext, requestCode);
            } else {
                showAlertDialog(mContext, "This device does not support for Google Play Service!");
            }
            return false;
        }

        return true;
    }

    //    AlertDialog with delay
    public AlertDialog showAlertDialog(Context context, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context).setCancelable(false);
        TextView alertMsg = new TextView(context);
        alertMsg.setText(message);
        alertMsg.setTextSize(18);
        alertMsg.setPadding(16, 64, 16, 64);
        alertMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog.setView(alertMsg);

        final AlertDialog alert = dialog.create();
        alert.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        handler.postDelayed(runnable, ALERT_TIME);

        return alert;
    }
}
