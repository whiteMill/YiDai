package com.sdot.yidai.prostate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.sdot.yidai.miandan.ApplyMdbtDateActivity;
import com.sdot.yidai.model.OrderMdbtVo;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.rongzizl.ApplyRzzlDateActivity;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.weight.MutiProgress;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MdbtStateActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_re_layout)
    RelativeLayout titleReLayout;
    @BindView(R.id.obj_kind_txt)
    TextView objKindTxt;
    @BindView(R.id.obj_kind)
    TextView objKind;
    @BindView(R.id.total_money_txt)
    TextView totalMoneyTxt;
    @BindView(R.id.total_money)
    TextView totalMoney;
    @BindView(R.id.time_txt)
    TextView timeTxt;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.guarantee_txt)
    TextView guaranteeTxt;
    @BindView(R.id.guarantee_money)
    TextView guaranteeMoney;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.textView14)
    TextView textView14;
    @BindView(R.id.mp_1)
    MutiProgress mp1;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.tijiao_time)
    TextView tijiaoTime;
    @BindView(R.id.first_step_layout)
    RelativeLayout firstStepLayout;
    @BindView(R.id.shezhezhong)
    TextView shezhezhong;
    @BindView(R.id.shehezhong_time)
    TextView shehezhongTime;
    @BindView(R.id.second_step_layout)
    RelativeLayout secondStepLayout;
    @BindView(R.id.chushentongguo)
    TextView chushentongguo;
    @BindView(R.id.chushentongguo_time)
    TextView chushentongguoTime;
    @BindView(R.id.third_step_layout)
    RelativeLayout thirdStepLayout;
    @BindView(R.id.mark_name)
    TextView markName;
    @BindView(R.id.Fourth_step_layout)
    RelativeLayout FourthStepLayout;
    @BindView(R.id.fangkuan)
    TextView fangkuan;
    @BindView(R.id.login_complete_layout)
    RelativeLayout loginCompleteLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private Intent intent;
    private ProductStateVo.MdbtVo mdbtVo;
    private OrderMdbtVo orderMdbtVo;
    Dialog dialog;
    private boolean isMaterials = false;

    private static final String TAG = "MdbtStateActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdbt_state);
        ButterKnife.bind(this);
        title.setText("面单白条");
        intent = getIntent();
        mdbtVo = (ProductStateVo.MdbtVo) intent.getSerializableExtra("mdbtVo");
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
        switch (view.getId()){
            case R.id.back:
                EventBus.getDefault().post(new MessageEvent("refresh"));
                this.finish();
                break;
            case R.id.mark_name:
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderMdbtVo", orderMdbtVo);//序列化
                startActivity(new Intent(this, ApplyMdbtDateActivity.class).putExtras(bundle));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(dialog!=null&&dialog.isShowing()){
            OkGo.getInstance().cancelTag(this);
        }else{
            EventBus.getDefault().post(new MessageEvent("refresh"));
        }

    }

    private void ininMp(String state) {
        switch (state) {
            //待审核
            case "CREATED":
                mp1.setCurrNodeNO(1);
                mp1.setCurrNodeState(1);
                mp1.resetView();
                markName.setVisibility(View.GONE);
                chushentongguoTime.setVisibility(View.GONE);
                tijiaoTime.setText(orderMdbtVo.getCreatedAt().split("T")[0]);
                shehezhongTime.setText(orderMdbtVo.getLastModifiedAt().split("T")[0]);
                break;
            //有效
            case "ENABLED":
                mp1.setCurrNodeNO(3);
                mp1.setCurrNodeState(1);
                mp1.resetView();
                chushentongguo.setText("审核通过");
                markName.setVisibility(View.INVISIBLE);
                break;
            //禁用
            case "DISABLED":
                break;
            //删除
            case "DELETED":
                break;
            //未通过
            case "DENIED":
                mp1.setCurrNodeNO(2);
                mp1.setCurrNodeState(0);
                mp1.resetView();
                chushentongguo.setText("审核未通过");
                markName.setText("申请资料");
                isMaterials = true;
                markName.setVisibility(View.VISIBLE);
                chushentongguoTime.setVisibility(View.VISIBLE);
                chushentongguoTime.setText(orderMdbtVo.getLastModifiedAt().split("T")[0]);
                tijiaoTime.setText(orderMdbtVo.getCreatedAt().split("T")[0]);
                shehezhongTime.setText(orderMdbtVo.getLastModifiedAt().split("T")[0]);
                break;
         /*   //通过
            case "ADOPT":
                mp1.setCurrNodeNO(2);
                mp1.setCurrNodeState(1);
                mp1.resetView();
                chushentongguo.setText("初审通过");
                markName.setText("请签约资方");
                isMaterials = false;
                // markName.setVisibility(View.VISIBLE);
                markName.setVisibility(View.INVISIBLE);
                chushentongguoTime.setVisibility(View.VISIBLE);
                chushentongguoTime.setText(orderMdbtVo.getLastModifiedAt().split("T")[0]);
                tijiaoTime.setText(orderMdbtVo.getCreatedAt().split("T")[0]);
                shehezhongTime.setText(orderMdbtVo.getLastModifiedAt().split("T")[0]);
                break;*/
        }
    }

    private void getHaveProduct() {
        OkGo.<String>get(URL.HAVE_MDBT).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onSuccess: "+response.body());
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("ordermdbts").getJSONObject(0);
                            orderMdbtVo = new Gson().fromJson(orderJSONObject.toString(), OrderMdbtVo.class);
                            totalMoney.setText(ToolUtils.formatStringNumber(orderMdbtVo.getApplyAmount()));
                            guaranteeMoney.setText(ToolUtils.formatDoubleNumber(Double.valueOf(orderMdbtVo.getApplyAmount() + "") * 0.05));
                            time.setText(orderMdbtVo.getCreatedAt().split("T")[0]);
                            //YDaiApplication.getInstance().setMdbtId(orderMdbtVo.getId() + "");
                           // ininMp(mdbtVo.getState());
                            getProductState(orderMdbtVo.getId());
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
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        Log.i(TAG, "onError: "+response.body());
                        LoadingDialog.closeDialog(dialog);
                        mp1.setCurrNodeState(1);
                        mp1.setCurrNodeState(1);
                        mp1.resetView();
                    }
                });
    }

    private void getProductState(String id) {
        OkGo.<String>get("http://test.edairisk.com/rest/ordermdbts/" + id + "/state").tag(this)
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
                            ininMp(changeState);
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
