package com.sdot.yidai.billfragment;


import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.beidou.BackEduActivity;
import com.sdot.yidai.billfragment.excuse.ExceptionUseActivity;
import com.sdot.yidai.model.CreditcardVo;
import com.sdot.yidai.ui.ApplyBorrowActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class EduFragment extends BaseFragment implements OnChartValueSelectedListener, EasyPermissions.PermissionCallbacks {

    Unbinder unbinder;

    @BindView(R.id.total_money)
    TextView totalMoney;
    @BindView(R.id.limit_date)
    TextView limitDate;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    boolean loadFinish = false;
    private static final String TAG = "EduFragment";
    @BindView(R.id.use_edu)
    TextView useEdu;
    @BindView(R.id.late_edu)
    TextView lateEdu;
    @BindView(R.id.late_eedu)
    TextView lateEedu;
    private CreditcardVo creditcardVo;

    AlertDialog.Builder alert;
    Dialog dialog;

    public EduFragment() {
    }


    @Override
    protected void loadData() {
        loadFinish = false;
        getEduAmount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edu, container, false);
        unbinder = ButterKnife.bind(this, view);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadFinish = false;
                getEduAmount();
            }
        });

        refreshLayout.setEnableLoadmore(false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "beidou_finish":
                loadFinish = false;
                getEduAmount();
                break;
        }
    }

    private void getException() {
        OkGo.<String>get(URL.EXCEPTION_USER).tag(this)
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
                            if (jsonObject.getString("ErrorCode").equals("0")) {
                                if (jsonObject.getString("data").equals("ABNORMAL")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("creditcardVo", creditcardVo);
                                    startActivity(new Intent(getActivity(), ExceptionUseActivity.class).putExtras(bundle));
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("creditcardVo", creditcardVo);
                                    startActivity(new Intent(getActivity(), ApplyBorrowActivity.class).putExtras(bundle));
                                }
                            } else {
                                ToastUtils.getInstance(getActivity()).showMessage(jsonObject.getString("ErrorInfo"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(getActivity()).showMessage("暂时无法借款");
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


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void getEduAmount() {
        OkGo.<String>get(URL.GET_EDU_AMOUNT + YDaiApplication.getInstance().getCreditCardId()).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        Log.i(TAG, "onSuccess: " + response.body());
                        try {
                            creditcardVo = new Gson().fromJson(response.body(), CreditcardVo.class);
                            YDaiApplication.getInstance().setCreditCardId(creditcardVo.getId() + "");
                            loadFinish = true;
                            float total_money = Float.valueOf(creditcardVo.getCreditGrant());
                            float balance_money = Float.valueOf(creditcardVo.getCreditBalance());
                            float use_money = total_money - balance_money;
                            totalMoney.setText(ToolUtils.qianweifenge(total_money) + "元");
                            useEdu.setText("已用额度" + ToolUtils.formatDoubleNumber(use_money) + "元");
                            lateEdu.setText("剩余额度 " + ToolUtils.formatDoubleNumber(balance_money) + "元");
                            lateEedu.setText("剩余额度 " + ToolUtils.formatDoubleNumber(balance_money));
                            String[] timeArr = creditcardVo.getCreatedAt().split("T");
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = simpleDateFormat.parse(timeArr[0]);
                            limitDate.setText("有效期至 " + getDateThreeMonth(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (refreshLayout != null) {
                                refreshLayout.finishRefresh();
                            }
                            loadFinish = false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        loadFinish = false;
                    }
                });
    }


    @OnClick({R.id.borrow_now, R.id.back_now, R.id.com_kf})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.borrow_now:
                if (!loadFinish) {
                    ToastUtils.getInstance(getActivity()).showMessage("请稍后");
                } else {
                    dialog = LoadingDialog.createLoadingDialog(getActivity(), "请稍后");
                    getException();
                }

                break;
            case R.id.back_now:
                if (!loadFinish) {
                    ToastUtils.getInstance(getActivity()).showMessage("请稍后");
                } else {
                    startActivity(new Intent(getActivity(), BackEduActivity.class).putExtra("phone", creditcardVo.getCreditcardIdentity()));
                }
                break;
            case R.id.com_kf:
                initPermission();
                break;
        }
    }

    private void initPermission() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            initDialog();
        } else {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
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

    public static String getDateThreeMonth(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 90);
        String defaultStartDate = sdf.format(now.getTime());    //格式化前3月的时间
        return defaultStartDate;
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
