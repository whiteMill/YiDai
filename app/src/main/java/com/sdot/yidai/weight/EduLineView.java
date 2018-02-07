package com.sdot.yidai.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sdot.yidai.R;

/**
 * Created by Administrator on 2017/12/13.
 */

public class EduLineView extends View {

    //第一段线段的画笔
    private Paint mFristPaint;
    //第二段线段的画笔
    private Paint mSecondPaint;
    //线段的高度
    private float lineHeight;
    private Context mContext;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private float mProgress;
    //已用颜色
    private int useColor;
    //未用颜色
    private int unuseColor;
    //线段宽度
    private float width;
    //线段高度
    private float height;

    private static final String TAG = "EduLineView";

    public EduLineView(Context context) {
        super(context);
    }

    public EduLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    public EduLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        Log.i(TAG, "onMeasure: "+width+"=="+height);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.lineView, 0, 0);
        useColor = typeArray.getColor(R.styleable.lineView_useColor,0xFFFFFFFF);
        unuseColor = typeArray.getColor(R.styleable.lineView_unuseColor,0xFFFFFFFF);
    }

    private void initVariable() {
        //已用画笔设置
        mFristPaint = new Paint();
        mFristPaint.setAntiAlias(true);//防锯齿
        mFristPaint.setColor(useColor);
        mFristPaint.setStyle(Paint.Style.FILL);
        //未用画笔设置
        mSecondPaint = new Paint();
        mSecondPaint.setAntiAlias(true);//防锯齿
        mSecondPaint.setColor(unuseColor);
        mSecondPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mFristPaint.setStrokeWidth((float) 20.0);
        mSecondPaint.setStrokeWidth((float) 20.0);
        canvas.drawLine(0,0, (float) (width*mProgress),0,mFristPaint);
        canvas.drawLine((float) (width*mProgress),0,width,0,mSecondPaint);
    }


    public void setmProgress(float mProgress) {
        this.mProgress = mProgress;
    }
}
