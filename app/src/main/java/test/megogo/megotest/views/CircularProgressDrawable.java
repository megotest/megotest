package test.megogo.megotest.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;




/**
 * Progress drawable similar to Lollipop progress bar circular drawable.
 */
public class CircularProgressDrawable extends Drawable
		implements Animatable {

	private static final int ANGLE_ANIMATOR_DURATION = 2000;
	private static final int SWEEP_ANIMATOR_DURATION = 600;
	private static final int MIN_SWEEP_ANGLE = 30;

	private final RectF fBounds = new RectF();
	private final Interpolator angelInterpolator = new LinearInterpolator();
	private final Interpolator sweepInterpolator = new AccelerateDecelerateInterpolator();

	private ObjectAnimator mObjectAnimatorSweep;
	private ObjectAnimator mObjectAnimatorAngle;
	private boolean mModeAppearing;
	private Paint mPaint;
	private float mCurrentGlobalAngleOffset;
	private float mCurrentGlobalAngle;
	private float mCurrentSweepAngle;
	private float mBorderWidth;
	private boolean mRunning;

	/**
	 * Constructor.
	 *
	 * @param color       Drawable color.
	 * @param borderWidth Border width of the progress circle.
	 */
	public CircularProgressDrawable(int color, float borderWidth) {
		mBorderWidth = borderWidth;

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(borderWidth);
		mPaint.setColor(color);

		setupAnimations();
	}


	private void setupAnimations() {
		mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, "currentGlobalAngle", 360f);
		mObjectAnimatorAngle.setInterpolator(angelInterpolator);
		mObjectAnimatorAngle.setDuration(ANGLE_ANIMATOR_DURATION);
		mObjectAnimatorAngle.setRepeatMode(ValueAnimator.RESTART);
		mObjectAnimatorAngle.setRepeatCount(ValueAnimator.INFINITE);

		mObjectAnimatorSweep = ObjectAnimator.ofFloat(this, "currentSweepAngle",
				360f - MIN_SWEEP_ANGLE * 2);
		mObjectAnimatorSweep.setInterpolator(sweepInterpolator);
		mObjectAnimatorSweep.setDuration(SWEEP_ANIMATOR_DURATION);
		mObjectAnimatorSweep.setRepeatMode(ValueAnimator.RESTART);
		mObjectAnimatorSweep.setRepeatCount(ValueAnimator.INFINITE);
		mObjectAnimatorSweep.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				toggleAppearingMode();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(Canvas canvas) {
		if (isVisible()) {
			float startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset;
			float sweepAngle = mCurrentSweepAngle;
			if (!mModeAppearing) {
				startAngle = startAngle + sweepAngle;
				sweepAngle = 360 - sweepAngle - MIN_SWEEP_ANGLE;
			} else {
				sweepAngle += MIN_SWEEP_ANGLE;
			}
			canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOpacity() {
		return PixelFormat.TRANSPARENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setVisible(final boolean visible, final boolean restart) {
		boolean changed = super.setVisible(visible, restart);
		updateAnimationState(visible, restart);
		return changed;
	}

	private void updateAnimationState(final boolean visible, final boolean restart) {
		if (visible) {
			if (restart) {
				start();
			}
		} else {
			stop();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		if (isRunning()) {
			return;
		}
		mRunning = true;
		mObjectAnimatorAngle.start();
		mObjectAnimatorSweep.start();
		invalidateSelf();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		if (!isRunning()) {
			return;
		}
		mRunning = false;
		mObjectAnimatorAngle.cancel();
		mObjectAnimatorSweep.cancel();
		invalidateSelf();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRunning() {
		return mRunning;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		fBounds.left = bounds.left + mBorderWidth / 2f + .5f;
		fBounds.right = bounds.right - mBorderWidth / 2f - .5f;
		fBounds.top = bounds.top + mBorderWidth / 2f + .5f;
		fBounds.bottom = bounds.bottom - mBorderWidth / 2f - .5f;
	}

	/**
	 * Setter for global angle property.
	 *
	 * @param currentGlobalAngle Value of new global angle.
	 */
	public void setCurrentGlobalAngle(float currentGlobalAngle) {
		mCurrentGlobalAngle = currentGlobalAngle;
		invalidateSelf();
	}

	/**
	 * Getter for global angle property.
	 *
	 * @return value of global angle.
	 */
	public float getCurrentGlobalAngle() {
		return mCurrentGlobalAngle;
	}

	/**
	 * Setter for sweep angle property.
	 *
	 * @param currentSweepAngle Value of new sweep angle.
	 */
	public void setCurrentSweepAngle(float currentSweepAngle) {
		mCurrentSweepAngle = currentSweepAngle;
		invalidateSelf();
	}

	/**
	 * Getter for sweep angel property.
	 *
	 * @return value of sweep angle.
	 */
	public float getCurrentSweepAngle() {
		return mCurrentSweepAngle;
	}

	private void toggleAppearingMode() {
		mModeAppearing = !mModeAppearing;
		if (mModeAppearing) {
			mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360;
		}
	}

}
