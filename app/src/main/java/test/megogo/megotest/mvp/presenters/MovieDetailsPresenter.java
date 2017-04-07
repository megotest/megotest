package test.megogo.megotest.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.atomic.AtomicBoolean;

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
    private final AtomicBoolean isLoading;
    @Inject
    TmdbService tmdbService;

    public MovieDetailsPresenter(final long movieId) {
        super();
        this.movieId = movieId;
        this.errorHelper = new ErrorHelper();
        this.isLoading = new AtomicBoolean();
        MovieApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadMovieDescription();
    }

    public void loadMovieDescription() {
        if(isLoading.compareAndSet(false, true)) {
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
    }

    private void onSuccess(final Movie movieDescription) {
        getViewState().onLoadingFinished();
        getViewState().showMovieDescription(movieDescription);
        isLoading.set(false);
    }

    private void onFail(final Throwable error) {
        Error serviceError = errorHelper.extractError(error);
        getViewState().onLoadingFinished();
        getViewState().onError(serviceError.getMessage());
    }

    public void errorProcessed() {
        isLoading.set(false);
    }
}
