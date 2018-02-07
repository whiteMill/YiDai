package com.sdot.yidai.billfragment.excuse;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.beidou.BorrowEduActivity;
import com.sdot.yidai.model.CreditcardVo;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SjshApplyFragment extends Fragment {

    @BindView(R.id.jiekaun_time)
    TextView jiekaunTime;
    @BindView(R.id.time_layout)
    RelativeLayout timeLayout;
    @BindView(R.id.huankaun_qixian)
    TextView huankaunQixian;
    @BindView(R.id.qixian_layout)
    RelativeLayout qixianLayout;
    @BindView(R.id.rixi)
    TextView rixi;
    @BindView(R.id.qixi_layout)
    RelativeLayout qixiLayout;
    @BindView(R.id.tixian_money)
    EditText tixianMoney;
    @BindView(R.id.tixian_layout)
    RelativeLayout tixianLayout;
    @BindView(R.id.button)
    Button button;
    Unbinder unbinder;
    CreditcardVo creditcardVo;
    Dialog dialog;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final String TAG = "SjshApplyFragment";

    public SjshApplyFragment() {

    }

    //待审核 已通过 未通过
    public void initDate(CreditcardVo creditcardVo){
        this.creditcardVo = creditcardVo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sjsh_apply, container, false);
        unbinder = ButterKnife.bind(this, view);
        jiekaunTime.setText(sdf.format(new Date()));
        tixianMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    rixi.setText(ToolUtils.formatDoubleNumber(Double.valueOf(editable.toString())*0.000417)+"元");
                }
            }
        });
        return view;
    }

    //查询待审核和已通过的借款记录
    private void findByTwoStateCode(){
        OkGo.<String>get(URL.TWO_STATE_USER).tag(this)
            .headers("cookie","session="+ YDaiApplication.getInstance().getCookieValue())
            .params("personId",YDaiApplication.getInstance().getUserVo().getPerson().getId())
            .execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.i(TAG, "onSuccess:"+response.body());
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body());
                        JSONArray jsonArray = new JSONArray(new JSONObject(jsonObject.getString("_embedded")).getString("approveLoans"));
                        if(jsonArray.length()==0){
                             updateApplyDan();
                        }else{
                            LoadingDialog.closeDialog(dialog);
                            ToastUtils.getInstance(getActivity()).showMessage("您已提交过借款申请!");
                            onChanageListener.change();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(getActivity()).showMessage("申请失败");
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    LoadingDialog.closeDialog(dialog);
                    Log.i(TAG, "onError: "+response.body());
                    ToastUtils.getInstance(getActivity()).showMessage("请求服务器失败");
            }
            });
    }

    private void updateApplyDan(){
        OkGo.<String>post(URL.UPDATE_EXCEPTION_RECORD).tag(this)
                .headers("cookie","session="+YDaiApplication.getInstance().getCookieValue())
                .params("createDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .params("loanAmount",tixianMoney.getText().toString().trim())
                .params("mobile",creditcardVo.getCreditcardIdentity())
                .params("person.id",YDaiApplication.getInstance().getUserVo().getPerson().getId())
                .params("approveLoanState.id","1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if(jsonObject.getString("ErrorCode").equals("0")){
                                ToastUtils.getInstance(getActivity()).showMessage("申请提交成功");
                                tixianMoney.setText("");
                            }else{
                                ToastUtils.getInstance(getActivity()).showMessage(jsonObject.getString("ErrorInfo"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(getActivity()).showMessage("申请提交失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: "+response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(getActivity()).showMessage("服务器请求失败");
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    interface OnChanageListener{
        void change();
    }

    public OnChanageListener onChanageListener;

    public OnChanageListener getOnChanageListener() {
        return onChanageListener;
    }

    public void setOnChanageListener(OnChanageListener onChanageListener) {
        this.onChanageListener = onChanageListener;
    }

    @OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                if(ToolUtils.CheckEmpty(tixianMoney)){
                    ToastUtils.getInstance(getActivity()).showMessage("请输入金额");
                }else if(Double.valueOf(tixianMoney.getText().toString())<1000){
                    ToastUtils.getInstance(getActivity()).showMessage("提现金额要大于1000元");
                }else  if(Double.valueOf(creditcardVo.getCreditBalance())<Double.valueOf(tixianMoney.getText().toString())){
                    ToastUtils.getInstance(getActivity()).showMessage("提现金额不应大于账户剩余额度");
                }else {
                    dialog =  LoadingDialog.createLoadingDialog(getActivity(),"请稍后");
                    findByTwoStateCode();
                }
                break;
        }
    }

}
