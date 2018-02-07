package com.sdot.yidai.mdbtfragment.mdback;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import com.sdot.yidai.model.BackPlanVo;
import com.sdot.yidai.model.MdBorrowRecordVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.weight.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MdBackPlanActivity extends BaseActivity {

    private static final String TAG = "MdBackPlanActivity";
    Intent intent;
    String loanId;
    MdBorrowRecordVo mdBorrowRecordVo;
    @BindView(R.id.total_money)
    TextView totalMoney;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;

    Dialog dialog;

    List<BackPlanVo> backPlanVoList = new ArrayList<>();
    List<BackPlanVo> backPlanList = new ArrayList<>();
    @BindView(R.id.planRecyclerView)
    RecyclerView planRecyclerView;
    PlanBaseQuickAdapter planBaseQuickAdapter;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_back_plan);
        ButterKnife.bind(this);
        intent = getIntent();
        title.setText("还款计划");
        mdBorrowRecordVo = (MdBorrowRecordVo) intent.getSerializableExtra("mdBorrowRecordVo");
        totalMoney.setText(ToolUtils.formatStringNumber(mdBorrowRecordVo.getPrincipal()));

        planBaseQuickAdapter = new PlanBaseQuickAdapter(R.layout.back_plan_adater_layour, backPlanVoList);
        planRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        planRecyclerView.setAdapter(planBaseQuickAdapter);
        planRecyclerView.addItemDecoration(new SpacesItemDecoration(20));

        planBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (backPlanList.get(position).getState() == null) {
                    ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("数据异常");
                } else {
                    switch (backPlanVoList.get(position).getState()) {
                        //待出账
                        case "ACCOUNT":
                            ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("该帐单未出账");
                            break;
                        //已出账
                        case "ACCOUNTED":
                            if (dialog != null) {
                                dialog.show();
                            } else {
                                LoadingDialog.createLoadingDialog(MdBackPlanActivity.this, "请稍后");
                            }
                            queryImageIsExist(backPlanVoList.get(position).getId(), backPlanVoList.get(position), position + "");
                         /*   startActivity(new Intent(MdBackPlanActivity.this,PlanApplyDateActivity.class)
                                    .putExtra("backPlan",backPlanVoList.get(position))
                                    .putExtra("loan",mdBorrowRecordVo.getId())
                                    .putExtra("position",position+""));*/
                            break;
                        //已结清
                        case "CLOSED":
                            ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("该帐单已结清");
                            break;
                    }
                }
            }
        });
        dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
        getBackPlan(mdBorrowRecordVo.getId());
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getBackPlan(mdBorrowRecordVo.getId());
            }
        });
        refresh.setEnableLoadmore(false);
    }

    private void getBackPlan(String loanId) {
        OkGo.<String>get(URL.QUERY_MD_BACK_PLAN).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("loanId", loanId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            backPlanList = new Gson().fromJson(new JSONObject(jsonObject.getString("_embedded")).getString("creditrepayplans"), new TypeToken<List<BackPlanVo>>() {
                            }.getType());
                            backPlanVoList.clear();
                            backPlanVoList.addAll(backPlanList);
                            for (int i = 0; i < backPlanList.size(); i++) {
                                getState(backPlanList.get(i).getId(), i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoadingDialog.closeDialog(dialog);
                            if(refresh!=null){
                                refresh.finishRefresh();
                            }
                            backPlanVoList.clear();
                            backPlanVoList.addAll(backPlanList);
                            planBaseQuickAdapter.notifyDataSetChanged();
                            ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("数据异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if(refresh!=null){
                             refresh.finishRefresh();
                        }
                        LoadingDialog.closeDialog(dialog);
                        backPlanVoList.clear();
                        backPlanVoList.addAll(backPlanList);
                        planBaseQuickAdapter.notifyDataSetChanged();
                        ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("请求数据失败");
                    }
                });
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x8852){
            getBackPlan(mdBorrowRecordVo.getId());
        }
    }

    private void queryImageIsExist(String creditrepayplanId, final BackPlanVo backPlanVo, final String clickPosition) {
        OkGo.<String>get(URL.QUERY_BAKC_IMAGE).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("creditrepayplanId", creditrepayplanId)
                .params("stateCode", "CREATED")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray = new JSONArray(new JSONObject(jsonObject.getString("_embedded")).getString("creditrepayments"));
                            if (jsonArray.length() == 0) {
                                startActivityForResult(new Intent(MdBackPlanActivity.this, PlanApplyDateActivity.class)
                                        .putExtra("backPlan", backPlanVo)
                                        .putExtra("loan", mdBorrowRecordVo.getId())
                                        .putExtra("position", clickPosition),0x357);
                            } else {
                                ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("您已提交过审核");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("数据异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: " + response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("网络请求失败");
                    }
                });
    }

    private void getState(String planId, final int position) {
        OkGo.<String>get("http://test.edairisk.com/rest/creditrepayplans/" + planId + "/state").tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        if (position == 2) {
                            if(refresh!=null){
                                refresh.finishRefresh();
                            }
                            LoadingDialog.closeDialog(dialog);
                        }
                        try {
                            jsonObject = new JSONObject(response.body());
                            switch (position) {
                                case 0:
                                    backPlanList.get(0).setState(jsonObject.getString("stateCode"));
                                    break;
                                case 1:
                                    backPlanList.get(1).setState(jsonObject.getString("stateCode"));
                                    break;
                                case 2:
                                    backPlanList.get(2).setState(jsonObject.getString("stateCode"));
                                    break;
                            }
                            if (position == 2) {
                                planBaseQuickAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (position == 2) {
                            LoadingDialog.closeDialog(dialog);
                            if(refresh!=null){
                                refresh.finishRefresh();
                            }
                            backPlanVoList.clear();
                            backPlanVoList.addAll(backPlanList);
                            planBaseQuickAdapter.notifyDataSetChanged();
                            ToastUtils.getInstance(MdBackPlanActivity.this).showMessage("请求数据失败");
                        }

                    }
                });
    }

    class PlanBaseQuickAdapter extends BaseQuickAdapter<BackPlanVo, BaseViewHolder> {

        public PlanBaseQuickAdapter(int layoutResId, @Nullable List<BackPlanVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BackPlanVo item) {
            TextView applyDate = helper.getView(R.id.apply_date);
            TextView appMoney = helper.getView(R.id.apply_money);
            if (item.getState() != null) {
                switch (item.getState()) {
                    //待出账
                    case "ACCOUNT":
                        appMoney.setTextColor(getResources().getColor(R.color.black));
                        applyDate.setTextColor(getResources().getColor(R.color.black));
                        break;
                    //已出账
                    case "ACCOUNTED":
                        appMoney.setTextColor(getResources().getColor(R.color.black));
                        applyDate.setTextColor(getResources().getColor(R.color.black));
                        break;
                    //已结清
                    case "CLOSED":
                        appMoney.setTextColor(getResources().getColor(R.color.default_font_color));
                        applyDate.setTextColor(getResources().getColor(R.color.default_font_color));
                        break;

                }
            }
            helper.setText(R.id.apply_date, item.getDebtorRepaymentDate().substring(0, 11));
            helper.setText(R.id.apply_money, ToolUtils.formatStringNumber(item.getFees()));
            helper.setText(R.id.benjin, ToolUtils.formatStringNumber(item.getPrincipal()));
            helper.setText(R.id.lixi, ToolUtils.formatStringNumber(item.getInterest()));
        }
    }
}
