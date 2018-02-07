package com.sdot.yidai.rongzizl;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.billfragment.BackFragment;
import com.sdot.yidai.model.ApplyDateVo;
import com.sdot.yidai.model.ImageVo;
import com.sdot.yidai.model.OrderRzzlVo;
import com.sdot.yidai.model.OrderWdxydVo;
import com.sdot.yidai.model.ProductImageVo;
import com.sdot.yidai.model.UpLoadImageVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.wangdian.ApplyDianDateActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ApplyRzzlDateActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.dateListView)
    ListView dateListView;
    TextView name;
    TextView phone;
    TextView ic;
    TextView wangdian;
    TextView area;
    TextView address;

    private int id = 1;


    List<ApplyDateVo> applyDateVoList;

    private Dialog dialog;
    private static final String TAG = "ApplyRzzlDateActivity";

    List<ImageVo> zhiZhaoList = new ArrayList<>();
    List<ImageVo> kaihuList = new ArrayList<>();
    List<ImageVo> kuaidiyeList = new ArrayList<>();
    List<ImageVo> gongsiList = new ArrayList<>();
    List<ImageVo> wangmengList = new ArrayList<>();
    List<ImageVo> zufangList = new ArrayList<>();
    List<ImageVo> fuqiList = new ArrayList<>();
    List<ImageVo> jiehunList = new ArrayList<>();
    List<ImageVo> zhengxinList = new ArrayList<>();
    List<ImageVo> gudingList = new ArrayList<>();
    List<ImageVo> cheliangList = new ArrayList<>();
    List<ImageVo> fangchanList = new ArrayList<>();
    List<ImageVo> jinliangList = new ArrayList<>();
    List<ImageVo> jinsanList = new ArrayList<>();
    List<ImageVo> jindiList = new ArrayList<>();
    List<ImageVo> duigongList = new ArrayList<>();
    List<ImageVo> gentaoList = new ArrayList<>();
    List<ImageVo> shebiList = new ArrayList<>();

    OrderRzzlVo orderRzzlVo;
    Intent intent;
    DateBaseAdapter dateBaseAdapter;
    View headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_rzzl_date);
        ButterKnife.bind(this);
        title.setText("融资租赁资料");
        initApplyDate();
        headView = LayoutInflater.from(this).inflate(R.layout.apply_date_headview, null);
        initView();
        dateListView.addHeaderView(headView);
        dateBaseAdapter = new DateBaseAdapter();
        dateListView.setAdapter(dateBaseAdapter);
        intent = getIntent();
        orderRzzlVo = (OrderRzzlVo) intent.getSerializableExtra("orderRzzlVo");
        name.setText(orderRzzlVo.getRealName());
        phone.setText(orderRzzlVo.getApplyMobile());
        ic.setText(orderRzzlVo.getApplyIdentityNo());
        wangdian.setText(orderRzzlVo.getApplyEnterpriseName());
        area.setText(orderRzzlVo.getApplyEnterpriseProvince()+" "+orderRzzlVo.getApplyEnterpriseCity()+" "+orderRzzlVo.getApplyEnterpriseDistrict());
        address.setText("暂无");

        initImage();
    }


    private void initView(){
        name = headView.findViewById(R.id.name);
        phone = headView.findViewById(R.id.phone);
        ic = headView.findViewById(R.id.ic);
        wangdian = headView.findViewById(R.id.wangdian);
        area = headView.findViewById(R.id.area);
        address = headView.findViewById(R.id.address);
    }

    private void initApplyDate() {
        zhiZhaoList.add(new ImageVo(0 + "", "null"));
        kaihuList .add(new ImageVo(0 + "", "null"));
        kuaidiyeList.add(new ImageVo(0 + "", "null"));
        gongsiList .add(new ImageVo(0 + "", "null"));
        wangmengList .add(new ImageVo(0 + "", "null"));

        zufangList.add(new ImageVo(0 + "", "null"));
        fuqiList .add(new ImageVo(0 + "", "null"));
        jiehunList.add(new ImageVo(0 + "", "null"));
        zhengxinList.add(new ImageVo(0 + "", "null"));
        gudingList.add(new ImageVo(0 + "", "null"));

        cheliangList.add(new ImageVo(0 + "", "null"));
        fangchanList .add(new ImageVo(0 + "", "null"));
        jinliangList.add(new ImageVo(0 + "", "null"));
        jinsanList .add(new ImageVo(0 + "", "null"));
        jindiList .add(new ImageVo(0 + "", "null"));

        duigongList.add(new ImageVo(0 + "", "null"));
        gentaoList .add(new ImageVo(0 + "", "null"));
        shebiList .add(new ImageVo(0 + "", "null"));

        applyDateVoList = new ArrayList<>();

        applyDateVoList.add(new ApplyDateVo("120", "营业执照", zhiZhaoList));
        applyDateVoList.add(new ApplyDateVo("121", "开户许可证", kaihuList));
        applyDateVoList.add(new ApplyDateVo("122", "快递业务经营许可证", kuaidiyeList));
        applyDateVoList.add(new ApplyDateVo("123", "公司章程", gongsiList));
        applyDateVoList.add(new ApplyDateVo("124", "网点加盟协议(跟总部)", wangmengList));

        applyDateVoList.add(new ApplyDateVo("125", "租房合同", zufangList));
        applyDateVoList.add(new ApplyDateVo("126", "夫妻双方身份证正反面", fuqiList));
        applyDateVoList.add(new ApplyDateVo("127", "结婚证", jiehunList));
        applyDateVoList.add(new ApplyDateVo("128", "征信报告(企业及法人夫妻)", zhengxinList));
        applyDateVoList.add(new ApplyDateVo("129", "公司固定资产清单(加盖公章)", gudingList));

        applyDateVoList.add(new ApplyDateVo("130", "车辆登记证书", cheliangList));
        applyDateVoList.add(new ApplyDateVo("131", "房产证", fangchanList));
        applyDateVoList.add(new ApplyDateVo("132", "近2年快递业务系统数据", jinliangList));
        applyDateVoList.add(new ApplyDateVo("133", "近3个月水电费清单", jinsanList));
        applyDateVoList.add(new ApplyDateVo("134", "近4个月的财务报表、科目明细表、纳税申报表", jindiList));

        applyDateVoList.add(new ApplyDateVo("135", "对公及个人主要账户流水", duigongList));
        applyDateVoList.add(new ApplyDateVo("136", "跟淘宝电商签订《服务协议》", gentaoList));
        applyDateVoList.add(new ApplyDateVo("137", "设备采购合同", shebiList));
    }

    private String uriUpdate(Intent intentDate) {
        List<Uri> mSelected = Matisse.obtainResult(intentDate);
        if (getRealFilePath(this, mSelected.get(0)) == null) {
            String path = Environment.getExternalStorageDirectory() + mSelected.get(0).getPath().replace("my_images", "Pictures");
            return path;
        } else {
            return getRealFilePath(this, mSelected.get(0));
        }
    }

    private void initImage(){
        OkGo.<String>get(URL.BIND_RONGZ_IMAGE+orderRzzlVo.getId()+"/files").tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray =  new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderrzzlFiles");
                            initImageDate(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void initImageDate(JSONArray imageArray){
        Log.i(TAG, "initImageDate: "+imageArray.length());
        for (int i = 0; i < imageArray.length(); i++) {
            try {
                ProductImageVo productImageVo = new Gson().fromJson(imageArray.getJSONObject(i).toString(), ProductImageVo.class);
                switch (productImageVo.getSubcategory()){
                    case "营业执照":
                        zhiZhaoList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "开户许可证":
                        kaihuList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "快递业务经营许可证":
                        kuaidiyeList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "公司章程":
                        gongsiList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "网点加盟协议(跟总部)":
                        wangmengList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;

                    case "租房合同":
                        zufangList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "夫妻双方身份证正反面":
                        fuqiList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "结婚证":
                        jiehunList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;
                    case "征信报告(企业及法人夫妻)":
                        zhengxinList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;
                    case "公司固定资产清单(加盖公章)":
                        gudingList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;

                    case "车辆登记证书":
                        cheliangList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;
                    case "房产证":
                        fangchanList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;
                    case "近2年快递业务系统数据":
                        jinliangList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;
                    case "近3个月水电费清单":
                        jinsanList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;
                    case "近4个月的财务报表、科目明细表、纳税申报表":
                        jindiList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;

                    case "对公及个人主要账户流水":
                        duigongList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;
                    case "跟淘宝电商签订《服务协议》":
                        gentaoList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;
                    case "设备采购合同":
                        shebiList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;

                }
            } catch (JSONException e) {
                e.printStackTrace();
                ToastUtils.getInstance(this).showMessage("异常");
            }
        }

        Collections.sort(zhiZhaoList, new ComparatorDate());
        Collections.sort(kaihuList, new ComparatorDate());
        Collections.sort(kuaidiyeList, new ComparatorDate());
        Collections.sort(gongsiList, new ComparatorDate());
        Collections.sort(wangmengList, new ComparatorDate());

        Collections.sort(zufangList, new ComparatorDate());
        Collections.sort(fuqiList, new ComparatorDate());
        Collections.sort(jiehunList, new ComparatorDate());
        Collections.sort(zhengxinList, new ComparatorDate());
        Collections.sort(gudingList, new ComparatorDate());

        Collections.sort(cheliangList, new ComparatorDate());
        Collections.sort(fangchanList, new ComparatorDate());
        Collections.sort(jinliangList, new ComparatorDate());
        Collections.sort(jinsanList, new ComparatorDate());
        Collections.sort(jindiList, new ComparatorDate());

        Collections.sort(duigongList, new ComparatorDate());
        Collections.sort(gentaoList, new ComparatorDate());
        Collections.sort(shebiList, new ComparatorDate());

        dateBaseAdapter.notifyDataSetChanged();
    }

    class ComparatorDate implements Comparator {
        public int compare(Object obj1, Object obj2) {
            ImageVo imageVo1 = (ImageVo) obj1;
            ImageVo imageVo2 = (ImageVo) obj2;
            String id1 = null;
            String id2 = null;
            String time1 = imageVo1.getId();
            String time2 = imageVo2.getId();

            if (Integer.valueOf(time1) > Integer.valueOf(time2)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    /**
     * uri转path
     */
    public static String getRealFilePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            //imageViewPageAdapter.notifyDataSetChanged();
        } else {
            if (resultCode == RESULT_OK) {
                ImageVo imageVo = null;
                switch (requestCode) {
                    case 120:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(0).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadDuplicateofBusinessLicense","营业执照",imageVo,applyDateVoList.get(0).getImageVoList());
                        break;
                    case 121:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(1).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadPermitForOpeningBankAccount", "开户许可证",imageVo,applyDateVoList.get(1).getImageVoList());
                        break;
                    case 122:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(2).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadExpressBusinessLicense", "快递业务经营许可证",imageVo,applyDateVoList.get(2).getImageVoList());
                        break;
                    case 123:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(3).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadArticlesOfCompanyAssociation", "公司章程",imageVo,applyDateVoList.get(3).getImageVoList());
                        break;
                    case 124:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(4).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadTheJoinedCooperationAgreementOfBraches", "网点加盟协议(跟总部)",imageVo,applyDateVoList.get(4).getImageVoList());
                        break;


                    case 125:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(5).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadLeaseContract", "租房合同",imageVo,applyDateVoList.get(5).getImageVoList());
                        break;
                    case 126:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(6).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadDoubleSidedPhotosOfIDCard", "夫妻双方身份证正反面",imageVo,applyDateVoList.get(6).getImageVoList());
                        break;
                    case 127:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(7).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadmarriageCertificatePhoto", "结婚证照片",imageVo,applyDateVoList.get(7).getImageVoList());
                        break;
                    case 128:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(8).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadCreditRepor", "征信报告(企业及法人夫妻)",imageVo,applyDateVoList.get(8).getImageVoList());
                        break;
                    case 129:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(9).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadDetailedListOfFixedAssets", "公司固定资产清单(加盖公章)",imageVo,applyDateVoList.get(9).getImageVoList());
                        break;


                    case 130:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(10).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadMotorVehicleRegister", "车辆登记证书",imageVo,applyDateVoList.get(10).getImageVoList());
                        break;
                    case 131:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(11).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadHousingOwnershipCertificate", "房产证",imageVo,applyDateVoList.get(11).getImageVoList());
                        break;
                    case 132:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(12).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadCheckingAndAnalysisSystemOfBusinessDataForRecent2Years", "近2年快递业务系统数据",imageVo,applyDateVoList.get(12).getImageVoList());
                        break;
                    case 133:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(13).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadListOfWaterAndElectricityFeeForLast3Months", "近3个月水电费清单",imageVo,applyDateVoList.get(13).getImageVoList());
                        break;
                    case 134:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(14).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadFinancialStatements", "近4个月的财务报表、科目明细表、纳税申报表",imageVo,applyDateVoList.get(14).getImageVoList());
                        break;
                    case 135:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(15).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadAccountFlow", "对公及个人主要账户流水",imageVo,applyDateVoList.get(15).getImageVoList());
                        break;
                    case 136:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(16).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadServiceAgreement", "跟淘宝电商签订《服务协议》",imageVo,applyDateVoList.get(16).getImageVoList());
                        break;
                    case 137:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(17).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadEquipmentPurchaseContract", "设备采购合同",imageVo,applyDateVoList.get(17).getImageVoList());
                        break;
                }
                dateBaseAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initPermission(String code) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            choosePic(Integer.valueOf(code));
        } else {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
    }


    private void uploadFile(File file, final String sort, final String sortName, final List<ImageVo> imageVoList, final ImageVo imageVo) {
        OkGo.<String>post(URL.UPDATE_IMAGE).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("fileType", "image")
                .params("file", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        JSONObject jsonObject = null;
                        UpLoadImageVo upLoadImageVo = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            upLoadImageVo = new Gson().fromJson(jsonObject.getString("result"),UpLoadImageVo.class) ;
                            upLoadImageVo.setTopcategory("企业资质");
                            upLoadImageVo.setSubcategory(sortName);
                            bindFile(upLoadImageVo,imageVo,imageVoList,sort);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(ApplyRzzlDateActivity.this).showMessage("上传失败");
                            imageVoList.remove(imageVo);
                            dateBaseAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyRzzlDateActivity.this).showMessage("上传失败");
                        imageVoList.remove(imageVo);
                        dateBaseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        super.uploadProgress(progress);
                    }
                });
    }


    private void bindFile(UpLoadImageVo upLoadImageVo, final ImageVo imageVo, final List<ImageVo> imageVoList, String sort) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileObject",upLoadImageVo);
        params.put("act", sort);
        OkGo.<String>patch(URL.BIND_RONGZ_IMAGE+orderRzzlVo.getId())
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(new Gson().toJson(params).toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyRzzlDateActivity.this).showMessage("上传成功");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyRzzlDateActivity.this).showMessage("上传失败");
                        imageVoList.remove(imageVo);
                        dateBaseAdapter.notifyDataSetChanged();
                    }
                });
    }

    //图片压缩
    public void compressImage(File file, final String sort, final String sortName, final ImageVo imageVo, final List<ImageVo> imageVoList) {
        Luban.with(this)
                .putGear(10)
                .load(file)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        dialog = LoadingDialog.createLoadingDialog(ApplyRzzlDateActivity.this, "正在上传");
                        uploadFile(file,sort,sortName,imageVoList,imageVo);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();

    }

    private void choosePic(int code) {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .capture(true)
                .theme(R.style.Matisse_Light)
                .captureStrategy(new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(code);
    }

    private class DateBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return applyDateVoList.size();
        }

        @Override
        public Object getItem(int i) {
            return applyDateVoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
              DateViewHolder dateViewHolder = null;
            if (convertView == null) {
                dateViewHolder = new DateViewHolder();
                convertView = LayoutInflater.from(ApplyRzzlDateActivity.this).inflate(R.layout.apply_date_adapter_layout, null);
                dateViewHolder.sortName = convertView.findViewById(R.id.sort_name);
                dateViewHolder.sortGridView = convertView.findViewById(R.id.sortGridView);
                convertView.setTag(dateViewHolder);
            }
            dateViewHolder = (DateViewHolder) convertView.getTag();
            dateViewHolder.sortName.setText(applyDateVoList.get(position).getSortName());
            dateViewHolder.sortGridView.setAdapter(new ImageViewPageAdapter(applyDateVoList.get(position).getImageVoList()));
            dateViewHolder.sortGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        initPermission(applyDateVoList.get(position).getSortId());
                    }
                }
            });
            return convertView;
        }
    }

    class ImageViewPageAdapter extends BaseAdapter {

        List<ImageVo> imageVosList;

        public ImageViewPageAdapter(List<ImageVo> imageVosList) {
            this.imageVosList = imageVosList;
        }

        @Override
        public int getCount() {
            return imageVosList.size();
        }

        @Override
        public Object getItem(int i) {
            return imageVosList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageViewPageAdapter.ViewHolder imageViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(ApplyRzzlDateActivity.this).inflate(R.layout.add_iamge, null);
                imageViewHolder = new ImageViewPageAdapter.ViewHolder(view);
                view.setTag(imageViewHolder);
            }
            imageViewHolder = (ImageViewPageAdapter.ViewHolder) view.getTag();
            if (imageVosList.get(i).getId().equals("0")) {
                imageViewHolder.addImage.setImageResource(R.drawable.picture_sign);
            } else {
                Glide.with(ApplyRzzlDateActivity.this).load(imageVosList.get(i).getPath()).into(imageViewHolder.addImage);
            }
            return view;
        }

        class ViewHolder {
            @BindView(R.id.add_image)
            ImageView addImage;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class DateViewHolder {
        TextView sortName;
        GridView sortGridView;
    }

    @OnClick(R.id.back)
    public void onClick() {
        OkGo.getInstance().cancelTag(this);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(dialog!=null&&dialog.isShowing()){
            OkGo.getInstance().cancelTag(this);
        }
    }
}
