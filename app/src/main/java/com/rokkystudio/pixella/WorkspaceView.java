package com.rokkystudio.pixella;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

/**
 *  Отображает рабочую область с изображением
 *  Позволяет перемещать и масштабировать изображение
 *  Возвращает координаты взаимодействия с пользователем
 *  Реагирует на изменения изображения в реальном времени
 */
public class WorkspaceView extends View
{
    private final Paint mPatternPaint = new Paint();
    private final Paint mBitmapPaint = new Paint();

    private enum State { NONE, DRAG, ZOOM, FLING, ANIMATE_ZOOM }
    private State mState = State.NONE;

    private Context mContext;
    private Fling mFling;

    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    private GestureDetector.OnDoubleTapListener mOnDoubleTapListener = null;
    private OnTouchListener mUserTouchListener = null;
    private SvgView.OnTouchImageViewListener mTouchImageViewListener = null;

    private Matrix mMatrix = new Matrix();
    private Bitmap mBitmap = null;

    public WorkspaceView(Context context) {
        super(context);
        init(context);
    }

    public WorkspaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WorkspaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setPattern(0xFFAAAAAA, 0xFFEEEEEE, 100);
        mBitmap = Bitmap.createBitmap(256, 128, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawColor(0xFFFFAAFF);
        mContext = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mGestureDetector = new GestureDetector(context, new GestureListener());
        super.setOnTouchListener(new PrivateOnTouchListener());
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        drawPattern(canvas);

        canvas.save();
        if (mMatrix != null) {
            canvas.concat(mMatrix);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
        super.onDraw(canvas);
    }

    @Override
    public Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", normalizedScale);
        bundle.putFloat("matchViewHeight", matchViewHeight);
        bundle.putFloat("matchViewWidth", matchViewWidth);
        bundle.putInt("viewWidth", viewWidth);
        bundle.putInt("viewHeight", viewHeight);
        mMatrix.getValues(m);
        bundle.putFloatArray("matrix", m);
        bundle.putBoolean("imageRendered", imageRenderedAtLeastOnce);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            normalizedScale = bundle.getFloat("saveScale");
            m = bundle.getFloatArray("matrix");
            mPrevMatrix.setValues(m);
            prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            prevViewHeight = bundle.getInt("viewHeight");
            prevViewWidth = bundle.getInt("viewWidth");
            imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }

