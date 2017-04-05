package test.megogo.megotest.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import test.megogo.megotest.mvp.TmdbApi;

/**
 * Created by JSJEM on 04.04.2017.
 */
@Module(includes = {RetrofitModule.class})
public class ApiModule {

    @Provides
    @Singleton
    public TmdbApi providesApi(final Retrofit retrofit) {
        return retrofit.create(TmdbApi.class);
    }
}
