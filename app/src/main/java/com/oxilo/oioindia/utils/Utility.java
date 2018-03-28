package com.oxilo.oioindia.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yaju on 20/3/18.
 */

public class Utility {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        // test for connection for WIFI
        if (info != null && info.isAvailable() && info.isConnected()) {
            return true;
        }
        info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // test for connection for Mobile
        if (info != null && info.isAvailable() && info.isConnected()) {
            return true;
        }
        return false;
    }
}
