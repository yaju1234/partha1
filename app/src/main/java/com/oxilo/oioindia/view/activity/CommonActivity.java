package com.oxilo.oioindia.view.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.oxilo.oioindia.R;

/**
 * Created by kamal on 02/23/2018.
 */

public class CommonActivity extends BaseActivity {

    ProgressDialog progDailog;
    private TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        WebView webView = (WebView) findViewById(R.id.webView);
        title = (TextView) findViewById(R.id.title);
       progDailog=new ProgressDialog(CommonActivity.this);
        progDailog.setMessage("Loading...");

        String val=getIntent().getStringExtra("key");
        if (val!=null){
            if (val.equalsIgnoreCase("aboutUs")){
                title.setText("About Us");
                webView.loadUrl("https://www.oioindia.com/about-us");

              //  webView.loadUrl("https://www.oioindia.com/about-us");
            }else if(val.equalsIgnoreCase("TramsCondition")){
                title.setText("Terms and Conditions");
                webView.loadUrl("https://www.oioindia.com/terms-conditions");

                //webView.loadUrl("https://www.oioindia.com/terms-conditions");
            }else if(val.equalsIgnoreCase("PrivacyPolicy")){
                title.setText("Privecy Policy");
              //  shouldOverrideUrlLoading(webView,"https://www.oioindia.com/privacy-policy");
                webView.loadUrl("https://www.oioindia.com/privacy-policy");
            }

        }
    }

}
