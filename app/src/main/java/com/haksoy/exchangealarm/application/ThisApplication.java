package com.haksoy.exchangealarm.application;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.haksoy.exchangealarm.dependencies.ApiComponent;
import com.haksoy.exchangealarm.dependencies.DaggerApiComponent;
import com.haksoy.exchangealarm.dependencies.DaggerNetworkComponent;
import com.haksoy.exchangealarm.dependencies.NetworkComponent;
import com.haksoy.exchangealarm.dependencies.NetworkModule;
import com.haksoy.exchangealarm.model.Constants;

/**
 * Created by haksoy on 27.03.2017.
 */

public class ThisApplication extends Application {
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        resolveDependency();
        Stetho.initializeWithDefaults(this);
        super.onCreate();
    }

    private void resolveDependency() {
        mApiComponent = DaggerApiComponent.builder().networkComponent(getNetworkComponent()).build();
    }

    public NetworkComponent getNetworkComponent() {
        return DaggerNetworkComponent.builder().networkModule(new NetworkModule(Constants.BASE_URL)).build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
