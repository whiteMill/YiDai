package com.sdot.yidai.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.gson.Gson;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.fragment.FirstPageFragment;
import com.sdot.yidai.fragment.PersonFragment;
import com.sdot.yidai.fragment.ProductCardFragment;
import com.sdot.yidai.model.UserVo;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.SharedPreferencesUtils;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

//com.sdot.yidai.ui.MainActivity
public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.changeFragment)
    FrameLayout changeFragment;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private FragmentManager fm;
    private FirstPageFragment firstPageFragment;
    private ProductCardFragment productCardFragment;
    private PersonFragment personFragment;
    private int pos = 0;
    private static final String TAG = "MainActivity";
    private final int FRAGMENT_REQUEST_CODE = 0x23;
    private long firstTime=0;
    private String loginState;
    private UserVo userVo;
    private String cookie;
    private String companyRole;
    private PushReceive pushReceive;
    public static final String PUSH_MESSAGE = "com.wl.action.PUSH_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loginState = (String) SharedPreferencesUtils.getParam(this, "login_state", "0");
        if (loginState.equals("1")) {
            YDaiApplication.getInstance().setLoginsState(true);
            initUser();
            initCookie();
            initCompanyRole();
        } else {
            YDaiApplication.getInstance().setLoginsState(false);
        }

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.shouye_select, "首页").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.home_icon_nomal)))
                .addItem(new BottomNavigationItem(R.mipmap.dingdan_select, "账单").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.zh_icon_nomal)))
                .addItem(new BottomNavigationItem(R.mipmap.wode_select, "我的").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.me_icon_nomal)))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        fm = getSupportFragmentManager();
        setSelected(pos);
        EventBus.getDefault().register(this);
        initBroad();
        Configuration config = getResources().getConfiguration();
        int smallestScreenWidth = config.smallestScreenWidthDp;
        Log.i("ssejfis", "onCreate: "+smallestScreenWidth);
    }

    private void initBroad(){
        pushReceive  = new PushReceive();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(PUSH_MESSAGE);
        registerReceiver(pushReceive,intentFilter);
    }

    private class PushReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            startActivity(new Intent(MainActivity.this,NewsCenterActivity.class));
        }
    }

    private void initUser() {
        userVo = new Gson().fromJson((String) SharedPreferencesUtils.getParam(this, "uservo", "null"), UserVo.class);
        YDaiApplication.getInstance().setUserVo(userVo);
        initXG();
    }

    private void initCompanyRole(){
        companyRole = (String) SharedPreferencesUtils.getParam(this, "companyRole", "null");
        YDaiApplication.getInstance().setCompanyRole(companyRole);
    }

    private void initXG() {
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

        XGPushManager.registerPush(getApplicationContext(), userVo.getUsername(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.d("TPush", "Account注册成功，设备token为：" + o);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Log.d("TPush", "Account注册成功注册成功，设备token为：" + o);
            }
        });
    }

    private void initCookie() {
        cookie = (String) SharedPreferencesUtils.getParam(this, "cookie", "null");
        YDaiApplication.getInstance().setCookieValue(cookie);
    }

    @Override
    public void onTabSelected(int position) {
        setSelected(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void setSelected(int position) {
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (firstPageFragment == null) {
                    firstPageFragment = new FirstPageFragment();
                    transaction.add(R.id.changeFragment, firstPageFragment);
                } else {
                    transaction.show(firstPageFragment);
                    firstPageFragment.reFreshDot();
                }
                break;
            case 1:
                if (productCardFragment == null) {
                    productCardFragment = new ProductCardFragment();
                    transaction.add(R.id.changeFragment, productCardFragment);
                } else {
                    transaction.show(productCardFragment);
                    productCardFragment.reFreshView();
                }
                break;
            case 2:
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                    transaction.add(R.id.changeFragment, personFragment);
                } else {
                    transaction.show(personFragment);
                }
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x25){
            if(requestCode ==FRAGMENT_REQUEST_CODE){
              //  billFragment.dialogShow();
            }
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (firstPageFragment != null) {
            fragmentTransaction.hide(firstPageFragment);
        }
        if (personFragment != null) {
            fragmentTransaction.hide(personFragment);
        }
        if(productCardFragment!=null){
            fragmentTransaction.hide(productCardFragment);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        pos = savedInstanceState.getInt("position");
        setSelected(pos);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 记录当前的position
        outState.putInt("position", pos);
        super.onSaveInstanceState(outState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        Log.i(TAG, "onMoonEvent: "+messageEvent.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        XGPushClickedResult result = XGPushManager.onActivityStarted(this);
        if (result != null) {

        }
    }
}
