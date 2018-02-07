package com.sdot.yidai.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sdot.yidai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyEvaluateActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.serview_image)
    ImageView serviewImage;
    @BindView(R.id.service_name)
    TextView serviceName;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.advice_edittext)
    EditText adviceEdittext;
    @BindView(R.id.submit_button)
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluate);
        ButterKnife.bind(this);
        title.setText("我的评价");
    }

    @OnClick({R.id.back, R.id.submit_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.submit_button:
                break;
        }
    }
}
