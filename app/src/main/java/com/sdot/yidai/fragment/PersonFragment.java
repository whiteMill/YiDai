package com.sdot.yidai.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.fragment.applyrecord.MyApplyRecordActivity;
import com.sdot.yidai.fragment.company.CompanyEditActivity;
import com.sdot.yidai.model.CompanyInfo;
import com.sdot.yidai.model.Employee;
import com.sdot.yidai.model.OrderwdsjshVo;
import com.sdot.yidai.model.UserVo;
import com.sdot.yidai.ui.ApplyDateListActivity;
import com.sdot.yidai.ui.EditUserActivity;
import com.sdot.yidai.ui.LoginActivity;
import com.sdot.yidai.ui.MyEvaluateActivity;
import com.sdot.yidai.ui.MyRecommendActivity;
import com.sdot.yidai.ui.SetActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.SharedPreferencesUtils;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.first_page)
    TextView firstPage;
    @BindView(R.id.logo_layout)
    RelativeLayout logoLayout;
    Unbinder unbinder;
    @BindView(R.id.touxiang_layout)
    RelativeLayout touxiangLayout;
    @BindView(R.id.ziliao_layout)
    RelativeLayout ziliaoLayout;
    @BindView(R.id.tuijian_layout)
    RelativeLayout tuijianLayout;
    @BindView(R.id.pingjia_layout)
    RelativeLayout pingjiaLayout;
    @BindView(R.id.shezhi_layout)
    RelativeLayout shezhiLayout;
    private static final String TAG = "PersonFragment";
    @BindView(R.id.touxiang)
    CircleImageView touxiang;
    @BindView(R.id.weidenglu)
    TextView weidenglu;
    AlertDialog.Builder alert;
    OrderwdsjshVo orderwdsjshVo;
    Dialog dialog;
    @BindView(R.id.company_layout)
    RelativeLayout companyLayout;
    List<Employee> employeemList;
    @BindView(R.id.paddingView)
    View paddingView;

    public PersonFragment() {

    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) paddingView.getLayoutParams();
        layoutParams.height = getStatusBarHeight();
        paddingView.setLayoutParams(layoutParams);
        EventBus.getDefault().register(this);
        if (YDaiApplication.getInstance().isLoginsState()) {
            initUser();
        }
        return view;
    }

    /**
     * 获取当前设备状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "user_login":
                initUser();
                break;
            case "user_exit":
                weidenglu.setText("未登录");
                touxiang.setImageResource(R.drawable.icon_head_yg);
                YDaiApplication.getInstance().setUserVo(new UserVo());
                YDaiApplication.getInstance().setCookieValue("null");
                YDaiApplication.getInstance().setLoginsState(false);
                SharedPreferencesUtils.setParam(getActivity(), "login_state", "0");
                SharedPreferencesUtils.setParam(getActivity(), "uservo", "null");
                SharedPreferencesUtils.setParam(getActivity(), "cookie", "null");
                SharedPreferencesUtils.setParam(getActivity(), "cookie_date", "0");
                break;
            case "user_info":
                initUser();
                break;
        }
    }

    private void initUser() {
        if (YDaiApplication.getInstance().getUserVo().getNickname() == null) {
            weidenglu.setText(YDaiApplication.getInstance().getUserVo().getUsername());
        } else {
            weidenglu.setText(YDaiApplication.getInstance().getUserVo().getNickname());
        }
        if (YDaiApplication.getInstance().getUserVo().getHeadimgurl() == null) {
            touxiang.setImageResource(R.drawable.icon_head_yg);
        } else {
            Glide.with(this).load(YDaiApplication.getInstance().getUserVo().getHeadimgurl()).into(touxiang);
        }
    }

    @OnClick({R.id.myapply_layout, R.id.touxiang_layout, R.id.ziliao_layout, R.id.tuijian_layout, R.id.pingjia_layout, R.id.shezhi_layout, R.id.kefu_txt, R.id.company_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myapply_layout:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    startActivity(new Intent(getActivity(), MyApplyRecordActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.touxiang_layout:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    startActivity(new Intent(getActivity(), EditUserActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.ziliao_layout:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    startActivity(new Intent(getActivity(), ApplyDateListActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.tuijian_layout:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    startActivity(new Intent(getActivity(), MyRecommendActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.pingjia_layout:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    startActivity(new Intent(getActivity(), MyEvaluateActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.shezhi_layout:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    startActivity(new Intent(getActivity(), SetActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.company_layout:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    if (dialog == null) {
                        dialog = LoadingDialog.createLoadingDialog(getActivity(), "请稍后");
                    } else {
                        dialog.show();
                    }
                    getCompanyInfo();
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.kefu_txt:
                initPermission();
                break;
        }
    }

    private void getCompanyInfo() {
        OkGo.<String>get(URL.COMPANY_INFO).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onSuccess: " + response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            String ErrorCode = jsonObject.getString("ErrorCode");
                            if (ErrorCode.equals("0")) {
                                CompanyInfo companyInfo = new Gson().fromJson(new JSONObject(jsonObject.getString("data")).getString("company"), CompanyInfo.class);
                                employeemList = new Gson().fromJson(new JSONObject(jsonObject.getString("data")).getString("employee"), new TypeToken<List<Employee>>() {
                                }.getType());
                                if (companyInfo != null) {
                                    startActivity(new Intent(getActivity(), CompanyEditActivity.class).putExtra("companyInfo", companyInfo));
                                } else {
                                    ToastUtils.getInstance(getActivity()).showMessage("还未创建公司");
                                }
                            } else {
                                ToastUtils.getInstance(getActivity()).showMessage("数据异常");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(getActivity()).showMessage("数据异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(getActivity()).showMessage("服务器请求失败");
                    }
                });
    }


    private void initPermission() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            initDialog();
        } else {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initDialog();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    private void initDialog() {

        alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("提示").setMessage("确定要拨打电话:" + "0571-86720030").setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "057186720030"));
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.create();
        alert.show();

    }

}
