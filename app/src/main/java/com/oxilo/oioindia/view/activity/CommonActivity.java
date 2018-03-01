package com.oxilo.oioindia.view.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oxilo.oioindia.R;

/**
 * Created by kamal on 02/23/2018.
 */

public class CommonActivity extends BaseActivity {
    ProgressDialog progDailog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        WebView webView = (WebView) findViewById(R.id.webView);
        progDailog=new ProgressDialog(CommonActivity.this);
        progDailog.setMessage("Loading...");

        String val=getIntent().getStringExtra("key");
        if (val!=null){
            if (val.equalsIgnoreCase("aboutUs")){
                shouldOverrideUrlLoading(webView,"https://www.oioindia.com/about-us");

              //  webView.loadUrl("https://www.oioindia.com/about-us");
            }else if(val.equalsIgnoreCase("TramsCondition")){
                shouldOverrideUrlLoading(webView,"https://www.oioindia.com/terms-conditions");

                //webView.loadUrl("https://www.oioindia.com/terms-conditions");
            }else if(val.equalsIgnoreCase("PrivacyPolicy")){
                shouldOverrideUrlLoading(webView,"https://www.oioindia.com/privacy-policy");
               // webView.loadUrl("https://www.oioindia.com/privacy-policy");
            }

        }
    }
    public void shouldOverrideUrlLoading (WebView webView, String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setLoadsImagesAutomatically(true);

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progDailog.setIndeterminate(true);
                progDailog.setCancelable(false);
                progDailog.show();
            }
        });

        webView.loadUrl(url);

    }
}
