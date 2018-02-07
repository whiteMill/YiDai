package com.sdot.yidai.ui;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.utils.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAgreementActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.h5View)
    WebView h5View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ButterKnife.bind(this);
        title.setText("金融信息服务协议");
        h5View.loadUrl(URL.USER_XIEYI);
    }

    @OnClick(R.id.back)
    public void onClick() {
        this.finish();
    }
}
