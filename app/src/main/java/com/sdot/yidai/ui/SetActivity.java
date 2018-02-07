package com.sdot.yidai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.UserVo;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.SharedPreferencesUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.tencent.android.tpush.XGPushManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.exit_user)
    Button exitUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        title.setText("设置");
    }

    @OnClick({R.id.back, R.id.shiming_layout, R.id.bank_layout, R.id.phone_layout, R.id.pass_layout, R.id.exit_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.shiming_layout:
                startActivity(new Intent(this,CertificationActivity.class));
                break;
            case R.id.bank_layout:
                break;
            case R.id.phone_layout:
                startActivity(new Intent(this,BindPhoneActivity.class));
                break;
            case R.id.pass_layout:
                startActivity(new Intent(this,AlterPassActivity.class));
                break;
            case R.id.exit_user:
                ToolUtils.ClearCookie();
                EventBus.getDefault().post(new MessageEvent("user_exit"));
                SharedPreferencesUtils.setParam(SetActivity.this,"login_state","0");
                SharedPreferencesUtils.setParam(SetActivity.this,"cookie","null");
                SharedPreferencesUtils.setParam(SetActivity.this,"uservo","null");
                SharedPreferencesUtils.setParam(SetActivity.this,"companyRole","null");

                YDaiApplication.getInstance().setUserVo(new UserVo());
                YDaiApplication.getInstance().setCookieValue("null");
                YDaiApplication.getInstance().setLoginsState(false);
                YDaiApplication.getInstance().setMdbtId(null);
                YDaiApplication.getInstance().setCreditCardId(null);
                YDaiApplication.getInstance().setWdxydId(null);
                YDaiApplication.getInstance().setRzzlId(null);
                YDaiApplication.getInstance().setCompanyRole("null");
                XGPushManager.unregisterPush(this);
                this.finish();
                break;
        }
    }
}
