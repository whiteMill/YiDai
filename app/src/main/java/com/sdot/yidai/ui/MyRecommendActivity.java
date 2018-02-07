package com.sdot.yidai.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyRecommendActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mingdanRecyclerView)
    RecyclerView mingdanRecyclerView;
    @BindView(R.id.friendBtn)
    Button friendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend);
        ButterKnife.bind(this);
        title.setText("我的推荐");
    }

    @OnClick({R.id.back, R.id.friendBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.friendBtn:
                break;
        }
    }
}
