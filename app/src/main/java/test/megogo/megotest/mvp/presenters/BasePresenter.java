package test.megogo.megotest.mvp.presenters;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by JSJEM on 04.04.2017.
 */
public class BasePresenter<T extends MvpView> extends MvpPresenter<T> {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected void unsubscribeOnDestroy(final Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }
}
