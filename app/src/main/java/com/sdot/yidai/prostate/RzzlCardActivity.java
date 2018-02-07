package com.sdot.yidai.prostate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RzzlCardActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rzzl_card);
        ButterKnife.bind(this);
        title.setText("融资租赁");
        intent = getIntent();
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                EventBus.getDefault().post(new MessageEvent("refresh"));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("refresh"));
    }
}
