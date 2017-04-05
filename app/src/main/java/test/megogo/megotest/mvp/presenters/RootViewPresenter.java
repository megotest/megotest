package test.megogo.megotest.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.views.RootView;

/**
 * Created by JSJEM on 04.04.2017.
 */
@InjectViewState
public class RootViewPresenter extends MvpPresenter<RootView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showTopMoviesContainer();
    }

    public void showMovieDetails(final Movie movie) {
        getViewState().showMovieDetailsContainer(movie);
    }
}
