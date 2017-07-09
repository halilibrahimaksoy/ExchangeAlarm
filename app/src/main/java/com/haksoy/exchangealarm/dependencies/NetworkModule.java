package com.haksoy.exchangealarm.dependencies;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.haksoy.exchangealarm.service.HtmlMappingAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by haksoy on 27.03.2017.
 */

@Module
public class NetworkModule {
    private String mBaseUrl;

    public NetworkModule(String BaseUrl) {
        mBaseUrl = BaseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(HtmlMappingAdapter htmlMappingAdapter, RxJavaCallAdapterFactory rxJavaCallAdapterFactory) {
        return new Retrofit.Builder().baseUrl(mBaseUrl)
                .addConverterFactory(htmlMappingAdapter)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build())
                .build();
    }

    @Provides
    @Singleton
    HtmlMappingAdapter provideHtmlMappingAdapter() {
        return new HtmlMappingAdapter();
    }

    @Provides
    @Singleton
    RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

}
