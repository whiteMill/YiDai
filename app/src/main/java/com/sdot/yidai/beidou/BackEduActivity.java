package com.sdot.yidai.beidou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackEduActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backH5View)
    WebView backH5View;

    Intent intent;
    String mobile;

    StringBuffer stringBuffer = new StringBuffer();
    private static final String TAG = "BackEduActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_h5);
        ButterKnife.bind(this);
        title.setText("");
        intent = getIntent();
        mobile = intent.getStringExtra("phone");

        stringBuffer.append("mobile="+mobile+"&");
        stringBuffer.append("zcCode="+"1002");
        backH5View.loadUrl(URL.BEND_BACK_MONEY+stringBuffer.toString());

        backH5View.getSettings().setJavaScriptEnabled(true);
        backH5View.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "shouldOverrideUrlLoading: "+url);
                backH5View.loadUrl(url);
                return true;

            }
        });
    }


    @OnClick(R.id.back)
    public void onClick() {
        this.finish();
        EventBus.getDefault().post(new MessageEvent("beidou_finish"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("beidou_finish"));
    }
}
