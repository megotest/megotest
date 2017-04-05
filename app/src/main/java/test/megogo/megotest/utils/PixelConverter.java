package test.megogo.megotest.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Utility for converting pixel to device density points.
 */
public final class PixelConverter {

    private PixelConverter() {
        // empty
    }

    /**
     * Transform density independent pixels to pixels.
     *
     * @param applicationContext Current context.
     * @param densityPoints      Density independent pixels value.
     * @return converted pixels value.
     */
    public static int dpToPx(final Context applicationContext, int densityPoints) {
        DisplayMetrics displayMetrics = applicationContext.getResources().getDisplayMetrics();
        return Math.round(densityPoints * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
