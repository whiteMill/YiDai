package com.sdot.yidai.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.model.EventMessage;
import com.sdot.yidai.model.LoginFailVo;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PassActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.pass_input)
    EditText passInput;
    @BindView(R.id.re_pass_input)
    EditText rePassInput;
    @BindView(R.id.complete)
    Button complete;
    @BindView(R.id.eyes_state)
    ImageView eyesState;

    private Intent intent;
    private boolean isShow = false;
    private String user_name;
    private String yanzm;
    private Dialog dialogLoading;
    private static final String TAG = "PassActivity";

    @OnClick(R.id.back)
    void backClick() {
        PassActivity.this.finish();
    }

    @OnClick(R.id.eyes_state)
    void eyeClick() {
        if (!isShow) {
            eyesState.setImageResource(R.mipmap.open_eyes);
            passInput.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
            rePassInput.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
            isShow = true;
            if(!ToolUtils.CheckEmpty(passInput)){
                passInput.setSelection(passInput.getText().toString().trim().length());
            }
            if(!ToolUtils.CheckEmpty(rePassInput)){
                rePassInput.setSelection(rePassInput.getText().toString().trim().length());
            }
        } else {
            isShow = false;
            eyesState.setImageResource(R.mipmap.close_eyes);

            passInput.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
            rePassInput.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
            if(!ToolUtils.CheckEmpty(passInput)){
                passInput.setSelection(passInput.getText().toString().trim().length());
            }
            if(!ToolUtils.CheckEmpty(rePassInput)){
                rePassInput.setSelection(rePassInput.getText().toString().trim().length());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        ButterKnife.bind(this);
        title.setText("设置密码");
        intent = getIntent();
        user_name = intent.getStringExtra("username");
        yanzm = intent.getStringExtra("smscode");
    }

    @OnClick(R.id.complete)
    public void onClick() {
        if(passInput.getText().toString().trim().isEmpty()||rePassInput.getText().toString().trim().isEmpty()){
            ToastUtils.getInstance(this).showMessage("请输入密码");
        }else{
            if(passInput.getText().toString().trim().equals(rePassInput.getText().toString().trim())){
                if(ToolUtils.Checkpass(passInput.getText().toString().trim())){
                    dialogLoading = LoadingDialog.createLoadingDialog(this,"请稍后");
                    ToolUtils.ClearCookie();
                    register();
                }else{
                    ToastUtils.getInstance(this).showMessage("密码格式不正确");
                }

            }else{
                ToastUtils.getInstance(this).showMessage("两次输入的密码不一致");
            }
        }
    }

    private void register(){
        OkGo.<String>post(URL.USER_REGISTER).tag(this)
                .params("username",user_name)
                .params("smscode",yanzm)
                .params("password",passInput.getText().toString().trim())
                .params("passwordAgain",rePassInput.getText().toString().trim())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialogLoading);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject  = new JSONObject(response.body());
                            if(jsonObject.getString("msg").equals("注册成功")){
                                ToastUtils.getInstance(PassActivity.this).showMessage("注册成功");
                                setResult(789);
                                EventMessage eventMessage = new EventMessage();
                                eventMessage.setMessage(user_name);
                                EventBus.getDefault().post(eventMessage);
                                PassActivity.this.finish();
                            }else{
                                ToastUtils.getInstance(PassActivity.this).showMessage("注册失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoginFailVo resultModel =  new Gson().fromJson(response.body(), LoginFailVo.class);
                            ToastUtils.getInstance(PassActivity.this).showMessage(resultModel.getErrorInfo());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LoadingDialog.closeDialog(dialogLoading);
                        ToastUtils.getInstance(PassActivity.this).showMessage("注册失败");
                    }

                });
    }
}
