package com.salah.ufindem.view;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.salah.ufindem.AppController;
import com.salah.ufindem.R;
import com.salah.ufindem.base.BaseActivity;
import com.salah.ufindem.fcm.UfindemFirebaseInstanceIDService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_logo_icon)  ImageView logoImageView;
    @BindView(R.id.iv_logo_title) ImageView logoTitleImageView;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        check_google_play_services();

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                    new IntentFilter(UfindemFirebaseInstanceIDService.REGISTER_FCM));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        }
    }

    private void initUI() {
        logoTitleImageView.setVisibility(View.INVISIBLE);
    }

    private void startAnimation() {
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fadein);

        logoImageView.startAnimation(animation_1);
        animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logoTitleImageView.setVisibility(View.VISIBLE);
                logoTitleImageView.startAnimation(animation_2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                navWithFinish(SplashActivity.this, MainActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void check_google_play_services() {
        if (commonUtils.isGooglePlayServicesAvailable(this)) {
            get_fcm_token();
        } else {
            String msg = "You can't use \"" + APP_NAME + "\" app without Google Play Service.";
            AlertDialog alertDialog = commonUtils.showAlertDialog(this, msg);
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });
        }
    }

    //    GET FCM Token
    private void get_fcm_token() {
        String token = commonUtils.getStringFromSharedPreference(this, USER_FCM_ID);
        if (token != null) {
            save_token(token);
        } else {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(UfindemFirebaseInstanceIDService.REGISTER_FCM)) {
                        String token = intent.getStringExtra("token");
                        save_token(token);
                    }
                }
            };

            LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                    new IntentFilter(UfindemFirebaseInstanceIDService.REGISTER_FCM));
        }
    }

    //    Save Token
    private void save_token(String token){
        Log.e(TAG, "FCM Token: " + token);

        commonUtils.setStringToSharedPreference(this, USER_FCM_ID, token);
        AppController.fcm_token = token;

        startAnimation();
    }
}
