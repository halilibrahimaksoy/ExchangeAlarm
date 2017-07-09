package com.haksoy.exchangealarm.service;

import com.haksoy.exchangealarm.model.Exchange;
import com.haksoy.exchangealarm.model.Market;
import com.haksoy.exchangealarm.model.MarketScreen;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by haksoy on 27.03.2017.
 */

public interface AppService {
    @GET("/piyasa-ekrani/canli-borsa")
    Observable<List<Exchange>> getExchange(@Query("Category") String category);

    @GET("/piyasa-ekrani")
    Observable<List<MarketScreen>> getMarketScreen(@Query("Category") String category);

    @GET("/")
    Observable<Market> getMarket();
}
