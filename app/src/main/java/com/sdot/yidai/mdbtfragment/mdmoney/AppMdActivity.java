package com.sdot.yidai.mdbtfragment.mdmoney;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.CreditcardVo;
import com.sdot.yidai.model.MdBorrowRecordVo;
import com.sdot.yidai.model.OrderMdbtVo;
import com.sdot.yidai.model.ProductBean;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//每月总不能超过总额度的50%
//双11双12
//不能超过总额度的70%

public class AppMdActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.shengqing_txt)
    TextView shengqingTxt;
    @BindView(R.id.shiyong_image)
    ImageView shiyongImage;
    @BindView(R.id.caigous_txt)
    EditText caigousTxt;
    @BindView(R.id.jiekuan_detail)
    EditText jiekuanDetail;
    @BindView(R.id.apply_now)
    Button applyNow;
    @BindView(R.id.use_date_layout)
    RelativeLayout useDateLayout;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    SimpleDateFormat secondformat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);

    TimePickerView beginpvTime;
    @BindView(R.id.shiyong_txt)
    TextView shiyongTxt;
    OrderMdbtVo orderMdbtVo;
    ProductBean productBean;
    Dialog dialog;

    private static final String TAG = "AppMdActivity";

    Intent intent;
    CreditcardVo creditcardVo;
    List<MdBorrowRecordVo> mdBorrowRecordVoList;
    double moneyTotalUser = 0;
    @BindView(R.id.name_detail)
    EditText nameDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_md);
        ButterKnife.bind(this);
        title.setText("申请借款");
        intent = getIntent();
        creditcardVo = (CreditcardVo) intent.getSerializableExtra("creditcardVo");
        //getMdbtProduct();
        shengqingTxt.setText(sdf.format(new Date()));
        shiyongTxt.setText(getAfterSevenDay());
        jiekuanDetail.setSelection(jiekuanDetail.getText().length());
        beginpvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                shiyongTxt.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setRangDate(getAfterSeven(), getEndDate())
                .build();
        getAfterSevenTime();
        getMonthUseMoney();
        getLilv();
    }

    @OnClick({R.id.back, R.id.apply_now, R.id.use_date_layout, R.id.lixi_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.use_date_layout:
                beginpvTime.show();
                break;
            case R.id.back:
                EventBus.getDefault().post(new MessageEvent("apply_finish"));
                this.finish();
                break;
            case R.id.apply_now:
                if (ToolUtils.CheckEmpty(jiekuanDetail)) {
                    ToastUtils.getInstance(this).showMessage("请输入借款金额");
                }else {
                        if (mdBorrowRecordVoList == null || productBean == null) {
                            ToastUtils.getInstance(this).showMessage("数据异常");
                        } else {
                            if (isEleMonth()) {
                                if ((moneyTotalUser + Double.valueOf(jiekuanDetail.getText().toString().trim())) / Double.valueOf(creditcardVo.getCreditGrant()) > 0.7) {
                                    ToastUtils.getInstance(this).showMessage("本月额度已用完");
                                } else {
                                    dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
                                    queryHaveYuQi();
                                }
                            } else {
                                if ((moneyTotalUser + Double.valueOf(jiekuanDetail.getText().toString().trim())) / Double.valueOf(creditcardVo.getCreditGrant()) > 0.5) {
                                    ToastUtils.getInstance(this).showMessage("本月额度已用完");
                                } else {
                                    dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
                                    queryHaveYuQi();
                                }
                            }
                        }
                }
                break;
            case R.id.lixi_layout:
                if (ToolUtils.CheckEmpty(jiekuanDetail)) {
                    ToastUtils.getInstance(this).showMessage("请输入借款金额");
                } else {
                    if (productBean == null) {
                        ToastUtils.getInstance(this).showMessage("数据异常");
                    } else {
                        startActivity(new Intent(this, BackPlanActivity.class).putExtra("total", jiekuanDetail.getText().toString().trim())
                                                                                            .putExtra("lilv", productBean.getLoanRate())
                                                                                            .putExtra("date",shiyongTxt.getText().toString()));
                    }
                }
                break;
        }
    }

    private void queryHaveYuQi(){
        OkGo.<String>get(URL.QUERY_HAVE_YUQI).tag(this)
                .headers("cookie","session="+YDaiApplication.getInstance().getCookieValue())
                .params("userid",YDaiApplication.getInstance().getUserVo().getId())
                .params("loanType","ORDERMDBT")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        JSONArray jsonArray = null;
                        try {
                            jsonObject  = new JSONObject(response.body());
                            jsonArray =new JSONArray( new JSONObject(jsonObject.getString("_embedded")).getString("loans"));
                            if(jsonArray.length()==0){
                                AppMdSingle();
                            }else{
                                LoadingDialog.closeDialog(dialog);
                                ToastUtils.getInstance(AppMdActivity.this).showMessage("您有逾期记录,暂时无法借款!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoadingDialog.closeDialog(dialog);
                            ToastUtils.getInstance(AppMdActivity.this).showMessage("借款申请失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(AppMdActivity.this).showMessage("借款申请失败");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("apply_finish"));
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


    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void getAfterSevenTime() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 7);
        beginpvTime.setDate(now);
    }

    private Calendar getAfterSeven() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 7);
        return now;
    }

    private Calendar getEndDate() {
        Calendar now = Calendar.getInstance();
        try {
            now.setTime(sdf.parse("2999-12-12"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return now;
    }

    private String getAfterSevenDay() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 7);
        String defaultStartDate = sdf.format(now.getTime());
        return defaultStartDate;
    }


    //获取当月已经借款金额
    private void getMonthUseMoney() {
        Log.i(TAG, "getMonthUseMoney: " + YDaiApplication.getInstance().getMdbtId());
        OkGo.<String>get(URL.MANTH_TOTAL_MONEY).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("creditcardId", YDaiApplication.getInstance().getMdbtId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        Log.i(TAG, "onSuccess: "+response.body());
                        try {
                            jsonObject = new JSONObject(response.body());
                            mdBorrowRecordVoList = new Gson().fromJson(new JSONObject(jsonObject.getString("_embedded")).getString("loans"), new TypeToken<List<MdBorrowRecordVo>>() {
                            }.getType());
                            moneyTotalUser = 0;
                            for (MdBorrowRecordVo mdBorrowRecordVo : mdBorrowRecordVoList) {
                                moneyTotalUser = moneyTotalUser + Double.valueOf(mdBorrowRecordVo.getPrincipal());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mdBorrowRecordVoList = null;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mdBorrowRecordVoList = null;
                        Log.i(TAG, "onError: "+response.body());
                    }
                });
    }

    //获取产品利率
    private void getLilv() {
        OkGo.<String>get(URL.QUERY_LILV).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        productBean = new Gson().fromJson(response.body(), ProductBean.class);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: " + response.body());
                        productBean = null;
                    }
                });
    }

    private void AppMdSingle() {
       /* Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 7);*/
        String applyDate = sdformat.format(new Date());
        String currentSeco = secondformat.format(new Date());
        String useDate =null;
        try {
             useDate = sdformat.format(sdformat.parse(shiyongTxt.getText().toString()+" "+currentSeco));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "AppMdSingle: "+applyDate+"  "+useDate);
        String totalInterest = ToolUtils.formatDoubleNumber(Double.valueOf(jiekuanDetail.getText().toString().trim()) * Double.valueOf(productBean.getLoanRate())/100 / 12 * 3);
        Map<String, String> appMap = new HashMap<>();
        appMap.put("principal", jiekuanDetail.getText().toString().trim());
        appMap.put("totalInterest", totalInterest);
        appMap.put("applyDate", applyDate);
        appMap.put("useDate", useDate);
        appMap.put("creditcard", "/rest/creditcards/" + YDaiApplication.getInstance().getMdbtId());
        appMap.put("loanType", "ORDERMDBT");
        appMap.put("debtorUser", "/rest/users/" + YDaiApplication.getInstance().getUserVo().getId());
        appMap.put("debtorName",nameDetail.getText().toString().trim());
        appMap.put("act", "create");
        appMap.put("purchasingUnit",caigousTxt.getText().toString().trim());
        final JSONObject jsonObject = new JSONObject(appMap);
        Log.i(TAG, "AppMdSingle: " + jsonObject);
        OkGo.<String>post(URL.APPLY_MD_SINGLE).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onSuccess: "+response.body());
                        if(response.body()==""){
                            EventBus.getDefault().post(new MessageEvent("apply_finish"));
                            ToastUtils.getInstance(AppMdActivity.this).showMessage("申请成功");
                            AppMdActivity.this.finish();
                        }else{
                            JSONObject jsonObject1 = null;
                            try {
                                jsonObject1 = new JSONObject(response.body());
                                if(jsonObject1.getString("ActionStatus").equals("FAIL")){
                                    ToastUtils.getInstance(AppMdActivity.this).showMessage(jsonObject1.getString("ErrorInfo"));
                                }else{
                                    ToastUtils.getInstance(AppMdActivity.this).showMessage("申请失败");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                ToastUtils.getInstance(AppMdActivity.this).showMessage("申请失败");
                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onError: " + response.body());
                        ToastUtils.getInstance(AppMdActivity.this).showMessage("申请失败");
                    }
                });
    }

}
