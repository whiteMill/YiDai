package com.sdot.yidai.wangdian;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
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
import com.sdot.yidai.model.JsonBean;
import com.sdot.yidai.model.OrderMdbtVo;
import com.sdot.yidai.model.OrderRzzlVo;
import com.sdot.yidai.model.OrderWdxydVo;
import com.sdot.yidai.model.OrderwdsjshVo;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.ui.LoginActivity;
import com.sdot.yidai.ui.UserAgreementActivity;
import com.sdot.yidai.utils.GetJsonDataUtil;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyDianActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.apply_money)
    TextView applyMoney;
    @BindView(R.id.qixian_edittext)
    TextView qixianEdittext;
    @BindView(R.id.daili_edittext)
    TextView dailiEdittext;
    @BindView(R.id.dailiqu_edittext)
    TextView dailiquEdittext;
    @BindView(R.id.name_edittext)
    EditText nameEdittext;
    @BindView(R.id.phone_edittext)
    EditText phoneEdittext;
    @BindView(R.id.iccard_edittext)
    EditText iccardEdittext;
    @BindView(R.id.tuijianren_edittext)
    EditText tuijianrenEdittext;
    @BindView(R.id.awang_name_edittext)
    EditText awangNameEdittext;
    @BindView(R.id.shoujian_edittext)
    EditText shoujianEdittext;
    @BindView(R.id.paijian_edittext)
    EditText paijianEdittext;
    @BindView(R.id.fuzhai_edittext)
    EditText fuzhaiEdittext;
    @BindView(R.id.diyazhai_edittext)
    EditText diyazhaiEdittext;
    @BindView(R.id.product_edittext)
    TextView productEdittext;
    @BindView(R.id.jine_edittext)
    EditText jineEdittext;
    @BindView(R.id.user_permissios_check)
    CheckBox userPermissiosCheck;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder soreBuilder;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private String province;
    private String city;
    private String area;
    Dialog dialog;
    ProductStateVo productStateVo;
    ProductStateVo.SjshVo sjshVo;
    ProductStateVo.WdxydVo wdxydVo;
    ProductStateVo.RzzlVo rzzlVo;

    private OrderRzzlVo orderRzzlVo;
    private OrderWdxydVo orderWdxydVo;
    private OrderwdsjshVo orderwdsjshVo;
    private OrderMdbtVo orderMdbtVo;

    Intent intent;
    String sort;

    private String defult_tiem = "3";

    private static final String TAG = "ApplyDianActivity";

    private boolean isIc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_dian);
        ButterKnife.bind(this);
        title.setText("填写申请单");
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        initSortAlterDialog();
        initAlterDialog();
        intent = getIntent();
        sort =  intent.getStringExtra("product");
        if(!sort.equals("null")){
            dialog = LoadingDialog.createLoadingDialog(this,"请稍后");
            initIcCard();
        }else{
            isIc = true;
        }
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


    @OnClick({R.id.back, R.id.apply_money, R.id.daili_layout, R.id.qixian_layout, R.id.dailiqu_layout,R.id.user_permission})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.apply_money:
                requestWdxy();
                break;
            case R.id.daili_layout:
                soreBuilder.show();
                break;
            case R.id.qixian_layout:
                builder.show();
                break;
            case R.id.dailiqu_layout:
                ShowPickerView();
                break;
            case R.id.user_permission:
                startActivity(new Intent(this, UserAgreementActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x25) {
            if (YDaiApplication.getInstance().isLoginsState()) {

            } else {
                ToastUtils.getInstance(this).showMessage("请先登录");
                startActivityForResult(new Intent(this, LoginActivity.class), 0x989);
            }
        }
    }

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
              /*      //Toast.makeText(AddAddressActivity.this,"解析数据成功",Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    introFragment.setProvince(options1Items,options2Items,options3Items);
                    conditionFragment.setProvince(options1Items,options2Items,options3Items);
                    flowFragment.setProvince(options1Items,options2Items,options3Items);*/
                    break;

                case MSG_LOAD_FAILED:
                    // Toast.makeText(AddAddressActivity.this,"解析数据失败",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    private void requestWdxy() {
                if(isIc){
                    if (ToolUtils.CheckEmpty(nameEdittext)) {
                        ToastUtils.getInstance(this).showMessage("请输入姓名");
                    } else if (!ToolUtils.checkPhone(phoneEdittext.getText().toString().trim())) {
                        ToastUtils.getInstance(this).showMessage("请输入正确的手机号码");
                    } else if (!ToolUtils.checkIcNumber(iccardEdittext.getText().toString().trim())) {
                        ToastUtils.getInstance(this).showMessage("请输入正确的身份证号");
                    } else if (ToolUtils.CheckEmpty(awangNameEdittext)) {
                        ToastUtils.getInstance(this).showMessage("请输入快递网点名称");
                    } else if (ToolUtils.CheckEmpty(shoujianEdittext)) {
                        ToastUtils.getInstance(this).showMessage("请输入每日收件量");
                    } else if (ToolUtils.CheckEmpty(paijianEdittext)) {
                        ToastUtils.getInstance(this).showMessage("请输入每日派件量");
                    } else if (dailiquEdittext.getText().toString().isEmpty()) {
                        ToastUtils.getInstance(this).showMessage("请选择经营区域");
                    } else if (ToolUtils.CheckEmpty(jineEdittext)) {
                        ToastUtils.getInstance(this).showMessage("请输入借款金额");
                    }else if(Double.valueOf(jineEdittext.getText().toString().trim())>2000000){
                        ToastUtils.getInstance(this).showMessage("申请额度最高200万");
                    } else if(!userPermissiosCheck.isChecked()){
                        ToastUtils.getInstance(this).showMessage("请同意金融信息服务协议");
                    }else {
                        dialog = LoadingDialog.createLoadingDialog(this, "正在申请");
                        applyDian();
                    }
                }else{
                    ToastUtils.getInstance(this).showMessage("申请失败");
                }

    }

    // 弹出选择器
    private void ShowPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                area = options3Items.get(options1).get(options2).get(options3);
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                // Toast.makeText(AddAddressActivity.this,tx,Toast.LENGTH_SHORT).show();
                dailiquEdittext.setText(tx);
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

    private void initAlterDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setItems(getResources().getStringArray(R.array.wangdain_time), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        qixianEdittext.setText("3个月");
                        defult_tiem = "3";
                        break;
                    case 1:
                        qixianEdittext.setText("6个月");
                        defult_tiem = "6";
                        break;
                    case 2:
                        qixianEdittext.setText("12个月");
                        defult_tiem = "12";
                        break;
                }
                dialogInterface.dismiss();
            }
        });
    }

    private void initSortAlterDialog() {
        final String[] sortArr = getResources().getStringArray(R.array.brand_sort);
        soreBuilder = new AlertDialog.Builder(this);
        soreBuilder.setItems(sortArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dailiEdittext.setText(sortArr[i]);
                dialogInterface.dismiss();
            }
        });
    }

    private void applyDian() {
        Map<String, String> params = new HashMap<>();
        params.put("product", "/rest/products/2");
        params.put("createdByDepartment", "/rest/departments/2");
        params.put("realName", nameEdittext.getText().toString().trim());
        params.put("applyMobile", phoneEdittext.getText().toString().trim());
        params.put("applyIdentityNo", iccardEdittext.getText().toString().trim());
        params.put("applyReferrerRealName", tuijianrenEdittext.getText().toString().trim());
        params.put("applyEnterpriseName", awangNameEdittext.getText().toString().trim());
        params.put("applyDayPickExpress", shoujianEdittext.getText().toString().trim());
        params.put("applyDayPatchExpress", paijianEdittext.getText().toString().trim());
        params.put("comfirmComprehensiveLiabilities", fuzhaiEdittext.getText().toString().trim().isEmpty() ? "0" : fuzhaiEdittext.getText().toString().trim());
        params.put("comfirmMortgageLiabilities", diyazhaiEdittext.getText().toString().trim().isEmpty() ? "0" : diyazhaiEdittext.getText().toString().trim());
        params.put("agentBrand",dailiEdittext.getText().toString());
        params.put("applyEnterpriseProvince", province);
        params.put("applyEnterpriseCity", city);
        params.put("applyEnterpriseDistrict", area);
        params.put("applyEnterpriseReigionName",province+city+area);
        params.put("applyPeriodNumber", defult_tiem);
        params.put("applyAmount", jineEdittext.getText().toString().trim());
        JSONObject jsonObject = new JSONObject(params);
        OkGo.<String>post(URL.APPLY_WANGDIAN).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        LoadingDialog.closeDialog(dialog);
                        if(response.body()==""){
                            ToastUtils.getInstance(ApplyDianActivity.this).showMessage("申请成功");
                            startActivity(new Intent(ApplyDianActivity.this, DianSuccessActivity.class));
                            ApplyDianActivity.this.finish();
                        }else{
                            JSONObject jsonObject =null;
                            try {
                                jsonObject = new JSONObject(response.body());
                                switch (jsonObject.getString("ErrorCode")){
                                    case "6657" :
                                        ToastUtils.getInstance(ApplyDianActivity.this).showMessage(jsonObject.getString("ErrorInfo"));
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                ToastUtils.getInstance(ApplyDianActivity.this).showMessage("申请失败");
                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: " + response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyDianActivity.this).showMessage("申请失败");
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
                        Log.i(TAG, "onSuccess: "+response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONObject orderJSONObject = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderwdsjshes").getJSONObject(0);
                            orderwdsjshVo = new Gson().fromJson(orderJSONObject.toString(), OrderwdsjshVo.class);
                            iccardEdittext.setText(orderwdsjshVo.getApplyIdentityNo());
                            nameEdittext.setText(orderwdsjshVo.getRealName());
                            iccardEdittext.setFocusable(false);
                            nameEdittext.setFocusable(false);
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
                            iccardEdittext.setText(orderRzzlVo.getApplyIdentityNo());
                            nameEdittext.setText(orderRzzlVo.getRealName());
                            iccardEdittext.setFocusable(false);
                            nameEdittext.setFocusable(false);
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
                            iccardEdittext.setText(orderWdxydVo.getApplyIdentityNo());
                            nameEdittext.setText(orderWdxydVo.getRealName());
                            iccardEdittext.setFocusable(false);
                            nameEdittext.setFocusable(false);
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
                            iccardEdittext.setText(orderMdbtVo.getApplyIdentityNo());
                            nameEdittext.setText(orderMdbtVo.getRealName());
                            iccardEdittext.setFocusable(false);
                            nameEdittext.setFocusable(false);
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
