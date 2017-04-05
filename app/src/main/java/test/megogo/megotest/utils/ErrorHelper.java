package test.megogo.megotest.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;

import butterknife.ButterKnife;
import test.megogo.megotest.R;

/**
 * Created by JSJEM on 05.04.2017.
 */

public class ErrorHelper {

    public static Snackbar createErrorSnackbar(final Activity parent, final String error, final boolean retry) {
        return Snackbar.make(ButterKnife.findById(parent, R.id.activity_movie),
                error, Snackbar.LENGTH_INDEFINITE);
    }
}
