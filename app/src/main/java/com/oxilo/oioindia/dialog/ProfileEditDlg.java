package com.oxilo.oioindia.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.handlers.CustomSSLSocketFactory;
import com.oxilo.oioindia.handlers.CustomX509TrustManager;
import com.oxilo.oioindia.interfaces.Login_Interface;
import com.oxilo.oioindia.view.activity.MainActivity;
import com.oxilo.oioindia.view.common.NavigationController;

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
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


public class ProfileEditDlg extends Dialog {


    public interface OnProfileUpdateListener{
        public void onProfileUpdateSuccess();
        public void onProfileUpdateFailure();
    }

   // private Login_Interface login_interface;
    private LinearLayout llMain;
    public int height, width;
    private ImageView iv_cancel;
    private Button register_btn;
    private EditText fname,lname,address,pincode;
    private EditText password;
    private Context context;
    private OnProfileUpdateListener listener;
    String s1,s2,  s3,  s4,userid;
    public ProgressDialog prsDlg;
    private HttpEntity resEntity;

    public ProfileEditDlg(int height, int width,  Context context,String userid,String s1, String s2, String s3, String s4,OnProfileUpdateListener listener) {
        super(context);


        this.listener = listener;
        this.context = context;
        this.height = height;
        this.width = width;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.userid = userid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_update_profile);
       // setCancelable(false);
        prsDlg = new ProgressDialog(context);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        register_btn = (Button) findViewById(R.id.register_btn);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        llMain.getLayoutParams().height = (int) (height * 1.0);
        llMain.getLayoutParams().width = (int) (width * 1.0);


        fname.setText(s1!=null?s1:"");
        lname.setText(s2!=null?s2:"");
        address.setText(s3!=null?s3:"");
        pincode.setText(s4!=null?s4:"");
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(isValid()){
                        new ProfileAdd().execute();
               }
            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
               // inter.cancel();
            }
        });

    }
    public void hideKeyboard() {
        /*
        if (!isKeyboardVisible())
           return;
        */
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            if (inputMethodManager.isAcceptingText()) {
                try {
                    inputMethodManager.hideSoftInputFromWindow(this.findViewById(android.R.id.content).getWindowToken(), 0);
                } catch (Exception e) {
                    //  MyLog.printStackTrace(e);
                }
                try {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                } catch (Exception e) {
                    //  MyLog.printStackTrace(e);
                }
                // inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            }
        } else {
            if (view instanceof EditText) {
                ((EditText) view).setText(((EditText) view).getText().toString()); // reset edit text bug on some keyboards bug
                try {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {
                    // MyLog.printStackTrace(e);
                }
                try {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                } catch (Exception e) {
                    //MyLog.printStackTrace(e);
                }
                // inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            }
        }
    }

    public boolean isValid(){
        boolean f = true;

        if(fname.getText().toString().trim().length() ==0){
            fname.setError("Please enter First Name");
            f = false;
        }else if(lname.getText().toString().trim().length() ==0){
            lname.setError("Please enter Last Name");
            f = false;
        }else if(address.getText().toString().trim().length() ==0){
            address.setError("Please enter address");
            f = false;
        }else if(pincode.getText().toString().trim().length() ==0){
            pincode.setError("Please enter pincode");
            f = false;
        }
        return f;
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
                HttpPost httpPost = new HttpPost("https://oioindia.com/api/update-profile.php");
                //String basicAuth = "Basic YWRtaW46MTIzNDU";
                // httpPost.setHeader("Authorization", basicAuth);
                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);



                entity.addPart("first_name", new StringBody(fname.getText().toString().trim()));
                entity.addPart("last_name", new StringBody(lname.getText().toString().trim()));
                entity.addPart("address", new StringBody(address.getText().toString().trim()));
                entity.addPart("pincode", new StringBody(pincode.getText().toString().trim()));
                entity.addPart("userID", new StringBody(userid));

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


            AppController.getInstance().getAppPrefs().putObject("FNAME",fname.getText().toString().trim());
            AppController.getInstance().getAppPrefs().putObject("LNAME", lname.getText().toString().trim());
            AppController.getInstance().getAppPrefs().putObject("ADDRESS", address.getText().toString().trim());
            AppController.getInstance().getAppPrefs().putObject("PINCODE", pincode.getText().toString().trim());

            AppController.getInstance().getAppPrefs().commit();

            listener.onProfileUpdateSuccess();
            dismiss();


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


}
