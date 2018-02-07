package com.sdot.yidai.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlterPassActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.yuan)
    EditText yuan;
    @BindView(R.id.xin)
    EditText xin;
    @BindView(R.id.re_xin)
    EditText reXin;
    @BindView(R.id.sure)
    Button sure;
    Dialog dialog;

    private static final String TAG = "AlterPassActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_pass);
        ButterKnife.bind(this);
        title.setText("修改登录密码");
    }

    @OnClick({R.id.back, R.id.sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.sure:
                if(ToolUtils.CheckEmpty(xin)||ToolUtils.CheckEmpty(reXin)||ToolUtils.CheckEmpty(yuan)){
                      ToastUtils.getInstance(this).showMessage("请输入密码");
                }else{
                    if(!reXin.getText().toString().trim().equals(xin.getText().toString().trim())){
                        ToastUtils.getInstance(this).showMessage("两次输入密码不一致");
                    }else{
                        if(ToolUtils.Checkpass(xin.getText().toString().trim())){
                            dialog  = LoadingDialog.createLoadingDialog(this,"请稍后");
                            updatePass();
                        }else{
                            ToastUtils.getInstance(this).showMessage("请输入正确的密码格式");
                        }
                    }
                }

                break;
        }
    }

    private void updatePass(){
        Map<String,String> stringMap  =new HashMap<>();
        stringMap.put("password",yuan.getText().toString().trim());
        stringMap.put("newPassword",xin.getText().toString().trim());
        stringMap.put("act","Modifypassword");
        JSONObject jsonObject = new JSONObject(stringMap);

        OkGo.<String>patch(URL.UPDATE_PASS+ YDaiApplication.getInstance().getUserVo().getId()).tag(this)
                .headers("cookie","session="+YDaiApplication.getInstance().getCookieValue())
              /*  .params("password",yuan.getText().toString().trim())
                .params("newPassword",xin.getText().toString().trim())
                .params("act","Modifypassword")*/
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(AlterPassActivity.this).showMessage("密码修改成功");
                        AlterPassActivity.this.finish();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: "+response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(AlterPassActivity.this).showMessage("密码修改失败");
                    }
                });
    }
}
