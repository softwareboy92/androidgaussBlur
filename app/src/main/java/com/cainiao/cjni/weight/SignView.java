package com.cainiao.cjni.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.cainiao.cjni.R;
import com.cainiao.cjni.tools.CalcUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：created by albert on 2019-12-05 10:49
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class SignView extends View {
    //signView 宽度
    private int width;//控件宽度
    //signView 高度
    private int height;//控件高度
    //签到图标
    private Bitmap mSigined = BitmapFactory.decodeResource(getResources(), R.drawable.icon_signed);
    //未签到图标
    private Bitmap mUnSigined = BitmapFactory.decodeResource(getResources(), R.drawable.icon_unsigned);
    //最后一个图标
    private Bitmap mGiftIcon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_sigin_gift);
    //中心点位置
    private List<Float> mCircleCenterPointPositionList;
    //线段paint
    private Paint mLinesPaint;
    //奖励画笔
    private Paint mTextNumberPaint;
    //日期画笔
    private Paint mTextDayPaint;
    //未完成颜色
    private int mLinsTextColor = ContextCompat.getColor(getContext(), R.color.c_f7b93c);
    //天数颜色
    private int mUnCompletedTextColor = ContextCompat.getColor(getContext(), R.color.c_cccccc);
    //完成的颜色
    private int mCompletedLineColor = ContextCompat.getColor(getContext(), R.color.c_41c961);

    //中心X
    private float centerX;
    //中心Y
    private float centerY;


    public SignView(Context context) {
        super(context);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mCircleCenterPointPositionList = new ArrayList<>();

        //线段画笔
        mLinesPaint = new Paint();
        mLinesPaint.setAntiAlias(true);
        mLinesPaint.setColor(mLinsTextColor);
        mLinesPaint.setStrokeWidth(2);
        mLinesPaint.setStyle(Paint.Style.FILL);

        //number paint
        mTextNumberPaint = new Paint();
        mTextNumberPaint.setAntiAlias(true);
        mTextNumberPaint.setColor(mLinsTextColor);
        mTextNumberPaint.setStyle(Paint.Style.FILL);
        mTextNumberPaint.setTextSize(CalcUtils.sp2px(getContext(), 8f));

        //day paint
        mTextDayPaint = new Paint();
        mTextDayPaint.setAntiAlias(true);
        mTextDayPaint.setColor(mUnCompletedTextColor);
        mTextDayPaint.setStyle(Paint.Style.FILL);
        mTextDayPaint.setTextSize(CalcUtils.sp2px(getContext(), 8f));

        centerX = mSigined.getWidth() / 2;
        centerY = mSigined.getHeight() / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hmodel = MeasureSpec.getMode(heightMeasureSpec);
        if (hmodel == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else if (hmodel == MeasureSpec.AT_MOST) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = CalcUtils.dp2px(getContext(), 100f);
        }
        width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleCenterPointPositionList.clear();
        //第一个点距离父控件左边14.5dp
        float size = mUnSigined.getWidth() / 2;
        mCircleCenterPointPositionList.add(size);
        for (int i = 1; i < 7; i++) {
            size = size + width / 7;
            mCircleCenterPointPositionList.add(size);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        witchClick(x, y);

        return super.onTouchEvent(event);
    }

    /**
     * 点击的哪个
     *
     * @param x
     * @param y
     */
    private void witchClick(float x, float y) {
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            float centerPoint = mCircleCenterPointPositionList.get(i);
            if (centerPoint - centerX >= x && x <= centerPoint + centerX) {
                if (centerPoint - centerY >= y && y <= centerPoint + centerY) {
                    Log.e("wu", "witchClick: " + "点击了" + i);
                    return;
                }
            }
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制文字
        drawText(canvas);
        //绘制图片
        drawDrawables(canvas);
        //绘制奖励
        drawCoins(canvas);
    }

    /**
     * @param canvas
     */
    private void drawCoins(Canvas canvas) {
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            canvas.drawText("第" + i + "天", mCircleCenterPointPositionList.get(i), height / 2 - CalcUtils.dp2px(getContext(), 10), mTextDayPaint);
        }
    }

    /**
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            if (i == 6) {
                canvas.drawText("100LB", mCircleCenterPointPositionList.get(i), height / 2 + CalcUtils.dp2px(getContext(), 50), mTextNumberPaint);
            } else {
                canvas.drawText("10LB", mCircleCenterPointPositionList.get(i), height / 2 + CalcUtils.dp2px(getContext(), 50), mTextNumberPaint);
            }
        }
    }

    /**
     * @param canvas
     */
    private void drawDrawables(Canvas canvas) {
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            if (i == 6) {
                canvas.drawBitmap(mGiftIcon, mCircleCenterPointPositionList.get(i), height / 2, null);
            } else {
                canvas.drawBitmap(mUnSigined, mCircleCenterPointPositionList.get(i), height / 2, null);
            }
        }
    }


}
