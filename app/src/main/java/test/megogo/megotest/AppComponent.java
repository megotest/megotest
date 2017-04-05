package test.megogo.megotest;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import test.megogo.megotest.modules.ContextModule;
import test.megogo.megotest.modules.TmdbModule;
import test.megogo.megotest.mvp.TmdbService;
import test.megogo.megotest.mvp.presenters.MovieDetailsPresenter;
import test.megogo.megotest.mvp.presenters.TopMoviesPresenter;

/**
 * Created by JSJEM on 04.04.2017.
 */
@Singleton
@Component(modules = {ContextModule.class, TmdbModule.class})
public interface AppComponent {

    Context getContext();

    TmdbService getTmdbService();

    void inject(final TopMoviesPresenter topMoviesPresenter);

    void inject(final MovieDetailsPresenter movieDetailsPresenter);
}
