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
import test.megogo.megotest.mvp.models.TopMovies;
import test.megogo.megotest.mvp.views.TopMoviesView;
import test.megogo.megotest.utils.ErrorHelper;

/**
 * Created by JSJEM on 04.04.2017.
 */
@InjectViewState
public class TopMoviesPresenter extends BasePresenter<TopMoviesView> {

    private final AtomicBoolean isLoading;
    private final ErrorHelper errorHelper;
    private int nextPage;
    @Inject
    TmdbService tmdbService;

    public TopMoviesPresenter() {
        super();
        this.isLoading = new AtomicBoolean();
        this.nextPage = 1;
        this.errorHelper = new ErrorHelper();
        MovieApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadItems();
    }

    public void loadItems() {
        if (isNextPageExists() && isLoading.compareAndSet(false, true)) {
            getViewState().onStartLoading();
            Subscription subscription = tmdbService.getTopMovies(nextPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(topMovies -> {
                                onSuccess(topMovies);
                            },
                            error -> {
                                onFail(error);
                            });
            unsubscribeOnDestroy(subscription);
        }
    }

    private boolean isNextPageExists() {
        return nextPage > 0;
    }

    private void onSuccess(final TopMovies topMovies) {
        getViewState().onLoadingFinished();
        if(topMovies.getPage() < topMovies.getTotalPages()) {
            nextPage++;
        } else {
            nextPage = 0;
        }
        getViewState().addMovies(topMovies.getResults(), isNextPageExists());
        isLoading.set(false);
    }

    private void onFail(final Throwable error) {
        Error serviceError = errorHelper.extractError(error);
        getViewState().onLoadingFinished();
        getViewState().onError(serviceError.getMessage());
    }

    public void selectMovie(final int position, final Movie movie) {
        getViewState().showDetails(position, movie);
    }


    public void errorProcessed() {
        isLoading.set(false);
    }
}
