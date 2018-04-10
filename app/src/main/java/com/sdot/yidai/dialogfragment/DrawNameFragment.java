package com.sdot.yidai.dialogfragment;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sdot.yidai.R;
import com.sdot.yidai.utils.ToolUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawNameFragment extends DialogFragment {

    @BindView(R.id.image_draw)
    ImageView imageDraw;
    Unbinder unbinder;
    @BindView(R.id.sure_draw)
    Button sureDraw;
    @BindView(R.id.cancle_draw)
    Button cancleDraw;
    @BindView(R.id.reset_draw)
    Button resetDraw;
    Unbinder unbinder1;
    private Dialog dialog;
    private Bitmap mBitmap;
    private Canvas canvas;
    private Paint paint;
    // 重置按钮
    private Button reset_btn;

    public DrawNameFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_draw_name);
        unbinder = ButterKnife.bind(this, dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        showImage();
        return dialog;
    }

    private void showImage() {
        RelativeLayout.LayoutParams para;
        para = (RelativeLayout.LayoutParams) imageDraw.getLayoutParams();
        // 创建一张空白图片
        mBitmap = Bitmap.createBitmap(ToolUtils.getScreenWidth(getContext()), para.height, Bitmap.Config.ARGB_8888);
        // 创建一张画布
        canvas = new Canvas(mBitmap);
        // 画布背景为白色
        canvas.drawColor(Color.WHITE);
        // 创建画笔
        paint = new Paint();
        // 画笔颜色为蓝色
        paint.setColor(Color.BLACK);
        // 宽度5个像素
        paint.setStrokeWidth(5);
        // 先将白色背景画上
        canvas.drawBitmap(mBitmap, new Matrix(), paint);
        imageDraw.setImageBitmap(mBitmap);

        imageDraw.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 获取手按下时的坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取手移动后的坐标
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        // 在开始和结束坐标间画一条线
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        // 实时更新开始坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        imageDraw.setImageBitmap(mBitmap);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sure_draw, R.id.cancle_draw, R.id.reset_draw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure_draw:
                break;
            case R.id.cancle_draw:
                dialog.dismiss();
                break;
            case R.id.reset_draw:
                imageDraw.setImageBitmap(null);
                showImage();
                break;
        }
    }
}
