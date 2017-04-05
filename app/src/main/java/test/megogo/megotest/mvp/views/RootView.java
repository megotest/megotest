package test.megogo.megotest.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import test.megogo.megotest.mvp.models.Movie;

/**
 * Created by JSJEM on 04.04.2017.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface RootView extends MvpView {

    void showTopMoviesContainer();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMovieDetailsContainer(final Movie movie);
}
