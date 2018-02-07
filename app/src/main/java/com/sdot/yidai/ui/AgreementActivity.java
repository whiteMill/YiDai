package com.sdot.yidai.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgreementActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onClick() {
        this.finish();
    }
}
