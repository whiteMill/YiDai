package com.sdot.yidai.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePassActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.edit_pass)
    EditText editPass;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.pass_eyes_state)
    ImageView passEyesState;

    @OnClick(R.id.back)
    void backClick() {
        this.finish();
    }

    private boolean isShow = false;
    Dialog dialog;

    String phone;
    String smsCode;

    Intent intent;

    private static final String TAG = "UpdatePassActivity";

    @OnClick(R.id.pass_eyes_state)
    void changeNumberState() {
        if (!isShow) {
            passEyesState.setImageResource(R.mipmap.open_eyes);
            editPass.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
            isShow = true;
            if(!ToolUtils.CheckEmpty(editPass)){
                editPass.setSelection(editPass.getText().toString().trim().length());
            }
        } else {
            isShow = false;
            passEyesState.setImageResource(R.mipmap.close_eyes);
            editPass.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
            if(!ToolUtils.CheckEmpty(editPass)){
                editPass.setSelection(editPass.getText().toString().trim().length());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        ButterKnife.bind(this);
        title.setText("找回登录密码");
        complete.setVisibility(View.VISIBLE);
        complete.setText("完成");
        intent = getIntent();
        phone =intent.getStringExtra("username");
        smsCode =intent.getStringExtra("smscode");
    }

    @OnClick(R.id.complete)
    public void onClick() {
        if(ToolUtils.CheckEmpty(editPass)){
            ToastUtils.getInstance(this).showMessage("请输入密码");
        }else{
            if(ToolUtils.Checkpass(editPass.getText().toString().trim())){
                dialog  = LoadingDialog.createLoadingDialog(this,"请稍后");
                forgetPass();
            }else{
                ToastUtils.getInstance(this).showMessage("密码格式不正确");
            }


        }
    }

    private void forgetPass(){
        OkGo.<String>post(URL.FORGET_PASS).tag(this)
                .params("username",phone)
                .params("smscode",smsCode)
                .params("password",editPass.getText().toString().trim())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(UpdatePassActivity.this).showMessage("密码修改成功");
                        setResult(252);
                        UpdatePassActivity.this.finish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: "+response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(UpdatePassActivity.this).showMessage("密码修改失败");
                    }
                });
    }
}
