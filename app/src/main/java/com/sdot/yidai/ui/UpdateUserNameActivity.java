package com.sdot.yidai.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.SharedPreferencesUtils;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateUserNameActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.user_name)
    EditText userName;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_name);
        ButterKnife.bind(this);
        title.setText("修改用户名");
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("完成");
        if(YDaiApplication.getInstance().getUserVo().getNickname()==null){

        }else{
            userName.setText(YDaiApplication.getInstance().getUserVo().getNickname());
            userName.setSelection(YDaiApplication.getInstance().getUserVo().getNickname().length());
        }
    }

    @OnClick({R.id.back, R.id.title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.title_right:
                if(ToolUtils.CheckEmpty(userName)){
                    ToastUtils.getInstance(UpdateUserNameActivity.this).showMessage("请输入用户名");
                }else{
                    dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
                    updateUserName(userName.getText().toString().trim());
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    private void updateUserName(final String userName){
        Map<String,String> imageMap = new HashMap<>();
        imageMap.put("nickname",userName);
        JSONObject jsonObject  =  new JSONObject(imageMap);

        OkGo.<String>patch(URL.UPDATE_PASS+ YDaiApplication.getInstance().getUserVo().getId()).tag(this)
                .headers("cookie","session="+YDaiApplication.getInstance().getCookieValue())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(UpdateUserNameActivity.this).showMessage("修改成功");
                        YDaiApplication.getInstance().getUserVo().setNickname(userName);
                        SharedPreferencesUtils.setParam(UpdateUserNameActivity.this,"uservo",new Gson().toJson(YDaiApplication.getInstance().getUserVo()));
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(UpdateUserNameActivity.this).showMessage("修改失败");
                    }
                });
    }
}
