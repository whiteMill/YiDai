package com.sdot.yidai.fragment.company;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.adapter.StaffAdapter;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.CompanyInfo;
import com.sdot.yidai.model.Employee;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.weight.ListViewForScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyEditActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.conpanyName)
    TextView conpanyName;
    @BindView(R.id.faren_name)
    TextView farenName;
    @BindView(R.id.company_code)
    TextView companyCode;
    @BindView(R.id.company_state)
    TextView companyState;
    @BindView(R.id.managerList)
    ListViewForScrollView managerList;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_re_layout)
    RelativeLayout titleReLayout;
    @BindView(R.id.textView34)
    TextView textView34;
    @BindView(R.id.textView37)
    TextView textView37;
    @BindView(R.id.textView39)
    TextView textView39;
    @BindView(R.id.company_refresh)
    SmartRefreshLayout companyRefresh;

    Dialog dialog;
    AlertDialog.Builder alert;
    AlertDialog.Builder companyAlert;
    CompanyInfo companyInfo;
    @BindView(R.id.add_staff_layout)
    RelativeLayout addStaffLayout;

    private Intent intent;
    private static final String TAG = "CompanyEditActivity";
    StaffAdapter staffAdapter;
    List<Employee> employeeList = new ArrayList<>();
    List<Employee> employeemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_edit);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        title.setText("公司资料");
        intent = getIntent();
        companyInfo = (CompanyInfo) intent.getSerializableExtra("companyInfo");
        initDate(companyInfo);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("编辑");
        editCompany();
        staffAdapter = new StaffAdapter(CompanyEditActivity.this, employeeList);
        managerList.setAdapter(staffAdapter);
        alert = new AlertDialog.Builder(this);
        getCompanyInfo();
        companyRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getCompanyInfo();
            }
        });
        if (YDaiApplication.getInstance().getCompanyRole() != null) {
            if (YDaiApplication.getInstance().getCompanyRole().equals("manager")) {
                managerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                        alert.setTitle("温馨提示").setMessage("确认要删除么").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog = LoadingDialog.createLoadingDialog(CompanyEditActivity.this, "请稍后");
                                        removeStaff(employeeList.get(position).getUsername());
                                    }
                                }
                        ).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alert.create();
                        alert.show();
                        alert.create();
                        return true;
                    }
                });
            }
        }

        if (YDaiApplication.getInstance().getCompanyRole().equals("null")) {
            titleRight.setVisibility(View.INVISIBLE);
            addStaffLayout.setVisibility(View.GONE);
        } else if (YDaiApplication.getInstance().getCompanyRole().equals("employee")) {
            titleRight.setVisibility(View.INVISIBLE);
            addStaffLayout.setVisibility(View.GONE);
        } else {
            addStaffLayout.setVisibility(View.VISIBLE);
            titleRight.setVisibility(View.VISIBLE);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "company_edit":
                getCompanyInfo();
                break;
        }
    }

    private void editCompany() {
        final String[] sortArr = getResources().getStringArray(R.array.company_edit);
        companyAlert = new AlertDialog.Builder(this);
        companyAlert.setItems(sortArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        if (companyInfo.getState().equals("ENABLED")) {
                            ToastUtils.getInstance(CompanyEditActivity.this).showMessage("公司已通过审核");
                        } else {
                            startActivity(new Intent(CompanyEditActivity.this, EditCompanyActivity.class).putExtra("companyInfo", companyInfo));
                        }
                        break;
                    case 1:
                        startActivity(new Intent(CompanyEditActivity.this, CompanyBorrRecordActivity.class).putExtra("companyId", companyInfo.getId()));
                        break;
                }
            }
        });
    }

    private void initDate(CompanyInfo companyIntro) {
        conpanyName.setText(companyIntro.getCompanyName());
        farenName.setText(companyIntro.getCompanyPersonName());
        companyCode.setText(companyIntro.getCompanyNumber());
        switch (companyIntro.getState()) {
            case "CREATED":
                companyState.setText("审核中");
                break;
            case "ENABLED":
                companyState.setText("审核通过");
                break;
        }
    }

    @OnClick({R.id.title_right, R.id.back,R.id.add_staff_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_staff_layout:
                startActivity(new Intent(CompanyEditActivity.this, AddStaffActivity.class));
                break;
            case R.id.title_right:
                companyAlert.show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (dialog != null && dialog.isShowing()) {
            OkGo.getInstance().cancelTag(this);
        }
    }

    private void getCompanyInfo() {
        OkGo.<String>get(URL.COMPANY_INFO).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (companyRefresh != null) {
                            companyRefresh.finishRefresh();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            String ErrorCode = jsonObject.getString("ErrorCode");
                            if (ErrorCode.equals("0")) {
                                employeemList.clear();
                                companyInfo = new Gson().fromJson(new JSONObject(jsonObject.getString("data")).getString("company"), CompanyInfo.class);
                                employeemList = new Gson().fromJson(new JSONObject(jsonObject.getString("data")).getString("employee"), new TypeToken<List<Employee>>() {
                                }.getType());
                                if (companyInfo != null) {
                                    initDate(companyInfo);
                                    employeeList.clear();
                                    employeeList.addAll(employeemList);
                                    staffAdapter.notifyDataSetChanged();
                                    if (employeeList.size() == 0) {
                                        managerList.setVisibility(View.INVISIBLE);
                                    } else {
                                        managerList.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                ToastUtils.getInstance(CompanyEditActivity.this).showMessage("服务器错误");
                                employeeList.clear();
                                staffAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(CompanyEditActivity.this).showMessage("服务器错误");
                            employeeList.clear();
                            staffAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (companyRefresh != null) {
                            companyRefresh.finishRefresh();
                        }
                        ToastUtils.getInstance(CompanyEditActivity.this).showMessage("数据获取失败");
                        employeeList.clear();
                        staffAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void removeStaff(String staffPhone) {
        OkGo.<String>post(URL.DELETE_STAFF).tag(this)
                .headers("cookie", "seesion=" + YDaiApplication.getInstance().getCookieValue())
                .params("username", staffPhone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("ErrorCode").equals("0")) {
                                ToastUtils.getInstance(CompanyEditActivity.this).showMessage("删除成功");
                                getCompanyInfo();
                            } else {
                                ToastUtils.getInstance(CompanyEditActivity.this).showMessage(jsonObject.getString("ErrorInfo"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(CompanyEditActivity.this).showMessage("删除失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(CompanyEditActivity.this).showMessage("网络请求失败");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }
}
