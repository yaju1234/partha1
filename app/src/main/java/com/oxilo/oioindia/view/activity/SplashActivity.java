package com.oxilo.oioindia.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.GeoSearchModel;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.constant.Constant;
import com.oxilo.oioindia.databinding.ActivitySplashBinding;
import com.oxilo.oioindia.view.common.*;
import com.oxilo.oioindia.vo.Splash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends BaseLocationActivity {

    // Used to load the 'native-lib' library on application startup.
   /* static {
        System.loadLibrary("native-lib");
    }*/

    ActivitySplashBinding binding;

//    RxGps rxGps;

    private static final String BUNDLE_SELECTED_CITY = "bundle_selected_planet";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        if (AppController.getInstance().getAppPrefs().getObject("LOCATION", String.class) == null) {
            AppController.getInstance().getAppPrefs().putObject("LOCATION", "");
            AppController.getInstance().getAppPrefs().commit();
        }

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.oxilo.oioindia",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Constant.fbhashkey =  Base64.encodeToString(md.digest(), Base64.DEFAULT);

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        binding.setSplash(new Splash(this));

//        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.M) {
//            if (!isGPSEnable()) {
//                locationDialog();
//            }else{
//                resumeService();
//            }
//        }
//        else
//            resumeService();

        resumeService();
    }


    private void validate(String address, String city) {
        String login = AppController.getInstance().getAppPrefs().getObject("LOGIN", String.class);
        if (login != null) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            i.putExtra("CITY", "" + city);
            i.putExtra("ADDRESS", "" + address);
            startActivity(i);
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    i.putExtra("CITY", "" + city);
                    i.putExtra("ADDRESS", "" + address);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
    }

    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getAddressText(Address address) {
        String addressText = "";
        final int maxAddressLineIndex = address.getMaxAddressLineIndex();
        for (int i = 0; i <= maxAddressLineIndex; i++) {
            addressText += address.getAddressLine(i);
            if (i != maxAddressLineIndex) {
                addressText += "\n";
            }
        }

        return addressText;
    }


    @Override
    public void onConnected(Location location) {
        if (location != null) {
            stopLocationUpdates();
            Log.e("Latitude", " " + location.getLatitude());
            Log.e("Longitude", " " + location.getLongitude());
            AppController.getInstance().getAppPrefs().putObject("LOCATION_LAT", location.getLatitude());
            AppController.getInstance().getAppPrefs().putObject("LOCATION_LON", location.getLongitude());
            AppController.getInstance().getAppPrefs().commit();
            String address = GeoSearchModel.addressByLocation(location.getLatitude(), location.getLongitude(), SplashActivity.this);
            String city = GeoSearchModel.getCityInfo(location.getLatitude(), location.getLongitude(), SplashActivity.this);
            Log.e("city", " " + city);
            Log.e("address", " " + address);
            validate(address, city);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }
}