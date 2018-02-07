package com.sdot.yidai.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.fragment.editapply.EditOderSjshActivity;
import com.sdot.yidai.fragment.editapply.EditOrderMdbtActivity;
import com.sdot.yidai.fragment.editapply.EditOrderRzzlActivity;
import com.sdot.yidai.fragment.editapply.EditOrderWdxydActivity;
import com.sdot.yidai.miandan.ApplyMdbtDateActivity;
import com.sdot.yidai.model.OrderMdbtVo;
import com.sdot.yidai.model.OrderRzzlVo;
import com.sdot.yidai.model.OrderWdxydVo;
import com.sdot.yidai.model.OrderwdsjshVo;
import com.sdot.yidai.rongzizl.ApplyRzzlDateActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.wangdian.ApplyDianDateActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyDateListActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    Dialog dialog;

    OrderwdsjshVo orderwdsjshVo;
    OrderRzzlVo orderRzzlVo;
    OrderWdxydVo orderWdxydVo;
    OrderMdbtVo orderMdbtVo;
    private AlertDialog.Builder sjBuilder;
    private AlertDialog.Builder wdBuilder;
    private AlertDialog.Builder rzBuilder;
    private AlertDialog.Builder mdBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_date_list);
        ButterKnife.bind(this);
        title.setText("产品分类");
        initSjBuilder();
        initWdBuilder();
        initRzBuilder();
        initMdBuilder();
    }

    private void initSjBuilder() {
        final String[] sortArr = getResources().getStringArray(R.array.edit_sjsh);
        sjBuilder = new AlertDialog.Builder(this);
        sjBuilder.setItems(sortArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        startActivity(new Intent(ApplyDateListActivity.this, EditOderSjshActivity.class).putExtra("orderwdsjshVo",orderwdsjshVo));
                        break;
                    case 1:
                        startActivity(new Intent(ApplyDateListActivity.this, ApplydataActivity.class).putExtra("orderwdsjshVo",orderwdsjshVo));
                        break;

                }
                dialogInterface.dismiss();
            }
        });
    }


    private void initWdBuilder() {
        final String[] sortArr = getResources().getStringArray(R.array.edit_wdxyd);
        wdBuilder = new AlertDialog.Builder(this);
        wdBuilder.setItems(sortArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        startActivity(new Intent(ApplyDateListActivity.this, EditOrderWdxydActivity.class).putExtra("orderwdsjshVo",orderwdsjshVo));
                        break;
                    case 1:
                        startActivity(new Intent(ApplyDateListActivity.this, ApplyDianDateActivity.class).putExtra("orderWdxydVo",orderWdxydVo));
                        break;

                }
                dialogInterface.dismiss();
            }
        });
    }



    private void initRzBuilder() {
        final String[] sortArr = getResources().getStringArray(R.array.edit_rzzl);
        rzBuilder = new AlertDialog.Builder(this);
        rzBuilder.setItems(sortArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        startActivity(new Intent(ApplyDateListActivity.this, EditOrderRzzlActivity.class).putExtra("orderRzzlVo",orderRzzlVo));
                        break;
                    case 1:
                        startActivity(new Intent(ApplyDateListActivity.this, ApplyRzzlDateActivity.class).putExtra("orderRzzlVo",orderRzzlVo));
                        break;

                }
                dialogInterface.dismiss();
            }
        });
    }


    private void initMdBuilder() {
        final String[] sortArr = getResources().getStringArray(R.array.edit_mdbt);
        mdBuilder = new AlertDialog.Builder(this);
        mdBuilder.setItems(sortArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        startActivity(new Intent(ApplyDateListActivity.this, EditOrderMdbtActivity.class).putExtra("orderMdbtVo",orderMdbtVo));
                        break;
                    case 1:
                        startActivity(new Intent(ApplyDateListActivity.this, ApplyMdbtDateActivity.class).putExtra("orderMdbtVo",orderMdbtVo));
                        break;

                }
                dialogInterface.dismiss();
            }
        });
    }



    @OnClick({R.id.back, R.id.suijie_layout, R.id.wangdian_layout, R.id.rongzi_layout,R.id.miandan_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.suijie_layout:
                if(dialog==null){
                    dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
                }else{
                    dialog.show();
                }
                 getHaveProduct();
                break;
            case R.id.wangdian_layout:
                if(dialog==null){
                    dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
                }else{
                    dialog.show();
                }
                getWdxyProduct();
                break;
            case R.id.rongzi_layout:
                if(dialog==null){
                    dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
                }else{
                    dialog.show();
                }
                getRzzlProduct();
                break;
            case R.id.miandan_layout:
                if(dialog==null){
                    dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
                }else{
                    dialog.show();
                }
                getMdbtProduct();
                break;
        }
    }

    private void getHaveProduct() {
        OkGo.<String>get(URL.HAVE_PRODUCT).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderwdsjshes").getJSONObject(0);
                            orderwdsjshVo = new Gson().fromJson(orderJSONObject.toString(), OrderwdsjshVo.class);
                            if (!String.valueOf(orderwdsjshVo.getId()).isEmpty()){
                                sjBuilder.show();
                               // startActivity(new Intent(ApplyDateListActivity.this, ApplydataActivity.class).putExtra("orderwdsjshVo",orderwdsjshVo));
                            }else{
                                ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                    }
                });
    }

    private void getRzzlProduct() {
        OkGo.<String>get(URL.HAVE_RZZL).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderrzzls").getJSONObject(0);
                            orderRzzlVo = new Gson().fromJson(orderJSONObject.toString(), OrderRzzlVo.class);
                            if (!String.valueOf(orderRzzlVo.getId()).isEmpty()){
                                //rzBuilder.show();
                                startActivity(new Intent(ApplyDateListActivity.this, ApplyRzzlDateActivity.class).putExtra("orderRzzlVo",orderRzzlVo));
                            }else{
                                ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                    }
                });
    }

    private void getWdxyProduct() {
        OkGo.<String>get(URL.HAVE_WDXY).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderwdxyds").getJSONObject(0);
                            orderWdxydVo = new Gson().fromJson(orderJSONObject.toString(), OrderWdxydVo.class);
                            if (!String.valueOf(orderWdxydVo.getId()).isEmpty()){
                                //wdBuilder.show();
                                startActivity(new Intent(ApplyDateListActivity.this, ApplyDianDateActivity.class).putExtra("orderWdxydVo",orderWdxydVo));
                            }else{
                                ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                    }
                });
    }

    private void getMdbtProduct() {
        OkGo.<String>get(URL.HAVE_MDBT).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("ordermdbts").getJSONObject(0);
                            orderMdbtVo = new Gson().fromJson(orderJSONObject.toString(), OrderMdbtVo.class);
                            if (!String.valueOf(orderMdbtVo.getId()).isEmpty()){
                                mdBuilder.show();
                                //startActivity(new Intent(ApplyDateListActivity.this, ApplyMdbtDateActivity.class).putExtra("orderMdbtVo",orderMdbtVo));
                            }else{
                                ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyDateListActivity.this).showMessage("您还未申请产品");
                    }
                });
    }
}
