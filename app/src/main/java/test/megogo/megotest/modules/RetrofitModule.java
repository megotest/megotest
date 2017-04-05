package test.megogo.megotest.modules;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by JSJEM on 04.04.2017.
 */
@Module
public class RetrofitModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit(final Retrofit.Builder builder) {
        return builder.baseUrl("https://api.themoviedb.org/").build();
    }

    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder(final Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(converterFactory);
    }

    @Provides
    @Singleton
    public Converter.Factory provideConverterFactory(final Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

}
