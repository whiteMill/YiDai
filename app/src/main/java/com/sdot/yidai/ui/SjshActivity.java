package com.sdot.yidai.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.dialogfragment.SjshDialogFragment;
import com.sdot.yidai.model.JsonBean;
import com.sdot.yidai.model.OrderMdbtVo;
import com.sdot.yidai.model.OrderRzzlVo;
import com.sdot.yidai.model.OrderWdxydVo;
import com.sdot.yidai.model.OrderwdsjshVo;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.utils.GetJsonDataUtil;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SjshActivity extends BaseActivity {

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static final String TAG = "SjshActivity";

    Unbinder unbinder;
    Dialog dialog;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.shenqingren)
    EditText shenqingren;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.ic)
    EditText ic;
    @BindView(R.id.wangdian)
    EditText wangdian;
    @BindView(R.id.area)
    EditText area;
    @BindView(R.id.tuijian)
    EditText tuijian;
    @BindView(R.id.qixian_info)
    TextView qixianInfo;
    @BindView(R.id.meiri_shou)
    EditText meiriShou;
    @BindView(R.id.meiri_pai)
    EditText meiriPai;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.address_edittext)
    EditText addressEdittext;
    @BindView(R.id.kuaidi_sort)
    TextView kuaidiSort;
    @BindView(R.id.shurujine)
    EditText shurujine;
    @BindView(R.id.advice_money)
    TextView adviceMoney;
    @BindView(R.id.user_permissios_check)
    CheckBox userPermissiosCheck;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private boolean isLoaded = false;
    private String province;
    private String city;
    private String distinct;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder soreBuilder;

    private OrderRzzlVo orderRzzlVo;
    private OrderWdxydVo orderWdxydVo;
    private OrderwdsjshVo orderwdsjshVo;
    private OrderMdbtVo orderMdbtVo;

    Intent intent;
    private boolean isIc=false;
    String sort;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        //  Toast.makeText(AddAddressActivity.this,"开始解析数据",Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    //Toast.makeText(AddAddressActivity.this,"解析数据成功",Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                /*    introFragment.setProvince(options1Items, options2Items, options3Items);
                    conditionFragment.setProvince(options1Items, options2Items, options3Items);
                    flowFragment.setProvince(options1Items, options2Items, options3Items);*/
                    break;

                case MSG_LOAD_FAILED:
                    // Toast.makeText(AddAddressActivity.this,"解析数据失败",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjsh);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initAlterDialog();
        initSortAlterDialog();
        intent = getIntent();
        sort =  intent.getStringExtra("product");
        if(!sort.equals("null")){
            dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
            initIcCard();
        }else{
            isIc = true;
        }
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        meiriShou.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    double adviceD = Double.valueOf(editable.toString()) * 600;
                    if(adviceD<=2000000){
                        adviceMoney.setText("贷款预估额: " + ToolUtils.formatDoubleNumber(adviceD) + "元");
                    }else{
                        adviceMoney.setText("贷款预估额: " +"2000000.00元");
                    }
                } else {
                    adviceMoney.setText("贷款预估额: 0.00元");
                }
            }
        });
    }

    private void initIcCard() {
        switch (sort){
            case "wdxyd":
                getHaveWDXYProduct();
                break;
            case "rzzlVo":
                getHaveRZZLProduct();
                break;
            case "sjsh":
                getHaveProduct();
                break;
            case "mdbt":
                getHaveMdbtProduct();
                break;
        }
    }


    // 弹出选择器
    private void ShowPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                distinct = options3Items.get(options1).get(options2).get(options3);
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                // Toast.makeText(AddAddressActivity.this,tx,Toast.LENGTH_SHORT).show();
                addressDetail.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initSortAlterDialog() {
        final String[] sortArr = getResources().getStringArray(R.array.brand_sort);
        soreBuilder = new AlertDialog.Builder(this);
        soreBuilder.setItems(sortArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                kuaidiSort.setText(sortArr[i]);
                dialogInterface.dismiss();
            }
        });
    }

    private void initAlterDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setItems(getResources().getStringArray(R.array.time), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        qixianInfo.setText("授信一年 3个月内随借随还");
                        break;
                }
                dialogInterface.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "apply_success":
                this.finish();
                break;
        }
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    private void requestSjsh() {
        if(isIc){
            if(ToolUtils.CheckEmpty(shenqingren)){
                ToastUtils.getInstance(this).showMessage("请输入申请人姓名");
            }else if(ToolUtils.CheckEmpty(phone)||!ToolUtils.checkPhone(phone.getText().toString().trim())){
                ToastUtils.getInstance(this).showMessage("请输入正确的手机号码");
            }else if(ToolUtils.CheckEmpty(ic)||!ToolUtils.checkIcNumber(ic.getText().toString().trim())){
                ToastUtils.getInstance(this).showMessage("请输入正确的身份证号码");
            }else if(ToolUtils.CheckEmpty(wangdian)){
                ToastUtils.getInstance(this).showMessage("请输入网点名称");
            }else if(ToolUtils.CheckEmpty(area)){
                ToastUtils.getInstance(this).showMessage("请输入经营区域");
            }else  if (qixianInfo.getText().toString().trim().isEmpty()) {
                ToastUtils.getInstance(this).showMessage("请选择产品期限");
            } else if (ToolUtils.CheckEmpty(meiriShou)) {
                ToastUtils.getInstance(this).showMessage("请填写每日收件量");
            } else if (ToolUtils.CheckEmpty(meiriPai)) {
                ToastUtils.getInstance(this).showMessage("请填写每日派件量");
            } else if (addressDetail.getText().toString().trim().isEmpty()) {
                ToastUtils.getInstance(this).showMessage("请选择地址");
            } else if (ToolUtils.CheckEmpty(addressEdittext)) {
                ToastUtils.getInstance(this).showMessage("请填写详细地址");
            } else if (ToolUtils.CheckEmpty(shurujine)) {
                ToastUtils.getInstance(this).showMessage("请填写借款金额");
            } else if(Double.valueOf(shurujine.getText().toString().trim())>2000000){
                ToastUtils.getInstance(this).showMessage("申请额度最高200万");
            }else if (!userPermissiosCheck.isChecked()) {
                ToastUtils.getInstance(this).showMessage("请同意金融信息服务协议");
            } else{
                dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
                applyMoney();
            }
        }else{
            ToastUtils.getInstance(this).showMessage("申请失败");
        }


    }

    @OnClick({R.id.back,R.id.chanpin_layout, R.id.choose_address_layout, R.id.apply_money_btn, R.id.brand_sort_layout, R.id.yulan_huankuan, R.id.user_permission})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.chanpin_layout:
                builder.show();
                break;
            case R.id.user_permission:
                startActivity(new Intent(this, UserAgreementActivity.class));
                break;
            case R.id.choose_address_layout:
                ShowPickerView();
                break;
            case R.id.brand_sort_layout:
                soreBuilder.show();
                break;
            case R.id.yulan_huankuan:
                if (shurujine.getText().toString().trim().isEmpty()) {
                    ToastUtils.getInstance(this).showMessage("请输入借款金额");
                } else {
                    SjshDialogFragment sjshDialogFragment = new SjshDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("money", shurujine.getText().toString().trim());
                    sjshDialogFragment.setArguments(bundle);
                    sjshDialogFragment.show(getSupportFragmentManager(), "sjshDialogFragment");
                }
                break;
            case R.id.apply_money_btn:
                requestSjsh();
                break;
        }
    }

    private void applyMoney() {
        Map<String, String> params = new HashMap<>();
        params.put("product", "/rest/products/1");
        params.put("applyAmount", shurujine.getText().toString().trim());
        params.put("applyPeriodNumber", "3");
        params.put("realName", shenqingren.getText().toString().trim());
        params.put("applyMobile", phone.getText().toString().trim());
        params.put("applyIdentityNo", ic.getText().toString().trim());
        params.put("applyEnterpriseName", wangdian.getText().toString().trim());
        params.put("applyEnterpriseReigionName", area.getText().toString().trim());
        params.put("applyEnterpriseProvince", province);
        params.put("applyEnterpriseCity", city);
        params.put("applyEnterpriseDistrict", distinct);
        params.put("applyEnterpriseAddress", addressEdittext.getText().toString().trim());
        params.put("applyReferrerRealName", tuijian.getText().toString().trim());
        params.put("applyDayPickExpress", meiriShou.getText().toString().trim());
        params.put("applyDayPatchExpress", meiriPai.getText().toString().trim());
        params.put("agentBrand",kuaidiSort.getText().toString());
        params.put("createdByDepartment", "/rest/departments/2");
        JSONObject jsonObject = new JSONObject(params);
        OkGo.<String>post(URL.APPLY_MONEY).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        if (response.body() == "") {
                            ToastUtils.getInstance(SjshActivity.this).showMessage("申请成功");
                            startActivity(new Intent(SjshActivity.this, ApplySuccessActivity.class));
                            EventBus.getDefault().post(new MessageEvent("apply_success"));
                            SjshActivity.this.finish();
                        } else {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.body());
                                switch (jsonObject.getString("ErrorCode")) {
                                    case "6657":
                                        ToastUtils.getInstance(SjshActivity.this).showMessage(jsonObject.getString("ErrorInfo"));
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                ToastUtils.getInstance(SjshActivity.this).showMessage("申请失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onError: " + response.body());
                        ToastUtils.getInstance(SjshActivity.this).showMessage("申请失败");
                    }
                });
    }

    private void getHaveProduct() {
        OkGo.<String>get(URL.HAVE_PRODUCT).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onSuccess: " + response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderwdsjshes").getJSONObject(0);
                            orderwdsjshVo = new Gson().fromJson(orderJSONObject.toString(), OrderwdsjshVo.class);
                            ic.setText(orderwdsjshVo.getApplyIdentityNo());
                            shenqingren.setText(orderwdsjshVo.getRealName());
                            ic.setFocusable(false);
                            shenqingren.setFocusable(false);
                            isIc = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            isIc = false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        isIc = false;
                    }
                });
    }

    private void getHaveRZZLProduct() {
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
                            ic.setText(orderRzzlVo.getApplyIdentityNo());
                            shenqingren.setText(orderRzzlVo.getRealName());
                            ic.setFocusable(false);
                            shenqingren.setFocusable(false);
                            isIc = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            isIc = false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        isIc = false;
                    }
                });
    }

    private void getHaveWDXYProduct() {
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
                            ic.setText(orderWdxydVo.getApplyIdentityNo());
                            shenqingren.setText(orderWdxydVo.getRealName());
                            ic.setFocusable(false);
                            shenqingren.setFocusable(false);
                            isIc = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            isIc = false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        isIc = false;
                    }
                });
    }

    private void getHaveMdbtProduct() {
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
                            ic.setText(orderMdbtVo.getApplyIdentityNo());
                            shenqingren.setText(orderMdbtVo.getRealName());
                            ic.setFocusable(false);
                            shenqingren.setFocusable(false);
                            isIc = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            isIc = false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        isIc = false;
                    }
                });
    }
}
