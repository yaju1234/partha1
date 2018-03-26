package com.oxilo.oioindia.view.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.oxilo.oioindia.R;
import com.oxilo.oioindia.camera.GalleryImagePickerHelper;
import com.oxilo.oioindia.camera.PhotoDetails;
import com.oxilo.oioindia.even_bus.MessageEventContect;
import com.oxilo.oioindia.event.EventPhotoChosenFromGallery;
import com.oxilo.oioindia.event.EventPickImageViaGallery;
import com.oxilo.oioindia.handlers.CustomSSLSocketFactory;
import com.oxilo.oioindia.handlers.CustomX509TrustManager;
import com.oxilo.oioindia.permission.PermissionConstant;
import com.oxilo.oioindia.utils.Utility;
import com.oxilo.oioindia.view.common.NavigationController;
import com.oxilo.oioindia.view.fragments.AllFragment;
import com.oxilo.oioindia.view.fragments.FaviouriteFragment;
import com.oxilo.oioindia.view.fragments.MainFragment;

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
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.File;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, AllFragment.OnFragmentInteractionListener, FaviouriteFragment.OnFragmentInteractionListener {
    final int RC_PICK_IMAGE_FROM_GALLERY = 1;
    private GalleryImagePickerHelper galleryImagePickerHelper;
    public BottomNavigationView navigation;
    private boolean doubleBackToExitPressedOnce = false;
    String currentVersion;
    private HttpEntity resEntity;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener

            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    String city = getIntent().getStringExtra("CITY");
                    String address = getIntent().getStringExtra("ADDRESS");
                    NavigationController navigationController4 = new NavigationController(MainActivity.this);
                    navigationController4.navigateToMain(city, address);
                    return true;
                case R.id.navigation_dashboard:
                    NavigationController navigationController1 = new NavigationController(MainActivity.this);
                    navigationController1.navigateToAddBusiness();
                    return true;
                case R.id.navigation_myaccuount:
                    NavigationController navigationController = new NavigationController(MainActivity.this);
                    navigationController.navigateToMyAccout();


                    return true;
                case R.id.business_list:
                    NavigationController navigationController2 = new NavigationController(MainActivity.this);
                    navigationController2.navigateToList();
                    return true;
            }
            return false;
        }
    };

    public void callList() {
        NavigationController navigationController2 = new NavigationController(MainActivity.this);
        navigationController2.navigateToList();


    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setContentView(R.layout.activity_main);
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        internetCheck();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        if (savedInstanceState == null) {
            String city = getIntent().getStringExtra("CITY");
            String address = getIntent().getStringExtra("ADDRESS");
            NavigationController navigationController = new NavigationController(this);
            navigationController.navigateToMain(city, address);
        }
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        /*BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setShiftingMode(false);
            itemView.setChecked(false);
        }
        navigation.enableS*/
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        galleryImagePickerHelper = new GalleryImagePickerHelper();
        new GetVersionCode().execute();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PermissionConstant.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE2:
                    EventBus.getDefault().post(new MessageEventContect("pic_add"));
                    break;
            }

        } else {
            Toast.makeText(MainActivity.this, "Select permissions", Toast.LENGTH_SHORT).show();
        }

    }

    public void onEventMainThread(EventPickImageViaGallery e) {
        Intent i = galleryImagePickerHelper.buildPickFileFromGalleryIntent(this);
        startActivityForResult(i, RC_PICK_IMAGE_FROM_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_PICK_IMAGE_FROM_GALLERY:
                if (resultCode == RESULT_CANCELED)
                    return;
                PhotoDetails photoDetails = galleryImagePickerHelper.processOnActivityResult(this, data);
                EventBus.getDefault().post(new EventPhotoChosenFromGallery(photoDetails));
                break;

        }
    }

    public void internetCheck() {

        if (!Utility.isOnline(MainActivity.this)) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Alert")
                    .setMessage("No internet connection. Please check your network connection.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            MainActivity.this.finish();
                        }
                    })
                    .show();
        }

    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            exitApplication();
        }


    }

    private void exitApplication() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.double_tap_to_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }


    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

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
                HttpPost httpPost = new HttpPost("https://oioindia.com/api/check-app-version.php");
                //String basicAuth = "Basic YWRtaW46MTIzNDU";
                // httpPost.setHeader("Authorization", basicAuth);
                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                entity.addPart("version", new StringBody(currentVersion));

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
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null) {

                try {
                    JSONObject jsonObject = new JSONObject(onlineVersion);
                    if (!jsonObject.getString("version").equalsIgnoreCase("matched")) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Update Application")
                                .setMessage("A newer version of this application is now available in google play. Please update your application for better performance")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("market://details?id="+MainActivity.this.getPackageName()));
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
