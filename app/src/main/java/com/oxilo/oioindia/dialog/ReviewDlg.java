package com.oxilo.oioindia.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.handlers.CustomSSLSocketFactory;
import com.oxilo.oioindia.handlers.CustomX509TrustManager;
import com.oxilo.oioindia.view.adapter.FaviouriteViewAdapter;
import com.oxilo.oioindia.view.adapter.ReviewAdapter;
import com.oxilo.oioindia.viewmodal.Review;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


public class ReviewDlg extends Dialog {




    private LinearLayout llMain;
    public int height, width;
   private Context context;
    ;
    public ProgressDialog prsDlg;
    private HttpEntity resEntity;
    private String businessid;
    private ImageView iv_cancel;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Review> faviouriteItems = new ArrayList<>();
    private RecyclerView list;

    public ReviewDlg(int height, int width, Context context, String businessid) {
        super(context);

     this.context = context;
        this.height = height;
        this.width = width;
        this.businessid = businessid;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_review);
        // setCancelable(false);
        prsDlg = new ProgressDialog(context);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        list = (RecyclerView) findViewById(R.id.list);
        llMain.getLayoutParams().height = (int) (height * 1.0);
        llMain.getLayoutParams().width = (int) (width * 1.0);


        faviouriteItems.clear();
        reviewAdapter = new ReviewAdapter(context, faviouriteItems);

        list.setAdapter(reviewAdapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        new ProfileAdd().execute();



    }



    class ProfileAdd extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDailog();

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
                HttpPost httpPost = new HttpPost("https://oioindia.com/api/get-reviews.php");
                //String basicAuth = "Basic YWRtaW46MTIzNDU";
                // httpPost.setHeader("Authorization", basicAuth);
                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);



                entity.addPart("businessid", new StringBody(businessid));

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

            try {
                JSONObject jsonObject = new JSONObject(sResponse);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    String review = c.getString("review");
                    String rating = c.getString("rating");
                    String image = c.getString("image");
                    String user_id = c.getString("user_id");
                    String username = c.getString("username");
                    faviouriteItems.add(new Review(review,rating,image,user_id,username));

                }
                reviewAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }




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
