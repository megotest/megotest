package test.megogo.megotest.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import test.megogo.megotest.MovieApp;
import test.megogo.megotest.mvp.TmdbService;
import test.megogo.megotest.mvp.models.Error;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.views.MovieDetailsView;
import test.megogo.megotest.utils.ErrorHelper;

/**
 * Created by JSJEM on 04.04.2017.
 */
@InjectViewState
public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {

    private final long movieId;
    private final ErrorHelper errorHelper;
    @Inject
    TmdbService tmdbService;

    public MovieDetailsPresenter(final long movieId) {
        super();
        this.movieId = movieId;
        this.errorHelper = new ErrorHelper();
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
        Error serviceError = errorHelper.extractError(error);
        getViewState().onLoadingFinished();
        getViewState().onError(serviceError.getMessage());
    }
}
