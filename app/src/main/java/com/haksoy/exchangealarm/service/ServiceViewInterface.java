package com.haksoy.exchangealarm.service;

/**
 * Created by haksoy on 27.03.2017.
 */

public interface ServiceViewInterface<T> {

    void onCompleted();

    void onError(String message);

    void onSuccess(T object);


}
