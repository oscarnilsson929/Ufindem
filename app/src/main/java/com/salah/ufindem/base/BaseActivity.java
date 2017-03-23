package com.salah.ufindem.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.salah.ufindem.AppConfig;
import com.salah.ufindem.utility.CommonUtils;

public class BaseActivity extends AppCompatActivity implements AppConfig{

    public CommonUtils commonUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commonUtils = CommonUtils.getInstance();
    }

    public void navWithFinish(Activity mActivity, Class mClass) {
        Intent intent = new Intent(mActivity, mClass);
        startActivity(intent);
        mActivity.finish();
    }
}
