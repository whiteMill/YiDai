package com.sdot.yidai.beidou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.sdot.yidai.R;
import com.sdot.yidai.model.CreditcardVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.URL;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BorrowEduActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.h5View)
    WebView h5View;
    Intent intent;
    CreditcardVo creditcardVo;
    String loanAmount;
    StringBuffer stringBuffer = new StringBuffer();
    private static final String TAG = "BorrowEduActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_h5);
        ButterKnife.bind(this);
        title.setText("");
        intent = getIntent();
        creditcardVo = (CreditcardVo) intent.getSerializableExtra("creditcardVo");
        loanAmount = intent.getStringExtra("loanAmount");
        stringBuffer.append("zcCode="+"1002&");
        stringBuffer.append("ptproductId="+"f889991e00e811e881babae5a9d2212c&");
        stringBuffer.append("zcName="+"红创金服&");
        stringBuffer.append("bankType="+"2&");
        stringBuffer.append("mobile="+creditcardVo.getCreditcardIdentity()+"&");
        stringBuffer.append("loanAmount="+loanAmount);
        h5View.loadUrl(URL.BEND_BORROW_MONEY+stringBuffer.toString());

        h5View.getSettings().setJavaScriptEnabled(true);
        h5View.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "shouldOverrideUrlLoading: "+url);
                h5View.loadUrl(url);
                // return super.shouldOverrideUrlLoading(view, url);
                return true;

            }
        });
    }

    @OnClick(R.id.back)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                EventBus.getDefault().post(new MessageEvent("beidou_finish"));
                this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("beidou_finish"));
    }
}
