package test.megogo.megotest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.megogo.megotest.fragments.MovieDetailsFragment;
import test.megogo.megotest.fragments.TopMoviesFragment;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.presenters.RootViewPresenter;
import test.megogo.megotest.mvp.views.RootView;

public class MovieActivity extends MvpAppCompatActivity implements RootView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @InjectPresenter
    RootViewPresenter rootViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showTopMoviesContainer() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() == 0) {
            replaceFragment(new TopMoviesFragment());
        }
    }

    private void replaceFragment(final Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showMovieDetailsContainer(final Movie movie) {
        replaceFragment(MovieDetailsFragment.createInstance(movie));
    }
}
