package cn.hjf.lj.wsyt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class WriteView extends View {

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	private List<Path> mPathList = new ArrayList<>();
	private Path mCurrentPath;

	public WriteView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public WriteView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public WriteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(10);
		mPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mPathList == null || mPathList.isEmpty()) {
			return;
		}

		for (Path p : mPathList) {
			canvas.drawPath(p, mPaint);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mCurrentPath = new Path();
				mPathList.add(mCurrentPath);
				mCurrentPath.moveTo(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_MOVE:
				mCurrentPath.lineTo(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				mCurrentPath = null;
				break;
		}

		invalidate();

		return true;
	}

	public void clear() {
		mPathList.clear();
		invalidate();
	}
}
