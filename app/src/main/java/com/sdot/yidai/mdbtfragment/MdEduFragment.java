package com.sdot.yidai.mdbtfragment;


import android.Manifest;
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
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.billfragment.BaseFragment;
import com.sdot.yidai.mdbtfragment.mdmoney.AppMdActivity;
import com.sdot.yidai.mdbtfragment.mdmoney.UpMoneyActivity;
import com.sdot.yidai.model.CreditcardVo;
import com.sdot.yidai.model.MdBorrowRecordVo;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
public class MdEduFragment extends BaseFragment implements OnChartValueSelectedListener, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.total_money)
    TextView totalMoney;
    @BindView(R.id.up_edu)
    TextView upEdu;
    @BindView(R.id.limit_date)
    TextView limitDate;
    @BindView(R.id.use_edu)
    TextView useEdu;
    Unbinder unbinder;

    boolean loadFinish = false;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.shengyu_edu)
    TextView shengyuEdu;
    @BindView(R.id.yuesheng_edu)
    TextView yueshengEdu;
    private static final String TAG = "EduFragment";
    TextView remainTimes;
    private CreditcardVo creditcardVo;
    List<MdBorrowRecordVo> mdBorrowRecordVoList;

    AlertDialog.Builder alert;

    double moneyTotalUser = 0;  //本月已经借款金额

    public MdEduFragment() {
    }

    @Override
    protected void loadData() {
        loadFinish = false;
        getEduAmount();
        getMonthUseMoney();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_md_edu, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        remainTimes = view.findViewById(R.id.remain_times);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadFinish = false;
                getEduAmount();
                getMonthUseMoney();
            }
        });
        refreshLayout.setEnableLoadmore(false);
        if (YDaiApplication.getInstance().getCompanyRole().equals("employee")) {
            upEdu.setVisibility(View.GONE);
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "apply_finish":
                loadFinish = false;
                getEduAmount();
                getMonthUseMoney();
                break;
        }
    }

    //获取当月已经借款
    private void getMonthUseMoney() {
        Log.i(TAG, "getMonthUseMoney: " + YDaiApplication.getInstance().getMdbtId());
        OkGo.<String>get(URL.MANTH_TOTAL_MONEY).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("creditcardId", YDaiApplication.getInstance().getMdbtId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        Log.i(TAG, "onSuccess: " + response.body());
                        try {
                            jsonObject = new JSONObject(response.body());
                            mdBorrowRecordVoList = new Gson().fromJson(new JSONObject(jsonObject.getString("_embedded")).getString("loans"), new TypeToken<List<MdBorrowRecordVo>>() {
                            }.getType());
                            moneyTotalUser = 0;
                            for (int i = 0; i < mdBorrowRecordVoList.size(); i++) {
                                moneyTotalUser = moneyTotalUser + Double.valueOf(mdBorrowRecordVoList.get(i).getPrincipal());
                            }
                            remainTimes.setText("本月剩余可借款次数 " + String.valueOf(3 - mdBorrowRecordVoList.size()));
                            getEduAmount();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mdBorrowRecordVoList = null;
                            remainTimes.setText("本月剩余可借款次数");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mdBorrowRecordVoList = null;
                        Log.i(TAG, "onError: " + response.body());
                        remainTimes.setText("本月剩余可借款次数");
                    }
                });
    }

    private boolean isEleMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        String currentMonth = simpleDateFormat.format(new Date());
        if (currentMonth.equals("11") || currentMonth.equals("12")) {
            return true;
        } else {
            return false;
        }
    }

    private void getEduAmount() {
        OkGo.<String>get(URL.GET_EDU_AMOUNT + YDaiApplication.getInstance().getMdbtId()).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        Log.i(TAG, "onSuccess: " + response.body() + moneyTotalUser);
                        try {
                            creditcardVo = new Gson().fromJson(response.body(), CreditcardVo.class);
                            YDaiApplication.getInstance().setCreditCardId(creditcardVo.getId() + "");
                            loadFinish = true;
                            float total_money = Float.valueOf(creditcardVo.getCreditGrant());
                            float balance_money = Float.valueOf(creditcardVo.getCreditBalance());
                            float use_money = total_money - balance_money;
                            totalMoney.setText(ToolUtils.qianweifenge(total_money));
                            shengyuEdu.setText("剩余额度:" + ToolUtils.formatDoubleNumber(balance_money));
                            useEdu.setText("已用额度 " + ToolUtils.formatDoubleNumber(use_money));
                            if (isEleMonth()) {
                                yueshengEdu.setText("本月剩余额度:" + ToolUtils.formatDoubleNumber((total_money * 0.7 - moneyTotalUser) > balance_money ? balance_money : (total_money * 0.7 - moneyTotalUser)));
                            } else {
                                yueshengEdu.setText("本月剩余额度:" + ToolUtils.formatDoubleNumber((total_money * 0.5 - moneyTotalUser) > balance_money ? balance_money : (total_money * 0.5 - moneyTotalUser)));
                            }
                            String[] timeArr = creditcardVo.getCreatedAt().split("T");
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = simpleDateFormat.parse(timeArr[0]);
                            limitDate.setText("有效期至 " + getDateThreeMonth(date));
                        } catch (Exception e) {
                            e.printStackTrace();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.up_edu, R.id.borrow_now, R.id.com_kf})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.up_edu:
                if (!loadFinish) {
                    ToastUtils.getInstance(getActivity()).showMessage("请稍后");
                } else {
                    startActivity(new Intent(getActivity(), UpMoneyActivity.class).putExtra("total", ToolUtils.formatStringNumberZero(creditcardVo.getCreditGrant())));
                }
                break;
            case R.id.borrow_now:
                if (!loadFinish) {
                    ToastUtils.getInstance(getActivity()).showMessage("请稍后");
                } else {
                    startActivity(new Intent(getActivity(), AppMdActivity.class).putExtra("creditcardVo", creditcardVo));
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


    private String getDateThreeMonth(Date dNow) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.YEAR, 1);  //设置为前3月
        Date dBefore = calendar.getTime();   //得到后3月的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
        return defaultStartDate;
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
