package test.megogo.megotest.mvp.views;

import com.arellomobile.mvp.MvpView;

/**
 * Created by JSJEM on 04.04.2017.
 */

public interface BaseView extends MvpView {

    void onStartLoading();

    void onLoadingFinished();

    void onError(final String errorDescription);

}
