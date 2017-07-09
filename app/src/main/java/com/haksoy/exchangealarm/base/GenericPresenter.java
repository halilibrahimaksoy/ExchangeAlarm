package com.haksoy.exchangealarm.base;

import com.haksoy.exchangealarm.service.ServiceViewInterface;

import rx.Observable;
import rx.Observer;

/**
 * Created by haksoy on 4.04.2017.
 */

public class GenericPresenter<T> extends BasePresenter implements Observer<T> {
    private ServiceViewInterface mViewInterface;

    public GenericPresenter(ServiceViewInterface mViewInterface) {
        this.mViewInterface = mViewInterface;
    }

    @Override
    public void onCompleted() {
        mViewInterface.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mViewInterface.onError(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        mViewInterface.onSuccess(t);
    }

    public void callService(Observable observable) {
        subscribe(observable, GenericPresenter.this);
    }

    public void callAndIterateService(Observable observable, long time) {
        callService(observable);
        timedSubscribe(observable, GenericPresenter.this, time);
    }
}
