package com.oxilo.oioindia.view.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.constant.Constant;
import com.oxilo.oioindia.handlers.CustomSSLSocketFactory;
import com.oxilo.oioindia.handlers.CustomX509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


public class MessageFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView ll_top_header;
   // private RatingBar ll_rating_bar;
    private EditText ll_comment_write;
    private AppCompatButton submit_btn;
    public ProgressDialog prsDlg;
    private HttpEntity resEntity;


    public MessageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {

        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_send_message, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ll_top_header = (TextView)v.findViewById(R.id.ll_top_header);
       // ll_rating_bar = (RatingBar)v.findViewById(R.id.ll_rating_bar);
        ll_comment_write = (EditText)v.findViewById(R.id.ll_comment_write);
        submit_btn = (AppCompatButton)v.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
        ll_top_header.setText(mParam2);
       // ll_comment_write.setText(Constant.fbhashkey);

        prsDlg = new ProgressDialog(getActivity());
        //LayerDrawable stars = (LayerDrawable) ll_rating_bar.getProgressDrawable();
       // stars.getDrawable(2).setColorFilter(Color.parseColor("#00BCD4"), PorterDuff.Mode.SRC_ATOP);

        return v;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_btn:
                if(isValid()){
                    new ProfileAdd().execute();
                }
                break;
        }
    }


    class ProfileAdd extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDailog();
            // baseActivity.showProgressDailog();
        }

        @Override
        protected String doInBackground(String... param) {
            try {

                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[] { new CustomX509TrustManager() },
                        new SecureRandom());
                HttpClient httpClient = new DefaultHttpClient();

                SSLSocketFactory ssf = new CustomSSLSocketFactory(ctx);
                ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                ClientConnectionManager ccm = httpClient.getConnectionManager();
                SchemeRegistry sr = ccm.getSchemeRegistry();
                sr.register(new Scheme("https", ssf, 443));
                DefaultHttpClient sslClient = new DefaultHttpClient(ccm,
                        httpClient.getParams());
                // trustEveryone();
//                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost("https://oioindia.com/api/send-message.php");
                //String basicAuth = "Basic YWRtaW46MTIzNDU";
                // httpPost.setHeader("Authorization", basicAuth);
                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                String userid = AppController.getInstance().getAppPrefs().getObject("USER_ID", String.class);
                String email = AppController.getInstance().getAppPrefs().getObject("EMAIL", String.class);
                String mobileno = AppController.getInstance().getAppPrefs().getObject("MOBILE", String.class);

                entity.addPart("userid", new StringBody(userid));
                entity.addPart("email", new StringBody(email));
                entity.addPart("mobileno", new StringBody(mobileno));
                entity.addPart("message", new StringBody(ll_comment_write.getText().toString().trim()));
                entity.addPart("businessid", new StringBody(mParam1));

                httpPost.setEntity(entity);

                HttpResponse response;
                response = httpClient.execute(httpPost);
                resEntity = response.getEntity();

                final String response_str = EntityUtils.toString(resEntity);
                Log.e("!!TAG12", "Response " + response_str);
                return response_str;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String sResponse) {

            dismissProgressDialog();

            getActivity().onBackPressed();


        }
    }



        public void showProgressDailog() {
       /* prsDlg = new ProgressDialog(this);*/
            prsDlg.setMessage("Please wait...");
            prsDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prsDlg.setIndeterminate(true);
            prsDlg.setCancelable(false);
            prsDlg.show();
        }

        public void dismissProgressDialog() {
            try {
                if (null != prsDlg) {
                    if (prsDlg.isShowing()) {
                        prsDlg.dismiss();
                    }
                }
            }catch (Exception e){

            }
        }

        public boolean isValid(){
        boolean f = true;
        if(ll_comment_write.getText().toString().trim().length() == 0){
            ll_comment_write.setError("Please enter review");
            f = false;
        }
        return f;
        }
}
