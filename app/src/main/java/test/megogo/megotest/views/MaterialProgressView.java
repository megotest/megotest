package test.megogo.megotest.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import test.megogo.megotest.R;
import test.megogo.megotest.utils.PixelConverter;


/**
 * Material progress view.
 */
public class MaterialProgressView extends View {

	private CircularProgressDrawable mDrawable;

	/**
	 * Constructor.
	 *
	 * @param context      Activity context.
	 */
	public MaterialProgressView(final Context context) {
		this(context, null);
	}

	/**
	 * Constructor.
	 *
	 * @param context      Activity context.
	 * @param attrs        View attributes.
	 */
	public MaterialProgressView(final Context context, final AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Constructor.
	 *
	 * @param context      Activity context.
	 * @param attrs        View attributes.
	 * @param defStyleAttr Default view style.
	 */
	public MaterialProgressView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedValue typedValue = new TypedValue();
		TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
		int color = a.getColor(0, Color.GRAY);
		a.recycle();

		mDrawable = new CircularProgressDrawable(color, PixelConverter.dpToPx(context, 5));
		mDrawable.setCallback(this);
		if(getVisibility() == VISIBLE) {
			mDrawable.start();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onVisibilityChanged(final View changedView, final int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (visibility == VISIBLE) {
			mDrawable.start();
		} else {
			mDrawable.stop();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onSizeChanged(final int width, final int height, int oldw, int oldh) {
		super.onSizeChanged(width, height, oldw, oldh);
		mDrawable.setBounds(0, 0, width, height);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(final Canvas canvas) {
		super.draw(canvas);
		mDrawable.draw(canvas);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean verifyDrawable(final Drawable who) {
		return who == mDrawable || super.verifyDrawable(who);
	}

}
