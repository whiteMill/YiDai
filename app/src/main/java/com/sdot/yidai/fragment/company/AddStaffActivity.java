package com.sdot.yidai.fragment.company;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
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
import com.sdot.yidai.model.LoginFailVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStaffActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.staff_name)
    EditText staffName;
    @BindView(R.id.staff_phone)
    EditText staffPhone;
    @BindView(R.id.yzm)
    EditText yzm;
    @BindView(R.id.get_yzma)
    TextView getYzma;
    private int recLen = 60;

    Dialog dialog;

    private static final String TAG = "AddStaffActivity";


    private boolean isClick = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        ButterKnife.bind(this);
        title.setText("添加员工");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(dialog!=null&&dialog.isShowing()){
            OkGo.getInstance().cancelTag(this);
        }
        EventBus.getDefault().post(new MessageEvent("company_edit"));
    }

    @OnClick({R.id.back, R.id.get_yzma,R.id.addStaffButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                EventBus.getDefault().post(new MessageEvent("company_edit"));
                this.finish();
                break;
            case R.id.get_yzma:
                if(getYzma.getText().equals("点击获取")){
                    if (ToolUtils.checkPhone(staffPhone.getText().toString().trim())) {
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
            case R.id.addStaffButton:
                if(ToolUtils.CheckEmpty(staffName)){
                    ToastUtils.getInstance(getApplicationContext()).showMessage("请输入员工姓名!");
                }else if(ToolUtils.CheckEmpty(staffPhone)){
                    ToastUtils.getInstance(getApplicationContext()).showMessage("请输入员工手机号码!");
                }else if(ToolUtils.CheckEmpty(yzm)){
                    ToastUtils.getInstance(getApplicationContext()).showMessage("请输入验证码!");
                }else{
                    dialog = LoadingDialog.createLoadingDialog(this,"请稍候");
                    addStaff();
                }
                break;
        }
    }


    private void getYzma(){
        OkGo.<String>get(URL.GET_YZM).tag(this)
                .params("mobile", staffPhone.getText().toString().trim())
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

    private void addStaff(){
        OkGo.<String>post(URL.ADD_STAFF).tag(this)
                .headers("cookie","session="+ YDaiApplication.getInstance().getCookieValue())
                .params("username",staffPhone.getText().toString().trim())
                .params("realname",staffName.getText().toString().trim())
                .params("smscode",yzm.getText().toString().trim())
                .params("userid",YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if(jsonObject.getString("ErrorCode").equals("0")){
                                ToastUtils.getInstance(AddStaffActivity.this).showMessage("添加成功");
                                staffPhone.setText("");
                                staffName.setText("");
                                yzm.setText("");
                            }else{
                                ToastUtils.getInstance(AddStaffActivity.this).showMessage(jsonObject.getString("ErrorInfo"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(AddStaffActivity.this).showMessage("添加失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(AddStaffActivity.this).showMessage("添加失败");
                    }
                });
    }


}
