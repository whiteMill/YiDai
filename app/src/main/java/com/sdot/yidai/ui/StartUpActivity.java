package com.sdot.yidai.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.UserVo;
import com.sdot.yidai.utils.SharedPreferencesUtils;
import com.sdot.yidai.utils.ToastUtils;

import java.util.Date;

public class StartUpActivity extends AppCompatActivity {
    private static final String TAG = "StartUpActivity";
    private String cookie_time;

    private int flag;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x12:
                    if (flag == 1) {
                        Intent intent = new Intent(StartUpActivity.this, GuideActivity.class);
                        startActivity(intent);
                        StartUpActivity.this.finish();
                    } else {
                        autoLogin();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        flag = (int) SharedPreferencesUtils.getParam(this, "guideFlag", 1);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x12);
            }
        }, 500);
    }


     private void autoLogin(){
         cookie_time = (String) SharedPreferencesUtils.getParam(this,"cookie_date","0");
         Log.i(TAG, "autoLogin: "+cookie_time);
         if(!cookie_time.equals("0")){
               if(new Date().getTime()-Long.valueOf(cookie_time)>=100000000){
                   YDaiApplication.getInstance().setUserVo(new UserVo());
                   YDaiApplication.getInstance().setCookieValue("null");
                   YDaiApplication.getInstance().setLoginsState(false);
                   SharedPreferencesUtils.setParam(this, "login_state", "0");
                   SharedPreferencesUtils.setParam(this, "uservo", "null");
                   SharedPreferencesUtils.setParam(this, "cookie", "null");
                   SharedPreferencesUtils.setParam(this, "cookie_date", "0");
                   SharedPreferencesUtils.setParam(this, "companyRole", "null");
                   ToastUtils.getInstance(this).showMessage("登陆过期,请重新登录");
                   autoJump();
               }else{
                   autoJump();
               }
         }else{
             autoJump();
         }
     }

     private void autoJump(){
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                 StartUpActivity.this.finish();
             }
         }, 200);
     }
}
