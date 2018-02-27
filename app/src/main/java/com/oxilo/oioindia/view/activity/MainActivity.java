package com.oxilo.oioindia.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.oxilo.oioindia.R;
import com.oxilo.oioindia.camera.GalleryImagePickerHelper;
import com.oxilo.oioindia.camera.PhotoDetails;
import com.oxilo.oioindia.even_bus.MessageEventContect;
import com.oxilo.oioindia.event.EventPhotoChosenFromGallery;
import com.oxilo.oioindia.event.EventPickImageViaGallery;
import com.oxilo.oioindia.permission.PermissionConstant;
import com.oxilo.oioindia.view.common.NavigationController;
import com.oxilo.oioindia.view.fragments.AllFragment;
import com.oxilo.oioindia.view.fragments.FaviouriteFragment;
import com.oxilo.oioindia.view.fragments.MainFragment;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, AllFragment.OnFragmentInteractionListener, FaviouriteFragment.OnFragmentInteractionListener {
    final int RC_PICK_IMAGE_FROM_GALLERY = 1;
    private GalleryImagePickerHelper galleryImagePickerHelper;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
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
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        if (savedInstanceState == null) {
            String city = getIntent().getStringExtra("CITY");
            String address = getIntent().getStringExtra("ADDRESS");
            NavigationController navigationController = new NavigationController(this);
            navigationController.navigateToMain(city, address);
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setShiftingMode(false);
            itemView.setChecked(false);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        galleryImagePickerHelper = new GalleryImagePickerHelper();
        EventBus.getDefault().register(this);
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
}
