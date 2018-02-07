package com.sdot.yidai.fragment.company;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.sdot.yidai.model.CompanyInfo;
import com.sdot.yidai.model.JsonBean;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.GetJsonDataUtil;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditCompanyActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.agongsi_name_edittext)
    EditText agongsiNameEdittext;
    @BindView(R.id.gongshang_no_edittext)
    EditText gongshangNoEdittext;
    @BindView(R.id.faren_name_edittext)
    EditText farenNameEdittext;
    @BindView(R.id.gongsi_dizhi_edittext)
    TextView gongsiDizhiEdittext;
    @BindView(R.id.xiangzi_address_edittext)
    EditText xiangziAddressEdittext;
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

    @BindView(R.id.sure)
    Button sure;
    CompanyInfo companyInfo;
    Intent intent;
    Dialog dialog;
    private static final String TAG = "EditCompanyActivity";
    @BindView(R.id.xx_layout)
    RelativeLayout xxLayout;

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
                   /*introFragment.setProvince(options1Items, options2Items, options3Items);
                    conditionFragment.setProvince(options1Items, options2Items, options3Items);
                    flowFragment.setProvince(options1Items, options2Items, options3Items);*/
                    break;

                case MSG_LOAD_FAILED:
                    // Toast.makeText(AddAddressActivity.this,"解析数据失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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
                String tx = options1Items.get(options1).getPickerViewText() + " " +
                        options2Items.get(options1).get(options2) + " " +
                        options3Items.get(options1).get(options2).get(options3);
                // Toast.makeText(AddAddressActivity.this,tx,Toast.LENGTH_SHORT).show();
                gongsiDizhiEdittext.setText(tx);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);
        ButterKnife.bind(this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        intent = getIntent();
        companyInfo = (CompanyInfo) intent.getSerializableExtra("companyInfo");
        agongsiNameEdittext.setText(companyInfo.getCompanyName());
        gongshangNoEdittext.setText(companyInfo.getCompanyNumber());
        farenNameEdittext.setText(companyInfo.getCompanyPersonName());
        if (companyInfo.getCompanyProvince() == null) {
            gongsiDizhiEdittext.setText("");
        } else {
            gongsiDizhiEdittext.setText(companyInfo.getCompanyProvince() + " " + companyInfo.getCompanyCity() + " " + companyInfo.getCompanyDistrict());
        }
        if (companyInfo.getCompanyAddress() == null) {
            xiangziAddressEdittext.setText("");
        } else {
            xiangziAddressEdittext.setText(companyInfo.getCompanyAddress());
            xiangziAddressEdittext.setSelection(companyInfo.getCompanyAddress().length());
        }


        agongsiNameEdittext.setSelection(companyInfo.getCompanyName().length());
        gongshangNoEdittext.setSelection(companyInfo.getCompanyNumber().length());
        farenNameEdittext.setSelection(companyInfo.getCompanyPersonName().length());

        title.setText("编辑");
    }

    @OnClick({R.id.back, R.id.sure, R.id.xx_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                EventBus.getDefault().post(new MessageEvent("company_edit"));
                this.finish();
                break;
            case R.id.sure:
                if (ToolUtils.CheckEmpty(agongsiNameEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入公司名称");
                } else if (ToolUtils.CheckEmpty(gongshangNoEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入统一社会信用代码");
                } else if (ToolUtils.CheckEmpty(farenNameEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入公司法人姓名");
                } else if (gongsiDizhiEdittext.getText().toString().isEmpty() || ToolUtils.CheckEmpty(xiangziAddressEdittext)) {
                    ToastUtils.getInstance(this).showMessage("请输入公司地址");
                } else {
                    dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
                    editCompany();
                }
                break;
            case R.id.xx_layout:
                ShowPickerView();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (dialog != null && dialog.isShowing()) {
            OkGo.getInstance().cancelTag(this);
        }
        EventBus.getDefault().post(new MessageEvent("company_edit"));
    }

    private void editCompany() {
        OkGo.<String>post(URL.EDIT_COMPANY).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("id", companyInfo.getId())
                .params("companyName", agongsiNameEdittext.getText().toString().trim())
                .params("companyNumber", gongshangNoEdittext.getText().toString().trim())
                .params("companyPersonName", farenNameEdittext.getText().toString().trim())
                .params("companyProvince", province)
                .params("companyCity", city)
                .params("companyDistrict", area)
                .params("companyAddress", xiangziAddressEdittext.getText().toString().trim())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        Log.i(TAG, "onSuccess: " + response.body());
                        //  EventBus.getDefault().post(new MessageEvent("company_edit"));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("ErrorCode").equals("0")) {
                                ToastUtils.getInstance(EditCompanyActivity.this).showMessage("修改成功");
                            } else {
                                ToastUtils.getInstance(EditCompanyActivity.this).showMessage(jsonObject.getString("ErrorInfo"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(EditCompanyActivity.this).showMessage("修改失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(EditCompanyActivity.this).showMessage("修改失败");
                    }
                });
    }
}
