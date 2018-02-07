package com.sdot.yidai.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.EventMessage;
import com.sdot.yidai.model.LoginFailVo;
import com.sdot.yidai.model.UserVo;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.SharedPreferencesUtils;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.close_iamge)
    ImageView closeIamge;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_pass)
    EditText inputPass;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forget_pass)
    TextView forgetPass;
    @BindView(R.id.register)
    TextView register;

    private Dialog loadingDialog;
    private UserVo userVo;

    private static final String TAG = "LoginActivity";

    private static final int RESULT_CODE = 0x25;

    @OnClick({R.id.close_iamge,R.id.forget_pass,R.id.register})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.close_iamge:
                LoginActivity.this.finish();
                break;
            case R.id.forget_pass:
                startActivity(new Intent(this, EditPassActivity.class));
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.login)
    public void onClick() {
        if(inputPhone.getText().toString().trim().isEmpty()||inputPass.getText().toString().trim().isEmpty()){
            ToastUtils.getInstance(this).showMessage("请输入手机号码和密码");
        }else{
            getAllCookies();
            ToolUtils.ClearCookie();
            loadingDialog = LoadingDialog.createLoadingDialog(this,"正在登录");
            login();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(EventMessage event) {
        inputPhone.setText(event.getMessage());
        inputPhone.setSelection(event.getMessage().length());
    }

    private void getAllCookies(){
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        HttpUrl httpUrl = okhttp3.HttpUrl.parse(URL.USER_LOGIN);
        List<Cookie> allCookie = cookieStore.getCookie(httpUrl);
    }

    private String getCookiesByUrl(){
        String cookieValue="";
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        List<Cookie> allCookie = cookieStore.getAllCookie();
        for (Cookie cookie:allCookie) {
            cookieValue = cookie.value();
        }
        return cookieValue;
    }

    private void login(){
        OkGo.<String>post(URL.USER_LOGIN).tag(this)
                .params("username",inputPhone.getText().toString().trim())
                .params("password",inputPass.getText().toString().trim())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(loadingDialog);
                        Log.i(TAG, "onSuccess: "+response.body());
                        JSONObject jsonObject = null;
                        try {
                              jsonObject = new JSONObject(response.body());
                              if(jsonObject.getString("msg").equals("登录成功")){
                                  userVo = new Gson().fromJson(jsonObject.getString("data"), UserVo.class);
                                  if(userVo.getCompanyRole()==null){
                                      YDaiApplication.getInstance().setCompanyRole("null");
                                      SharedPreferencesUtils.setParam(LoginActivity.this,"companyRole","null");
                                  }else{
                                      YDaiApplication.getInstance().setCompanyRole(userVo.getCompanyRole());
                                      SharedPreferencesUtils.setParam(LoginActivity.this,"companyRole",userVo.getCompanyRole());
                                  }
                                  SharedPreferencesUtils.setParam(LoginActivity.this,"login_state","1");
                                  SharedPreferencesUtils.setParam(LoginActivity.this,"uservo",jsonObject.getString("data"));
                                  SharedPreferencesUtils.setParam(LoginActivity.this,"cookie",getCookiesByUrl());
                                  SharedPreferencesUtils.setParam(LoginActivity.this,"cookie_date",String.valueOf(new Date().getTime()));
                                  YDaiApplication.getInstance().setCookieValue(getCookiesByUrl());
                                  YDaiApplication.getInstance().setLoginsState(true);
                                  YDaiApplication.getInstance().setUserVo(userVo);

                                  EventBus.getDefault().post(new MessageEvent("user_login"));
                                  ToastUtils.getInstance(LoginActivity.this).showMessage("登录成功");
                                  initXG();
                                  setResult(RESULT_CODE);
                                  LoginActivity.this.finish();
                              }else{
                                  ToastUtils.getInstance(LoginActivity.this).showMessage("登录失败");
                              }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoginFailVo loginFailVo =  new Gson().fromJson(response.body(),LoginFailVo.class);
                            ToastUtils.getInstance(LoginActivity.this).showMessage(loginFailVo.getErrorInfo());
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        LoadingDialog.closeDialog(loadingDialog);
                        ToastUtils.getInstance(LoginActivity.this).showMessage("服务器请求失败");
                    }
                });
    }

    private void  initXG(){
        XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });

        XGPushManager.registerPush(getApplicationContext(), inputPhone.getText().toString().trim(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.d("TPush", "Account注册成功，设备token为：" +o);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Log.d("TPush", "Account注册成功注册成功，设备token为："+o);
            }
        });
    }
}
