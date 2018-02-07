package com.sdot.yidai.fragment.editapply;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.miandan.CompanyActivity;
import com.sdot.yidai.miandan.MianDanSuccessActivity;
import com.sdot.yidai.model.CompanyVo;
import com.sdot.yidai.model.JsonBean;
import com.sdot.yidai.model.OrderMdbtVo;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.ui.BaseActivity;
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

public class EditOrderMdbtActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.name_edittext)
    EditText nameEdittext;
    @BindView(R.id.phone_edittext)
    EditText phoneEdittext;
    @BindView(R.id.iccard_edittext)
    EditText iccardEdittext;
    @BindView(R.id.tuijianren_edittext)
    EditText tuijianrenEdittext;
    @BindView(R.id.qiye_name_edittext)
    TextView qiyeNameEdittext;
    @BindView(R.id.awang_name_edittext)
    EditText awangNameEdittext;
    @BindView(R.id.shoujian_edittext)
    EditText shoujianEdittext;
    @BindView(R.id.paijian_edittext)
    EditText paijianEdittext;
    @BindView(R.id.danjia_edittext)
    EditText danjiaEdittext;
    @BindView(R.id.fuzhai_edittext)
    EditText fuzhaiEdittext;
    @BindView(R.id.diyazhai_edittext)
    EditText diyazhaiEdittext;
    @BindView(R.id.daili_edittext)
    EditText dailiEdittext;
    @BindView(R.id.band_iamge)
    ImageView bandIamge;
    @BindView(R.id.daili_layout)
    RelativeLayout dailiLayout;
    @BindView(R.id.dailiqu)
    TextView dailiqu;
    @BindView(R.id.dailiqu_edittext)
    TextView dailiquEdittext;
    @BindView(R.id.jine_edittext)
    EditText jineEdittext;
    @BindView(R.id.apply_money)
    Button applyMoney;
    @BindView(R.id.user_permissios_check)
    CheckBox userPermissiosCheck;
    @BindView(R.id.user_permission)
    TextView userPermission;
    private AlertDialog.Builder soreBuilder;
    CompanyVo companyVo;
    Intent intent;
    Dialog dialog;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    private String province;
    private String city;
    private String area;

    private static final String TAG = "ApplyDanActivity";
    private static final int COMPANY_CODE = 0x322;
    private static final int COMPANY_FINISH_CODE = 0x320;

    OrderMdbtVo orderMdbtVo;
    ProductStateVo productStateVo;

    boolean isEdit = false;

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
                    // isLoaded = true;
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
        setContentView(R.layout.activity_edit_order_mdbt);
        ButterKnife.bind(this);
        title.setText("申请单");
        initSortAlterDialog();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        getCompanyInfo();
        intent = getIntent();
        orderMdbtVo = (OrderMdbtVo) intent.getSerializableExtra("orderMdbtVo");
        initDate();
        getProductSort();
    }

    private void initDate() {
        nameEdittext.setText(orderMdbtVo.getRealName());
        phoneEdittext.setText(orderMdbtVo.getApplyMobile());
        iccardEdittext.setText(orderMdbtVo.getApplyIdentityNo());
        if (orderMdbtVo.getApplyReferrerRealName() != null) {
            tuijianrenEdittext.setText(orderMdbtVo.getApplyReferrerRealName());
        }
        awangNameEdittext.setText(orderMdbtVo.getApplyEnterpriseName());
        shoujianEdittext.setText(orderMdbtVo.getApplyDayPickExpress());
        paijianEdittext.setText(orderMdbtVo.getApplyDayPatchExpress());
        danjiaEdittext.setText(orderMdbtVo.getApplyPurchasePrice());
        dailiEdittext.setText(orderMdbtVo.getAgentBrand());
        dailiquEdittext.setText(orderMdbtVo.getApplyEnterpriseProvince() + orderMdbtVo.getApplyEnterpriseCity() + orderMdbtVo.getApplyEnterpriseDistrict());
        jineEdittext.setText(orderMdbtVo.getApplyAmount());
        province = orderMdbtVo.getApplyEnterpriseProvince();
        city = orderMdbtVo.getApplyEnterpriseCity();
        area = orderMdbtVo.getApplyEnterpriseDistrict();
        if (isEdit) {
            applyMoney.setClickable(true);
            applyMoney.setText("提交修改");
        } else {
            applyMoney.setClickable(false);
            applyMoney.setText("请稍后");

        }
    }

    private void forceAble() {
        nameEdittext.setFocusable(false);
        phoneEdittext.setFocusable(false);
        iccardEdittext.setFocusable(false);
        tuijianrenEdittext.setFocusable(false);
        awangNameEdittext.setFocusable(false);
        shoujianEdittext.setFocusable(false);
        paijianEdittext.setFocusable(false);
        danjiaEdittext.setFocusable(false);
        dailiEdittext.setFocusable(false);
        dailiquEdittext.setFocusable(false);
        jineEdittext.setFocusable(false);
    }

    private void getProductSort() {
        OkGo.<String>get(URL.GET_SORT_PRODUCT).tag(this)
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        Log.i(TAG, "onSuccess: " + response.body());
                        try {
                            jsonObject = new JSONObject(response.body());
                            productStateVo = new Gson().fromJson(jsonObject.getString("data"), ProductStateVo.class);
                            if (productStateVo.getSjsh().getState() != null) {
                                if (productStateVo.getSjsh().getState().equals("ENABLED") || productStateVo.getSjsh().getState().equals("ADOPT") || productStateVo.getSjsh().getState().equals("SIGNED")) {
                                    nameEdittext.setFocusable(false);
                                    iccardEdittext.setFocusable(false);
                                }
                            } else if (productStateVo.getWdxyd().getState() != null) {
                                if (productStateVo.getWdxyd().getState().equals("ENABLED")
                                        || productStateVo.getWdxyd().getState().equals("ADOPT")) {
                                    nameEdittext.setFocusable(false);
                                    iccardEdittext.setFocusable(false);
                                }
                            } else if (productStateVo.getRzzl().getState() != null) {
                                if (productStateVo.getRzzl().getState().equals("ENABLED") || productStateVo.getRzzl().getState().equals("ADOPT")) {
                                    nameEdittext.setFocusable(false);
                                    iccardEdittext.setFocusable(false);
                                }
                            }

                            if (productStateVo.getMdbt().getCardId() != null) {
                                applyMoney.setClickable(false);
                                forceAble();
                                applyMoney.setText("审核通过");
                                applyMoney.setBackgroundResource(R.drawable.force_click);
                                isEdit = false;
                            } else {
                                isEdit = true;
                                applyMoney.setClickable(true);
                                applyMoney.setText("提交修改");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            applyMoney.setClickable(false);
                            forceAble();
                            applyMoney.setText("数据异常");
                            applyMoney.setBackgroundResource(R.drawable.force_click);
                            isEdit = false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        applyMoney.setClickable(false);
                        forceAble();
                        applyMoney.setText("数据异常");
                        applyMoney.setBackgroundResource(R.drawable.force_click);
                        isEdit = false;
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

    @OnClick({R.id.qiye_name_edittext, R.id.band_iamge, R.id.daili_layout, R.id.apply_money, R.id.user_permission, R.id.back, R.id.dailiqu_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dailiqu_layout:
                if (isEdit) {
                    ShowPickerView();
                }
                break;
            case R.id.back:
                this.finish();
                break;
            case R.id.qiye_name_edittext:
                if (isEdit) {
                    if (qiyeNameEdittext.getText().toString().trim().isEmpty()) {
                        Intent intentCom = new Intent(this, CompanyActivity.class);
                        intentCom.putExtra("state", "edit");
                        startActivityForResult(intentCom, COMPANY_CODE);
                    } else {
                        Intent intentUnCom = new Intent(this, CompanyActivity.class);
                        intentUnCom.putExtra("state", "unEdit");
                        intentUnCom.putExtra("companyVo", companyVo);
                        startActivityForResult(intentUnCom, COMPANY_CODE);
                    }
                }

                break;
            case R.id.band_iamge:
                if (isEdit) {
                    soreBuilder.show();
                }
                break;
            case R.id.daili_layout:

                break;
            case R.id.apply_money:
                if (ToolUtils.CheckEmpty(nameEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入姓名");
                } else if (ToolUtils.CheckEmpty(phoneEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入手机号码");
                } else if (ToolUtils.CheckEmpty(iccardEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入身份证号");
                } else if (qiyeNameEdittext.getText().toString().trim().isEmpty()) {
                    ToastUtils.getInstance(this).showMessage("请输入企业名称");
                } else if (ToolUtils.CheckEmpty(awangNameEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入快递网点名称");
                } else if (ToolUtils.CheckEmpty(shoujianEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入日均收件量");
                } else if (ToolUtils.CheckEmpty(paijianEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入日均派件量");
                } else if (ToolUtils.CheckEmpty(danjiaEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入面单采购单价");
                } else if (ToolUtils.CheckEmpty(dailiEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入或选择代理品牌");
                } else if (dailiquEdittext.getText().toString().trim().isEmpty()) {
                    ToastUtils.getInstance(this).showMessage("请选择经营区域");
                } else if (ToolUtils.CheckEmpty(jineEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入进款金额");
                } else if (Double.valueOf(jineEdittext.getText().toString().trim()) < 500000) {
                    ToastUtils.getInstance(this).showMessage("申请额度最低50万");
                } else if (Double.valueOf(jineEdittext.getText().toString().trim()) > 30000000) {
                    ToastUtils.getInstance(this).showMessage("申请额度最高3000万");
                } else {
                    if (dialog != null) {
                        dialog.show();
                    } else {
                        dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
                    }
                    applyDan();
                }
                break;
            case R.id.user_permission:
                startActivity(new Intent(this, UserAgreementActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == COMPANY_FINISH_CODE) {
            getCompanyInfo();
        }
    }

    private void applyDan() {
        final Map<String, String> danMap = new HashMap<>();
        danMap.put("realName", nameEdittext.getText().toString().trim());
        danMap.put("applyMobile", phoneEdittext.getText().toString().trim());
        danMap.put("applyIdentityNo", iccardEdittext.getText().toString().trim());
        danMap.put("applyReferrerRealName", tuijianrenEdittext.getText().toString().trim());
        //danMap.put("company","/rest/company/"+companyVo.getId());
        danMap.put("applyEnterpriseName", awangNameEdittext.getText().toString().trim());
        danMap.put("applyDayPickExpress", shoujianEdittext.getText().toString().trim());
        danMap.put("applyDayPatchExpress", paijianEdittext.getText().toString().trim());
        danMap.put("applyPurchasePrice", danjiaEdittext.getText().toString().trim());
        danMap.put("comfirmComprehensiveLiabilities", fuzhaiEdittext.getText().toString().trim().isEmpty() ? "0" : fuzhaiEdittext.getText().toString().trim());
        danMap.put("comfirmMortgageLiabilities", diyazhaiEdittext.getText().toString().trim().isEmpty() ? "0" : diyazhaiEdittext.getText().toString().trim());
        danMap.put("agentBrand", dailiEdittext.getText().toString());
        danMap.put("applyEnterpriseProvince", province);
        danMap.put("applyEnterpriseCity", city);
        danMap.put("applyEnterpriseDistrict", area);
        danMap.put("applyEnterpriseReigionName", province + city + area);
        danMap.put("applyAmount", jineEdittext.getText().toString().trim());
        danMap.put("act", "confirm");

       /* danMap.put("product", "/rest/products/4");
        *//* danMap.put("act", "create");*//*
        danMap.put("createdByDepartment", "/rest/department/2");*/

        JSONObject jsonObject = new JSONObject(danMap);
        OkGo.<String>patch(URL.APPLY_MDBT + "/" + orderMdbtVo.getId()).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(EditOrderMdbtActivity.this).showMessage("修改成功");
                        EditOrderMdbtActivity.this.finish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: " + response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(EditOrderMdbtActivity.this).showMessage("修改失败");
                    }
                });
    }

    private void getCompanyInfo() {
        OkGo.<String>get(URL.QUERY_COMPANY).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        companyVo = new Gson().fromJson(response.body(), CompanyVo.class);
                        if (companyVo != null) {
                            qiyeNameEdittext.setText(companyVo.getCompanyName());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }
}
