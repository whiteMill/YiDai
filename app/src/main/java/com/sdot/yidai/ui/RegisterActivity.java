package com.sdot.yidai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.model.LoginFailVo;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_yzma)
    EditText inputYzma;
    @BindView(R.id.user_permission)
    TextView userPermission;
    @BindView(R.id.next_step)
    Button nextStep;
    @BindView(R.id.forget_pass)
    TextView forgetPass;
    @BindView(R.id.login_no)
    TextView loginNo;
    @BindView(R.id.get_yzma)
    TextView getYzma;
    @BindView(R.id.user_permissios_check)
    CheckBox userPermissiosCheck;
    private int recLen = 60;
    private static final String TAG = "RegisterActivity";
    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            getYzma.setText(recLen + "s");
            if (recLen != 0) {
                handler.postDelayed(this, 1000);
            } else {
                getYzma.setText("点击获取");
                recLen = 60;
            }
        }
    };

    private void getYzma() {
        OkGo.<String>get(URL.GET_YZM).tag(this)
                .params("mobile", inputPhone.getText().toString().trim())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        isClick = false;
                        try {
                            jsonObject = new JSONObject(response.body());
                            String errored =  jsonObject.getString("errorCode");
                            if(errored.equals("0")){
                                handler.post(runnable);
                                ToastUtils.getInstance(getApplicationContext()).showMessage("验证码已发送!");
                            }else{
                                ToastUtils.getInstance(getApplicationContext()).showMessage("验证码发送失败!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoginFailVo loginFailVo =  new Gson().fromJson(response.body(), LoginFailVo.class);
                            ToastUtils.getInstance(getApplicationContext()).showMessage(loginFailVo.getErrorInfo());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.getInstance(getApplicationContext()).showMessage("验证码发送失败!");
                        isClick = false;
                    }
                });
    }


    @OnClick({R.id.back, R.id.next_step, R.id.login_no, R.id.get_yzma, R.id.user_permission})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.next_step:
                if (ToolUtils.CheckEmpty(inputPhone) || ToolUtils.CheckEmpty(inputYzma)) {
                    ToastUtils.getInstance(this).showMessage("请输入手机号码和验证码");
                } else if (!userPermissiosCheck.isChecked()) {
                    ToastUtils.getInstance(this).showMessage("请同意金融信息服务协议");
                }else{
                    startActivityForResult(new Intent(this, PassActivity.class).putExtra("username", inputPhone.getText().toString().trim()).putExtra("smscode", inputYzma.getText().toString().trim()),890);
                }
                break;
            case R.id.login_no:
                this.finish();
                break;
            case R.id.get_yzma:
                if(getYzma.getText().equals("点击获取")){
                    if (ToolUtils.checkPhone(inputPhone.getText().toString().trim())) {
                        if(!isClick){
                            getYzma();
                            isClick = true;
                        }else{
                            ToastUtils.getInstance(getApplicationContext()).showMessage("请稍后");
                        }
                    } else {
                        ToastUtils.getInstance(getApplicationContext()).showMessage("请输入正确的手机号码!");
                    }
                }else{
                    ToastUtils.getInstance(getApplicationContext()).showMessage("请稍后");
                }
                break;
            case R.id.user_permission:
                startActivity(new Intent(this, UserAgreementActivity.class));
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode==789){
            if(requestCode==890){
                this.finish();
            }
        }
    }
}
