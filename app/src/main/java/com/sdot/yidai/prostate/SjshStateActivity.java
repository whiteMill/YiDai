package com.sdot.yidai.prostate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.beidou.RequestEduActivity;
import com.sdot.yidai.model.OrderwdsjshVo;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.ui.ApplydataActivity;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.weight.MutiProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SjshStateActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.total_money)
    TextView totalMoney;
    @BindView(R.id.guarantee_money)
    TextView guaranteeMoney;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.mp_1)
    MutiProgress mp1;
    @BindView(R.id.mark_name)
    TextView markName;
    @BindView(R.id.chushentongguo)
    TextView chushentongguo;
    @BindView(R.id.chushentongguo_time)
    TextView chushentongguoTime;
    @BindView(R.id.tijiao_time)
    TextView tijiaoTime;
    @BindView(R.id.shehezhong_time)
    TextView shehezhongTime;
    private Intent intent;
    private OrderwdsjshVo orderwdsjshVo;
    private boolean isMaterials = false;
    private static final String TAG = "SjshStateActivity";
    Dialog dialog;
    ProductStateVo.SjshVo sjshVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjsh_state);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        title.setText("随借随还");
        intent = getIntent();
        sjshVo = (ProductStateVo.SjshVo) intent.getSerializableExtra("sjshVo");
        dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
        getHaveProduct();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getHaveProduct();
            }
        });
        refreshLayout.setEnableLoadmore(false);
    }

    @OnClick({R.id.back,R.id.mark_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                EventBus.getDefault().post(new MessageEvent("refresh"));
                break;
            case R.id.mark_name:
                if (isMaterials) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderwdsjshVo", orderwdsjshVo);//序列化
                    startActivity(new Intent(this, ApplydataActivity.class).putExtras(bundle));
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderwdsjshVo", orderwdsjshVo);//序列化
                    startActivity(new Intent(this, RequestEduActivity.class).putExtras(bundle));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "state_refresh":
                getProductState(orderwdsjshVo.getId()+"");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("refresh"));
    }

    private void changeState(String state) {
        switch (state) {
            //禁用
            case "DISABLED":
                break;
            //已删除
            case "DELETED":
                break;
            //贝兜通过
            case "ENABLED":
                mp1.setCurrNodeNO(3);
                mp1.setCurrNodeState(1);
                mp1.resetView();
                markName.setVisibility(View.INVISIBLE);
                startActivity(new Intent(this,SjshCardActivity.class));
                this.finish();
                break;
            //待审核
            case "CREATED":
                mp1.setCurrNodeNO(1);
                mp1.setCurrNodeState(1);
                mp1.resetView();
                markName.setVisibility(View.GONE);
                chushentongguoTime.setVisibility(View.GONE);
                tijiaoTime.setText(orderwdsjshVo.getCreatedAt().split("T")[0]);
                shehezhongTime.setText(orderwdsjshVo.getLastModifiedAt().split("T")[0]);
                break;
            //物流钱庄通过
            case "ADOPT":
                mp1.setCurrNodeNO(2);
                mp1.setCurrNodeState(1);
                mp1.resetView();
                chushentongguo.setText("初审通过");
                markName.setText("请签约资方");
                isMaterials = false;
                markName.setVisibility(View.VISIBLE);
                chushentongguoTime.setVisibility(View.VISIBLE);
                chushentongguoTime.setText(orderwdsjshVo.getLastModifiedAt().split("T")[0]);
                tijiaoTime.setText(orderwdsjshVo.getCreatedAt().split("T")[0]);
                shehezhongTime.setText(orderwdsjshVo.getLastModifiedAt().split("T")[0]);
                break;
            //用户已签约
            case "SIGNED":
                mp1.setCurrNodeNO(2);
                mp1.setCurrNodeState(1);
                mp1.resetView();
                chushentongguo.setText("初审通过");
                markName.setVisibility(View.VISIBLE);
                chushentongguoTime.setVisibility(View.VISIBLE);
                chushentongguoTime.setText(orderwdsjshVo.getLastModifiedAt().split("T")[0]);
                tijiaoTime.setText(orderwdsjshVo.getCreatedAt().split("T")[0]);
                shehezhongTime.setText(orderwdsjshVo.getLastModifiedAt().split("T")[0]);
                break;
            //用户已提交贝兜
            case "SUBMISSION":
                break;
            //贝兜未通过
            case "NOTPASS":
                break;
            //物流钱庄未通过
            case "DENIED":
                mp1.setCurrNodeNO(2);
                mp1.setCurrNodeState(0);
                mp1.resetView();
                chushentongguo.setText("初审未通过");
                markName.setText("申请资料");
                isMaterials = true;
                markName.setVisibility(View.VISIBLE);
                chushentongguoTime.setVisibility(View.VISIBLE);
                chushentongguoTime.setText(orderwdsjshVo.getLastModifiedAt().split("T")[0]);
                tijiaoTime.setText(orderwdsjshVo.getCreatedAt().split("T")[0]);
                shehezhongTime.setText(orderwdsjshVo.getLastModifiedAt().split("T")[0]);
                break;
        }
    }

    private void getHaveProduct() {
        OkGo.<String>get(URL.HAVE_PRODUCT).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderwdsjshes").getJSONObject(0);
                            orderwdsjshVo = new Gson().fromJson(orderJSONObject.toString(), OrderwdsjshVo.class);
                            totalMoney.setText(ToolUtils.formatStringNumber(orderwdsjshVo.getApplyAmount() + ""));
                            guaranteeMoney.setText(ToolUtils.formatDoubleNumber(Double.valueOf(orderwdsjshVo.getApplyAmount() + "") * 0.05));
                            time.setText(orderwdsjshVo.getCreatedAt().split("T")[0]);
                            getProductState(orderwdsjshVo.getId() + "");
                            YDaiApplication.getInstance().setProductId(orderwdsjshVo.getId() + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoadingDialog.closeDialog(dialog);
                            mp1.setCurrNodeState(1);
                            mp1.setCurrNodeState(1);
                            mp1.resetView();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        LoadingDialog.closeDialog(dialog);
                        mp1.setCurrNodeState(1);
                        mp1.setCurrNodeState(1);
                        mp1.resetView();
                    }
                });
    }

    private void getProductState(String id) {
        OkGo.<String>get("http://test.edairisk.com/rest/orderwdsjshes/" + id + "/state").tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            String changeState = jsonObject.getString("stateCode");
                            String lastModifiedAt = jsonObject.getString("lastModifiedAt");
                            changeState(changeState);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mp1.setCurrNodeState(1);
                            mp1.setCurrNodeState(1);
                            mp1.resetView();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        mp1.setCurrNodeState(1);
                        mp1.setCurrNodeState(1);
                        mp1.resetView();
                    }
                });
    }
}
