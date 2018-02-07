package com.sdot.yidai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

public class EditPassActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_yzma)
    EditText inputYzma;
    @BindView(R.id.next_step)
    TextView nextStep;
    @BindView(R.id.get_yzma)
    TextView getYzma;
    private boolean isClick = false;

    private int recLen = 60;

    private static final String TAG = "EditPassActivity";

    @OnClick(R.id.back)void backClick(){
        this.finish();
    }

    @OnClick(R.id.next_step)void nextStep(){
        if(ToolUtils.CheckEmpty(inputPhone)||ToolUtils.CheckEmpty(inputYzma)){
            ToastUtils.getInstance(this).showMessage("请输入手机号码和验证码");
        }else{
            startActivityForResult(new Intent(this,UpdatePassActivity.class).putExtra("username",inputPhone.getText().toString().trim()).putExtra("smscode",inputYzma.getText().toString().trim()),369);
            //this.finish();
        }
    }

    @OnClick(R.id.get_yzma)void setGetYzma(){
        if(getYzma.getText().equals("点击获取")){
            if(ToolUtils.checkPhone(inputPhone.getText().toString().trim())){
                if(!isClick){
                    getYzma();
                    isClick = true;
                }else{
                    ToastUtils.getInstance(getApplicationContext()).showMessage("请稍后!");
                }
            }else{
                ToastUtils.getInstance(getApplicationContext()).showMessage("请输入正确的手机号码!");
            }
        }else{
            ToastUtils.getInstance(getApplicationContext()).showMessage("请稍后!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==252){
            this.finish();
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            getYzma.setText(recLen+"s");
            if(recLen!=0){
                handler.postDelayed(this, 1000);
            }else{
                getYzma.setText("点击获取");
                recLen = 60;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);
        ButterKnife.bind(this);
        title.setText("修改登录密码");
        inputYzma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    nextStep.setBackgroundResource(R.drawable.log_btn_shape_deep);
                }else{
                    nextStep.setBackgroundResource(R.drawable.log_btn_shape);
                }

            }
        });
    }

    private void getYzma(){
        OkGo.<String>get(URL.GET_YZM).tag(this)
                .params("mobile",inputPhone.getText().toString().trim())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        isClick = false;
                        JSONObject jsonObject = null;
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
}
