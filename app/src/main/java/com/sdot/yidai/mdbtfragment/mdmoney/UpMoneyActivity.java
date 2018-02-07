package com.sdot.yidai.mdbtfragment.mdmoney;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpMoneyActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.change_money)
    EditText changeMoney;
    Dialog dialog;
    Intent intent;
    String oldMoney;

    private static final String TAG = "UpMoneyActivity";
    @BindView(R.id.apply_up)
    Button applyUp;

    String lastThree;
    @BindView(R.id.zero_txt)
    TextView zeroTxt;
    @BindView(R.id.title_right)
    TextView titleRight;
    private boolean isTrue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_money);
        ButterKnife.bind(this);
        title.setText("申请调额");
        intent = getIntent();
        oldMoney = intent.getStringExtra("total");
        changeMoney.setText(oldMoney);
        dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
        queryChangeEduState();
        titleRight.setText("编辑");
        lastThree = oldMoney.substring(oldMoney.length() - 3, oldMoney.length());
        zeroTxt.setText(lastThree);
        changeMoney.setFocusable(false);
    }

    @OnClick({R.id.back, R.id.apply_up, R.id.title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                if (!isTrue) {
                    isTrue = true;
                    zeroTxt.setVisibility(View.VISIBLE);
                    changeMoney.setText(changeMoney.getText().toString().substring(0, changeMoney.getText().toString().length() - 3));
                    changeMoney.setGravity(Gravity.RIGHT);
                    changeMoney.setFocusable(true);
                    changeMoney.setFocusableInTouchMode(true);
                    changeMoney.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                } else {
                    isTrue = false;
                    changeMoney.setFocusable(false);
                    zeroTxt.setVisibility(View.GONE);
                    if(ToolUtils.CheckEmpty(changeMoney)){
                        changeMoney.setText("1" + lastThree);
                    }else{
                        changeMoney.setText(changeMoney.getText().toString().trim() + lastThree);
                    }
                }
                break;
            case R.id.back:
                this.finish();
                break;
            case R.id.apply_up:
               String facyMoney="";
               if(zeroTxt.getVisibility()==View.VISIBLE){
                    facyMoney  =changeMoney.getText().toString().trim()+"000";
               }else{
                    facyMoney  =changeMoney.getText().toString().trim();
               }
                if (Integer.valueOf(facyMoney) <= Integer.valueOf(oldMoney)) {
                    ToastUtils.getInstance(this).showMessage("调整额度不能低于当前额度");
                } else {
                    dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
                    changeTotalMoney(facyMoney);
                }
                break;
        }
    }

    private void changeTotalMoney(String factEdu) {
        OkGo.<String>get(URL.CHANGE_EEDU).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("creditcardId", YDaiApplication.getInstance().getMdbtId())
                .params("quotaAmount", factEdu)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("ErrorCode").equals("0")) {
                                ToastUtils.getInstance(UpMoneyActivity.this).showMessage("提额申请已提交审核");
                                changeMoney.setFocusable(false);
                                applyUp.setBackgroundResource(R.drawable.force_click);
                                applyUp.setClickable(false);
                                titleRight.setVisibility(View.INVISIBLE);
                            } else {
                                ToastUtils.getInstance(UpMoneyActivity.this).showMessage(jsonObject.getString("ErrorInfo"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(UpMoneyActivity.this).showMessage("提额申请失败");
                        }
                        Log.i(TAG, "onSuccess: " + response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onError: " + response.body());
                        ToastUtils.getInstance(UpMoneyActivity.this).showMessage("提额申请失败");
                    }
                });
    }

    //data 0:表示没有申请过提额  1:表示申请过提额
    private void queryChangeEduState() {
        OkGo.<String>post(URL.CHANGE_EEDU_STATE).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("creditcardId", YDaiApplication.getInstance().getMdbtId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("ErrorCode").equals("0")) {
                                if (jsonObject.getString("data").equals("1")) {
                                    changeMoney.setFocusable(false);
                                    applyUp.setBackgroundResource(R.drawable.force_click);
                                    applyUp.setClickable(false);
                                    titleRight.setVisibility(View.INVISIBLE);
                                }else{
                                    titleRight.setVisibility(View.VISIBLE);
                                }
                            } else {
                                changeMoney.setFocusable(false);
                                applyUp.setBackgroundResource(R.drawable.force_click);
                                applyUp.setClickable(false);
                                titleRight.setVisibility(View.INVISIBLE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            changeMoney.setFocusable(false);
                            applyUp.setBackgroundResource(R.drawable.force_click);
                            applyUp.setClickable(false);
                            titleRight.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onError: " + response.body());
                        changeMoney.setFocusable(false);
                        applyUp.setBackgroundResource(R.drawable.force_click);
                        applyUp.setClickable(false);
                        titleRight.setVisibility(View.INVISIBLE);
                    }
                });
    }


}