        super.onRestoreInstanceState(state);
    }

    public void setPattern(int color1, int color2, int size) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color1);
        Paint paint = new Paint();
        paint.setColor(color2);
        canvas.drawRect(size / 2f, 0, size, size / 2f, paint);
        canvas.drawRect(0, size / 2f, size / 2f, size, paint);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mPatternPaint.setShader(shader);
    }

    private void drawPattern(Canvas canvas) {
        canvas.drawPaint(mPatternPaint);
    }

    public void setBitmap(Bitmap bitmap) {

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            if (mOnDoubleTapListener != null) {
                return mOnDoubleTapListener.onSingleTapConfirmed(e);
            }

            if (mOnClickLinkListener != null)
            {
                PointF point = coordTouchToSVG(e.getX(), e.getY());
                String href = getLinkFromPoint(point);

                if (href != null) {
                    mOnClickLinkListener.clickLink(href);
                }
                return true;
            }
            return performClick();
        }

        @Override
        public void onLongPress(MotionEvent e) {
            performLongClick();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if (mFling != null) mFling.cancelFling();
            mFling = new SvgView.Fling((int) velocityX, (int) velocityY);
            postOnAnimation(mFling);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
            boolean consumed = false;
            if (mOnDoubleTapListener != null) {
                consumed = mOnDoubleTapListener.onDoubleTap(e);
            }

            if (mState == State.NONE) {
            }

            return consumed;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            if (mOnDoubleTapListener != null) {
                return mOnDoubleTapListener.onDoubleTapEvent(e);
            }
            return false;
        }
    }

    private class PrivateOnTouchListener implements OnTouchListener
    {
        // Remember last point position for dragging
        private PointF last = new PointF();

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            mScaleDetector.onTouchEvent(event);
            mGestureDetector.onTouchEvent(event);
            PointF curr = new PointF(event.getX(), event.getY());

            if (mState == State.NONE || mState == State.DRAG || mState == State.FLING)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        last.set(curr);
                        if (mFling != null)
                            mFling.cancelFling();
                        mState = State.DRAG;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (mState == State.DRAG) {
                            float deltaX = curr.x - last.x;
                            float deltaY = curr.y - last.y;
                            float fixTransX = getFixDragTrans(deltaX, viewWidth, getImageWidth());
                            float fixTransY = getFixDragTrans(deltaY, viewHeight, getImageHeight());
                            mMatrix.postTranslate(fixTransX, fixTransY);
                            fixTrans();
                            last.set(curr.x, curr.y);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mState = State.NONE;
                        break;
                }
            }

            invalidate();

            // User-defined OnTouchListener
            if (userTouchListener != null) {
                userTouchListener.onTouch(v, event);
            }

            // OnTouchImageViewListener is set: TouchImageView dragged by user.
            if (mOnTouchImageViewListener != null) {
                touchImageViewListener.onMove();
            }

            // indicate event was handled
            return true;
        }
    }

    private class Fling implements Runnable
    {
        Scroller scroller;
        int currX, currY;

        Fling(int velocityX, int velocityY)
        {
            scroller = new Scroller(mContext);
            mMatrix.getValues(m);

            int startX = (int) m[Matrix.MTRANS_X];
            int startY = (int) m[Matrix.MTRANS_Y];
            int minX, maxX, minY, maxY;

            if (getImageWidth() > viewWidth) {
                minX = viewWidth - (int) getImageWidth();
                maxX = 0;

            } else {
                minX = maxX = startX;
            }

            if (getImageHeight() > viewHeight) {
                minY = viewHeight - (int) getImageHeight();
                maxY = 0;

            } else {
                minY = maxY = startY;
            }

            scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            currX = startX;
            currY = startY;
        }

        void cancelFling() {
            if (scroller != null) {
                mState = State.NONE;
                scroller.forceFinished(true);
            }
        }

        @Override
        public void run()
        {
            mState = State.FLING;
            // OnTouchImageViewListener is set: TouchImageView listener has been flung by user.
            // Listener runnable updated with each frame of mFling animation.
            if (touchImageViewListener != null) {
                touchImageViewListener.onMove();
            }

            if (scroller.isFinished()) {
                scroller = null;
                return;
            }

            if (scroller.computeScrollOffset())
            {
                int newX = scroller.getCurrX();
                int newY = scroller.getCurrY();
                int transX = newX - currX;
                int transY = newY - currY;
                currX = newX;
                currY = newY;
                mMatrix.postTranslate(transX, transY);
                fixTrans();
                invalidate();
                postOnAnimation(this);
                return;
            }

            mState = State.NONE;
            invalidate();
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mState = State.ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            scaleImage(detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY(), true);

            // OnTouchImageViewListener is set: TouchImageView pinch zoomed by user.
            if (touchImageViewListener != null) {
                touchImageViewListener.onMove();
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector)
        {
            super.onScaleEnd(detector);
            mState = State.NONE;
            boolean animateToZoomBoundary = false;
            float targetZoom = normalizedScale;
            if (normalizedScale > maxScale) {
                targetZoom = maxScale;
                animateToZoomBoundary = true;

            } else if (normalizedScale < minScale) {
                targetZoom = minScale;
                animateToZoomBoundary = true;
            }

            if (animateToZoomBoundary) {
                SvgView.DoubleTapZoom doubleTap = new SvgView.DoubleTapZoom(targetZoom, viewWidth / 2f, viewHeight / 2f, true);
                postOnAnimation(doubleTap);
            }
        }
    }
}
