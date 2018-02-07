package com.sdot.yidai.beidou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.sdot.yidai.R;
import com.sdot.yidai.model.OrderwdsjshVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.URL;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestEduActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.h5View)
    WebView h5View;

    private Intent intent;
    private OrderwdsjshVo orderwdsjshVo;
    private static final String TAG = "RequestMoneyActivity";
    private StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_money);
        ButterKnife.bind(this);
        title.setText("");
        intent  = getIntent();
        orderwdsjshVo  = (OrderwdsjshVo) intent.getSerializableExtra("orderwdsjshVo");
        stringBuffer.append("zcCode="+"1002&");
        stringBuffer.append("zcName="+"红创金服&");
        stringBuffer.append("name="+orderwdsjshVo.getRealName()+"&");
        stringBuffer.append("mobile="+orderwdsjshVo.getApplyMobile()+"&");
        stringBuffer.append("idcar="+orderwdsjshVo.getApplyIdentityNo()+"&");
        stringBuffer.append("ptproductId="+"f889991e00e811e881babae5a9d2212c"+"&");
        stringBuffer.append("productName="+"快递网点贷&");
        stringBuffer.append("amount="+orderwdsjshVo.getApplyAmount()+"&");
        stringBuffer.append("fileUrl="+"http://files.xiaojinpingtai.com/"+orderwdsjshVo.getZipName());
        h5View.loadUrl(URL.BEND_MONEY+stringBuffer.toString());

        h5View.getSettings().setJavaScriptEnabled(true);
        h5View.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "shouldOverrideUrlLoading: "+url);
                h5View.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick(R.id.back)
    public void onClick() {
        EventBus.getDefault().post(new MessageEvent("state_refresh"));
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("state_refresh"));
    }
}
