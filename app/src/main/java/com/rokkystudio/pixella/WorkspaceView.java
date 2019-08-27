package com.rokkystudio.pixella;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 *  Отображает рабочую область с изображением
 *  Позволяет перемещать и масштабировать изображение
 *  Возвращает координаты взаимодействия с пользователем
 *  Реагирует на изменения изображения в реальном времени
 */
public class WorkspaceView extends View
{
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;
    private static final float SUPER_MAX_MULTIPLIER = 1.75f;

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
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPattern(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
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
                        setState(SvgView.State.DRAG);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (mState == SvgView.State.DRAG) {
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
                        setState(SvgView.State.NONE);
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
}
