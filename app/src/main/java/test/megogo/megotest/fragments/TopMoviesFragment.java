package test.megogo.megotest.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.megogo.megotest.R;
import test.megogo.megotest.adapters.MoviesAdapter;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.presenters.TopMoviesPresenter;
import test.megogo.megotest.mvp.views.RootView;
import test.megogo.megotest.mvp.views.TopMoviesView;
import test.megogo.megotest.utils.ErrorHelper;
import test.megogo.megotest.views.MaterialProgressView;
import test.megogo.megotest.views.SpaceItemDecoration;

/**
 * Created by JSJEM on 04.04.2017.
 */

public class TopMoviesFragment extends MvpAppCompatFragment implements TopMoviesView,
        MoviesAdapter.MovieScrollListener, MoviesAdapter.MovieClickListener {

    private static final int ITEM_SPACE = 5;

    @InjectPresenter
    TopMoviesPresenter topMoviesPresenter;

    @BindView(R.id.movies_recycler_view)
    RecyclerView moviesRecycler;
    @BindView(R.id.movie_loading)
    MaterialProgressView progressView;

    private MoviesAdapter adapter;
    private boolean mayLoadMore;
    private Snackbar errorSnackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new MoviesAdapter();
        int spanCount = view.getResources().getInteger(R.integer.movie_grid_spans);
        moviesRecycler.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        moviesRecycler.setAdapter(adapter);
        moviesRecycler.addItemDecoration(new SpaceItemDecoration(ITEM_SPACE, ITEM_SPACE));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.fragment_top_movies_title);
        adapter.setScrollListener(this);
        adapter.setMovieClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.setScrollListener(null);
        adapter.setMovieClickListener(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideError();
    }


    private void hideError() {
        if (isRemoving()) {
            topMoviesPresenter.errorProcessed();
            if (errorSnackbar != null) {
                errorSnackbar.dismiss();
            }
        }
    }

    @Override
    public void setMovies(List<Movie> movies, boolean mayLoadMore) {
        adapter.setMovies(movies);
        this.mayLoadMore = mayLoadMore;
    }

    @Override
    public void addMovies(List<Movie> movies, boolean mayLoadMore) {
        adapter.addMovies(movies);
        this.mayLoadMore = mayLoadMore;
    }

    @Override
    public void showDetails(int position, Movie movie) {
        ((RootView)getActivity()).showMovieDetailsContainer(movie);
    }

    @Override
    public void onStartLoading() {
        if(adapter.isEmpty()) {
            progressView.setVisibility(View.VISIBLE);
        } else {
            adapter.setLoading(true);
        }
    }

    @Override
    public void onLoadingFinished() {
        progressView.setVisibility(View.INVISIBLE);
        adapter.setLoading(false);
    }

    @Override
    public void onError(String errorDescription) {
        if(isResumed()) {
            errorSnackbar = ErrorHelper
                    .createErrorSnackbar(getActivity(), errorDescription);
            errorSnackbar.setAction(R.string.error_button_retry, (view) -> {
                topMoviesPresenter.errorProcessed();
                topMoviesPresenter.loadItems();
                errorSnackbar = null;
            });
            errorSnackbar.show();
        } else {
            topMoviesPresenter.errorProcessed();
        }
    }

    @Override
    public void onEndReached() {
        if(mayLoadMore) {
            topMoviesPresenter.loadItems();
        }
    }

    @Override
    public void onMovieClick(int position, Movie movie) {
        topMoviesPresenter.selectMovie(position, movie);
    }
}
