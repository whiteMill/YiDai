package com.sdot.yidai.rongzizl;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.OrderRzzlVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RongZiSuccessActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backMainBtn)
    Button backMainBtn;
    @BindView(R.id.bindCard)
    Button bindCard;
    Dialog dialog;

    OrderRzzlVo orderRzzlVo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_zi_success);
        ButterKnife.bind(this);
        title.setText("申请成功");
    }

    @OnClick({R.id.back, R.id.backMainBtn, R.id.bindCard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                EventBus.getDefault().post(new MessageEvent("refresh"));
                this.finish();
                break;
            case R.id.backMainBtn:
                EventBus.getDefault().post(new MessageEvent("refresh"));
                this.finish();
                break;
            case R.id.bindCard:
                EventBus.getDefault().post(new MessageEvent("refresh"));
                if(dialog==null){
                    dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
                }else{
                    dialog.show();
                }
                getRzzlProduct();
                break;
        }
    }

    private void getRzzlProduct() {
        OkGo.<String>get(URL.HAVE_RZZL).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderrzzls").getJSONObject(0);
                            orderRzzlVo = new Gson().fromJson(orderJSONObject.toString(), OrderRzzlVo.class);
                            if (!String.valueOf(orderRzzlVo.getId()).isEmpty()){
                                startActivity(new Intent(RongZiSuccessActivity.this, ApplyRzzlDateActivity.class).putExtra("orderRzzlVo",orderRzzlVo));
                                RongZiSuccessActivity.this.finish();
                            }else{
                                ToastUtils.getInstance(RongZiSuccessActivity.this).showMessage("服务器异常");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(RongZiSuccessActivity.this).showMessage("服务器异常");
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(RongZiSuccessActivity.this).showMessage("服务器异常");
                    }
                });
    }
}
