package test.megogo.megotest.mvp.views;

import test.megogo.megotest.mvp.models.Movie;

/**
 * Created by JSJEM on 04.04.2017.
 */

public interface MovieDetailsView extends BaseView {

    void showMovieDescription(final Movie movieDescription);
}
