package test.megogo.megotest.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.inject.Inject;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import test.megogo.megotest.AppComponent;
import test.megogo.megotest.MovieApp;
import test.megogo.megotest.R;
import test.megogo.megotest.mvp.models.Error;

/**
 * Created by JSJEM on 05.04.2017.
 */

public class ErrorHelper {

    private static final String TAG = "ErrorHelper";

    @Inject
    Retrofit retrofit;

    public static Snackbar createErrorSnackbar(final Activity parent, final String error) {
        return Snackbar.make(ButterKnife.findById(parent, R.id.activity_movie),
                error, Snackbar.LENGTH_INDEFINITE);
    }

    public ErrorHelper() {
        MovieApp.getAppComponent().inject(this);
    }

    public Error extractError(final Throwable exception) {
        Error error = new Error();
        error.setMessage(exception.getMessage());
        if (exception instanceof HttpException) {
            ResponseBody body = ((HttpException) exception).response().errorBody();
            Converter<ResponseBody, Error> errorConverter =
                    retrofit.responseBodyConverter(Error.class, new Annotation[0]);
            try {
                error = errorConverter.convert(body);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return error;
    }
}
