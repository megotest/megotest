package test.megogo.megotest.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JSJEM on 04.04.2017.
 */
@Module
public class ContextModule {

    private final Context appContext;

    public ContextModule(final Context context) {
        this.appContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return appContext;
    }
}
