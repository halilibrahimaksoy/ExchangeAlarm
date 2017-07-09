package com.haksoy.exchangealarm.dependencies;

import com.haksoy.exchangealarm.service.AppService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by haksoy on 27.03.2017.
 */

@Module
public class ApiModule {
    @Provides
    @CustomScope
    AppService provideUserService(Retrofit retrofit) {
        return retrofit.create(AppService.class);
    }
}
