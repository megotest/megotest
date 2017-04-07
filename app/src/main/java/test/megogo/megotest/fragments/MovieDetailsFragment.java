package test.megogo.megotest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import test.megogo.megotest.R;
import test.megogo.megotest.mvp.TmdbService;
import test.megogo.megotest.mvp.models.Genre;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.presenters.MovieDetailsPresenter;
import test.megogo.megotest.mvp.views.MovieDetailsView;
import test.megogo.megotest.utils.ErrorHelper;
import test.megogo.megotest.views.MaterialProgressView;

/**
 * Created by JSJEM on 04.04.2017.
 */

public class MovieDetailsFragment extends MvpAppCompatFragment implements MovieDetailsView {

    private static final String ARG_MOVIE_ID = "test.megogo.megotest.movie_id";

    @InjectPresenter
    MovieDetailsPresenter movieDetailsPresenter;

    @BindView(R.id.movie_loading)
    MaterialProgressView progressView;
    @BindView(R.id.movie_image)
    ImageView movieImage;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.movie_year)
    TextView movieYear;
    @BindView(R.id.movie_genres)
    TextView movieGenres;
    @BindView(R.id.movie_description)
    TextView movieDescription;

    private SimpleDateFormat dateFormat;
    private Snackbar errorSnackbar;

    public static MovieDetailsFragment createInstance(final Movie movie) {
        MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_MOVIE_ID, movie.getId());
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    @ProvidePresenter
    MovieDetailsPresenter provideDetailsPresenter() {
        return new MovieDetailsPresenter(getArguments().getLong(ARG_MOVIE_ID));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        dateFormat = new SimpleDateFormat("yyyy");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.fragment_movie_details_title);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideError();
    }

    private void hideError() {
        if (isRemoving()) {
            movieDetailsPresenter.errorProcessed();
            if (errorSnackbar != null) {
                errorSnackbar.dismiss();
            }
        }
    }

    @Override
    public void showMovieDescription(Movie movieDesc) {
        movieTitle.setText(movieDesc.getTitle());
        movieYear.setText(dateFormat.format(movieDesc.getReleaseDate()));
        movieGenres.setText(getGenres(movieDesc));
        movieDescription.setText(movieDesc.getOverview());
        int expectedSize = (int) movieImage.getContext().getResources()
                .getDimension(R.dimen.movie_poster_default_size);
        Picasso.with(movieImage.getContext())
                .load(TmdbService.getImageUrl(movieDesc.getPosterPath()))
                .error(R.drawable.image_movie_stub)
                .resize(expectedSize, expectedSize)
                .centerInside().into(movieImage);
    }

    private String getGenres(Movie movieDesc) {
        StringBuilder builder = new StringBuilder();
        for (Genre genre : movieDesc.getGenres()) {
            builder.append(genre).append(", ");
        }
        String genresString = builder.toString();
        return genresString.isEmpty() ? genresString :
                genresString.substring(0, genresString.length() - 2);
    }

    @Override
    public void onStartLoading() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFinished() {
        progressView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(String errorDescription) {
        if (isResumed()) {
            errorSnackbar = ErrorHelper
                    .createErrorSnackbar(getActivity(), errorDescription);
            errorSnackbar.setAction(R.string.error_button_retry, (view) -> {
                movieDetailsPresenter.errorProcessed();
                movieDetailsPresenter.loadMovieDescription();
                errorSnackbar = null;
            });
            errorSnackbar.show();
        } else {
            movieDetailsPresenter.errorProcessed();
        }

    }

}
