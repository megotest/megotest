package test.megogo.megotest.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import test.megogo.megotest.utils.PixelConverter;

/**
 * Created by JSJEM on 05.04.2017.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int spaceX;
    private final int spaceY;

    /**
     * Constructor.
     *
     * @param spaceX Item horizontal spacing.
     * @param spaceY Item vertical spacing.
     */
    public SpaceItemDecoration(final int spaceX, final int spaceY) {
        this.spaceX = spaceX;
        this.spaceY = spaceY;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(final Rect outRect, final View view,
                               final RecyclerView parent, final RecyclerView.State state) {
        int spaceXPixels = PixelConverter.dpToPx(view.getContext(), spaceX);
        int spaceYPixels = PixelConverter.dpToPx(view.getContext(), spaceY);
        outRect.left = spaceXPixels;
        outRect.right = spaceXPixels;
        outRect.bottom = spaceYPixels;
        outRect.top = spaceYPixels;
    }
}
