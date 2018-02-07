package com.sdot.yidai.miandan;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.ui.ApplyDateListActivity;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.ui.LoginActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainDanActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.apply_money)
    TextView applyMoney;

    ProductStateVo productStateVo;
    ProductStateVo.SjshVo sjshVo;
    ProductStateVo.WdxydVo wdxydVo;
    ProductStateVo.RzzlVo rzzlVo;
    ProductStateVo.MdbtVo mdbtVo;
    ProductStateVo.CompanyMDBT companyMDBT;
    Dialog dialog;
    String sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dan);
        ButterKnife.bind(this);
        title.setText("网点面单白条");
    }

    @OnClick({R.id.back, R.id.apply_money})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.apply_money:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
                    getProductSort();
                } else {
                    ToastUtils.getInstance(this).showMessage("请先登录");
                    startActivityForResult(new Intent(this, LoginActivity.class), 0x889);
                }
                break;
        }
    }

    private void getProductSort() {
        OkGo.<String>get(URL.GET_SORT_PRODUCT).tag(this)
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            String errorCode = jsonObject.getString("ErrorCode");
                            if (errorCode.equals("0")) {
                                productStateVo = new Gson().fromJson(jsonObject.getString("data"), ProductStateVo.class);
                                sjshVo = productStateVo.getSjsh();
                                rzzlVo = productStateVo.getRzzl();
                                wdxydVo = productStateVo.getWdxyd();
                                mdbtVo = productStateVo.getMdbt();
                                companyMDBT = productStateVo.getCompanyMDBT();
                            } else {
                                productStateVo = null;
                                ToastUtils.getInstance(MainDanActivity.this).showMessage("网络异常请稍后再试");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            productStateVo = null;
                        }
                        requestMdbt();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        productStateVo = null;
                        requestMdbt();
                    }
                });

    }

    private void requestMdbt(){
        if (productStateVo == null) {
            ToastUtils.getInstance(this).showMessage("网络异常请稍后再试");
        }else if(mdbtVo.getState()!=null){
            ToastUtils.getInstance(this).showMessage("您已经申请过该产品");
            startActivity(new Intent(this, ApplyDateListActivity.class));
        }else if((sjshVo.getState()!=null&&(sjshVo.getState().equals("CREATED")||sjshVo.getState().equals("DENIED")))
                ||
                (rzzlVo.getState()!=null&&(rzzlVo.getState().equals("CREATED")||rzzlVo.getState().equals("DENIED")))
                ||
                (wdxydVo.getState()!=null&&(wdxydVo.getState().equals("CREATED")||wdxydVo.getState().equals("DENIED")))
                ){
            ToastUtils.getInstance(this).showMessage("您有其他产品正在审核中");
        }else{
            if(wdxydVo.getState()!=null){
                sort = "wdxyd";
            }else if(rzzlVo.getState()!=null){
                sort = "rzzlVo";
            }else if(sjshVo.getState()!=null){
                sort = "sjsh";
            } else if(mdbtVo.getState()!=null){
                sort = "mdbt";
            }else{
                sort = "null";
            }
            startActivity(new Intent(this,ApplyDanActivity.class).putExtra("product",sort));
            this.finish();
        }
    }

}
