package test.megogo.megotest.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import test.megogo.megotest.MovieApp;
import test.megogo.megotest.mvp.TmdbService;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.models.TopMovies;
import test.megogo.megotest.mvp.views.TopMoviesView;

/**
 * Created by JSJEM on 04.04.2017.
 */
@InjectViewState
public class TopMoviesPresenter extends BasePresenter<TopMoviesView> {

    private final AtomicBoolean isLoading;
    private int nextPage;
    @Inject
    TmdbService tmdbService;

    public TopMoviesPresenter() {
        super();
        this.isLoading = new AtomicBoolean();
        this.nextPage = 1;
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
        getViewState().onLoadingFinished();
        getViewState().onError(error.getMessage());
        isLoading.set(false);
    }

    public void selectMovie(final int position, final Movie movie) {
        getViewState().showDetails(position, movie);
    }
}
