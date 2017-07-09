package com.haksoy.exchangealarm.dependencies;

import com.haksoy.exchangealarm.ui.MainActivity;

import dagger.Component;

/**
 * Created by haksoy on 27.03.2017.
 */

@CustomScope
@Component(modules = ApiModule.class, dependencies = NetworkComponent.class)
public interface ApiComponent {
    void inject(MainActivity activity);
}
