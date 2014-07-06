package com.ms.square.android.shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * ShadowView.java
 *
 * @author Manabu-GT on 7/6/14.
 */
public class ShadowView extends View {

    private static final String TAG = ShadowView.class.getSimpleName();

    private int mShadowRadius = 5;
    private int mShadowOffsetX = 10;
    private int mShadowOffsetY = 10;

    private float mCornerRadius = 2f;

    private Paint mShadowPaint;
    private Bitmap mShadowBitmap;

    private RectF mShadowRectF;
    private Rect mShadowRect;
    private RectF mTempShadowRectF;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public ShadowView(Context context) {
        super(context);
        init();
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #View(android.content.Context, android.util.AttributeSet, int)
     */
    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Perform inflation from XML and apply a class-specific base style. This
     * constructor of View allows subclasses to use their own base style when
     * they are inflating. For example, a Button class's constructor would call
     * this version of the super class constructor and supply
     * <code>R.attr.buttonStyle</code> for <var>defStyle</var>; this allows
     * the theme's button style to modify all of the base view attributes (in
     * particular its background) as well as the Button class's attributes.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource to apply to this view. If 0, no
     *                     default style will be applied.
     * @see #View(android.content.Context, android.util.AttributeSet)
     */
    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Called by the constructors - sets up the drawing parameters for the drop shadow.
     */
    private void init() {
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setColor(Color.BLACK);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(mShadowRadius, BlurMaskFilter.Blur.NORMAL));
        mShadowPaint.setAlpha((int) (0.7f * 255));

        mTempShadowRectF = new RectF(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mShadowRectF = new RectF(0, 0, getWidth(), getHeight());
        mShadowRect = new Rect(0, 0, getWidth() + 2 * mShadowRadius, getHeight() + 2 * mShadowRadius);

        mShadowBitmap = Bitmap.createBitmap(mShadowRect.width(),
                mShadowRect.height(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mShadowBitmap);
        c.translate(mShadowRadius, mShadowRadius);
        c.drawRoundRect(mShadowRectF, mCornerRadius, mCornerRadius, mShadowPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getLeft() + mShadowOffsetX, getTop() + mShadowOffsetY);
        mTempShadowRectF.right = getWidth();
        mTempShadowRectF.bottom = getHeight();
        canvas.drawBitmap(mShadowBitmap, mShadowRect, mTempShadowRectF, mShadowPaint);
        canvas.restore();
    }
}