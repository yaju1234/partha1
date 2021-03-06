package com.oxilo.oioindia.viewmodal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.oxilo.oioindia.modal.CityResponse;
import com.oxilo.oioindia.repositary.main.MainRequestManager;
import com.oxilo.oioindia.view.CallAnotherActivityNavigator;

import io.reactivex.Observable;


/**
 * Created by nikk on 13/10/17.
 */

public class MyAccountViewModal extends AndroidViewModel {


    Application application;
    public ObservableBoolean enable = new ObservableBoolean();
    private CallAnotherActivityNavigator navigator;

    public MyAccountViewModal(Application application, CallAnotherActivityNavigator navigator) {
        super(application);
        this.application = application;
        this.navigator = navigator;
    }

    public Observable<CityResponse> getSubCategory(String serviceid){
        return MainRequestManager.getInstance(application.getApplicationContext()).getCity();
    }
    public void launchRegisterActivity(){
        navigator.callActivity();
    }



    public void onProfileImageClicked(View view) {
        Log.e("ViewModel", "profile Clicked");
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        CallAnotherActivityNavigator navigator;

        public Factory( Application application,CallAnotherActivityNavigator navigator) {
            mApplication = application;
            this.navigator = navigator;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MyAccountViewModal(mApplication,navigator);
        }
    }
}
