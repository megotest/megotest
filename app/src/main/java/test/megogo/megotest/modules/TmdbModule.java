package test.megogo.megotest.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import test.megogo.megotest.mvp.TmdbApi;
import test.megogo.megotest.mvp.TmdbService;

/**
 * Created by JSJEM on 04.04.2017.
 */
@Module(includes = {ApiModule.class})
public class TmdbModule {

    @Provides
    @Singleton
    public TmdbService providesTmdbService(final Context context, final TmdbApi api) {
        return new TmdbService(context, api);
    }
}
