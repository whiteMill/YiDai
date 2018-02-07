package com.sdot.yidai.dialogfragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SjshDialogFragment extends DialogFragment {

    @BindView(R.id.total_lixi)
    TextView totalLixi;
    Unbinder unbinder;
    @BindView(R.id.yujie_edit)
    EditText yujieEdit;
    @BindView(R.id.meiri_edit)
    TextView meiriEdit;
    @BindView(R.id.dismissBtn)
    TextView dismissBtn;

    private Dialog dialog;
    private String money;

    public SjshDialogFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_sjsh_dialog);
        unbinder = ButterKnife.bind(this, dialog);

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        Bundle bundle =getArguments();
        if(bundle!=null){
            money = bundle.getString("money");
        }
        if(!money.isEmpty()){
            meiriEdit.setText(ToolUtils.formatDoubleNumber(Double.valueOf(money)*0.000417));
            yujieEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(!editable.toString().isEmpty()){
                        if(Integer.valueOf(editable.toString())>90){
                            ToastUtils.getInstance(getActivity()).showMessage("不能超过90天");
                            yujieEdit.setText("90");
                            yujieEdit.setSelection("90".length());
                        }else{
                            totalLixi.setText("合计利息:"+ToolUtils.formatDoubleNumber(Integer.valueOf(editable.toString())*Double.valueOf(meiriEdit.getText().toString())));
                        }
                    }else{
                        totalLixi.setText("合计利息:0.00");
                    }
                }
            });
        }

        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
