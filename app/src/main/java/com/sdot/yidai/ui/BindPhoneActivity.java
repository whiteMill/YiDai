package com.sdot.yidai.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_yzma)
    EditText inputYzma;
    @BindView(R.id.get_yzma)
    TextView getYzma;
    @BindView(R.id.next_step)
    Button nextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        title.setText("绑定新手机");
    }


    @OnClick({R.id.back, R.id.next_step})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.next_step:
                if(ToolUtils.CheckEmpty(inputPhone)||ToolUtils.CheckEmpty(inputYzma)){
                    ToastUtils.getInstance(this).showMessage("请输入手机号码和验证码");
                }else{
                    ToastUtils.getInstance(this).showMessage("重置成功");
                }
                break;
        }
    }
}
