package org.mariotaku.twidere.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import org.mariotaku.twidere.util.NyanDrawingHelper;

public class NyanDaydreamView extends View {

	private final InvalidateRunnable mInvalidateRunnable;

	private final NyanDrawingHelper mNyanDrawingHelper;

	public NyanDaydreamView(final Context context) {
		this(context, null);
	}

	public NyanDaydreamView(final Context context, final AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NyanDaydreamView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mNyanDrawingHelper = new DreamViewNyanDrawingHelper(context);
		mInvalidateRunnable = new InvalidateRunnable(this);
	}

	public void setScale(final float scale) {
		mNyanDrawingHelper.setScale(scale);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		post(mInvalidateRunnable);
	}

	@Override
	protected void onDetachedFromWindow() {
		removeCallbacks(mInvalidateRunnable);
		super.onDetachedFromWindow();
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);
		mNyanDrawingHelper.dispatchDraw(canvas);
	}

	@Override
	protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mNyanDrawingHelper.dispatchSizeChanged(w, h);
	}

	private static final class DreamViewNyanDrawingHelper extends NyanDrawingHelper {

		private final int mDisplayHeight;

		public DreamViewNyanDrawingHelper(final Context context) {
			super(context);
			final Resources res = getResources();
			final DisplayMetrics dm = res.getDisplayMetrics();
			mDisplayHeight = dm.heightPixels;
		}

		@Override
		protected int getRainbowYOffset() {
			return mDisplayHeight - getHeight();
		}

	}

	private static final class InvalidateRunnable implements Runnable {

		private final View mView;

		InvalidateRunnable(final View view) {
			mView = view;
		}

		@Override
		public void run() {
			mView.invalidate();
			mView.postDelayed(this, 66);
		}

	}

}
