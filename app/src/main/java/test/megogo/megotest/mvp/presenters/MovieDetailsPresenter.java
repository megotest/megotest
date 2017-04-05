package test.megogo.megotest.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import test.megogo.megotest.MovieApp;
import test.megogo.megotest.mvp.TmdbService;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.views.MovieDetailsView;

/**
 * Created by JSJEM on 04.04.2017.
 */
@InjectViewState
public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {

    private final long movieId;
    @Inject
    TmdbService tmdbService;

    public MovieDetailsPresenter(final long movieId) {
        super();
        this.movieId = movieId;
        MovieApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadMovieDescription();
    }

    public void loadMovieDescription() {
        getViewState().onStartLoading();
        Subscription subscription = tmdbService.getMovieDetails(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieDesc -> {
                            onSuccess(movieDesc);
                        },
                        error -> {
                            onFail(error);
                        });
        unsubscribeOnDestroy(subscription);
    }

    private void onSuccess(final Movie movieDescription) {
        getViewState().onLoadingFinished();
        getViewState().showMovieDescription(movieDescription);
    }

    private void onFail(final Throwable error) {
        getViewState().onLoadingFinished();
        getViewState().onError(error.getMessage());
    }
}
