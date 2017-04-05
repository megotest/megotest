package test.megogo.megotest;

import android.app.Application;

import test.megogo.megotest.modules.ContextModule;

/**
 * Created by JSJEM on 04.04.2017.
 */
public class MovieApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
