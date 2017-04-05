package test.megogo.megotest.mvp.views;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import test.megogo.megotest.mvp.models.Movie;

/**
 * Created by JSJEM on 04.04.2017.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface TopMoviesView extends BaseView {

    void setMovies(final List<Movie> movies, final boolean mayLoadMore);

    @StateStrategyType(AddToEndStrategy.class)
    void addMovies(final List<Movie> movies, final boolean mayLoadMore);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showDetails(final int position, final Movie movie);
}
