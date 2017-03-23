package com.salah.ufindem.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.salah.ufindem.AppController;
import com.salah.ufindem.R;
import com.salah.ufindem.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

//@SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
public class MainActivity extends BaseActivity {

    @BindView(R.id.wv_content) WebView contentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initUI();
    }

    private void initUI() {
        Toast.makeText(this, AppController.fcm_token, Toast.LENGTH_LONG).show();

        WebSettings settings = contentWebView.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setLoadsImagesAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
//        settings.setUserAgentString("UFMPHONE");

//        contentWebView.clearCache(true);
//        contentWebView.clearHistory();
        contentWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        contentWebView.setWebViewClient(new WebViewClient());
        contentWebView.loadUrl(BASE_URL);
    }

    public class WebAppInterface {

        private Context mContext;

        public WebAppInterface(Context mContext) {
            this.mContext = mContext;
        }

        @JavascriptInterface
        public String retDevID() {
            showToast(AppController.fcm_token);
            return AppController.fcm_token;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, "Javascript Interface\n\n" + toast, Toast.LENGTH_SHORT).show();
        }
    }


}
