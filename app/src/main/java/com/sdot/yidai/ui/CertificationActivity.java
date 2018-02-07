package com.sdot.yidai.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CertificationActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.ceID)
    EditText ceID;
    @BindView(R.id.certification_now)
    Button certificationNow;
    @BindView(R.id.shou_layout)
    RelativeLayout shouLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        title.setText("实名认证");
    }

    @OnClick(R.id.certification_now)
    public void onClick() {
        if (ToolUtils.CheckEmpty(name) || ToolUtils.CheckEmpty(ceID)) {
            ToastUtils.getInstance(this).showMessage("请填写相关信息");
        } else {
            ToastUtils.getInstance(this).showMessage("认证成功");
        }

    }

    @OnClick(R.id.back)
    public void setBackClick() {
        this.finish();
    }
}
