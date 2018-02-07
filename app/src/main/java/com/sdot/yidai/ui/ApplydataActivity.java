package com.sdot.yidai.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.ImageVo;
import com.sdot.yidai.model.OrderwdsjshVo;
import com.sdot.yidai.model.ProductImageVo;
import com.sdot.yidai.model.UpLoadImageVo;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.weight.ScrollGridView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ApplydataActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.zhizGridView)
    ScrollGridView zhizGridView;
    @BindView(R.id.kuaidiGridView)
    ScrollGridView kuaidiGridView;
    @BindView(R.id.fadingGridView)
    ScrollGridView fadingGridView;
    @BindView(R.id.wangdianjingGridView)
    ScrollGridView wangdianjingGridView;
    @BindView(R.id.wangdainxiGridView)
    ScrollGridView wangdainxiGridView;
    @BindView(R.id.caiwubaoGridView)
    ScrollGridView caiwubaoGridView;
    @BindView(R.id.jiehunzhengGridView)
    ScrollGridView jiehunzhengGridView;
    @BindView(R.id.shenqingrenGridView)
    ScrollGridView shenqingrenGridView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.ic)
    TextView ic;
    @BindView(R.id.wangdian)
    TextView wangdian;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.address)
    TextView address;

    private int id = 1;

    private Dialog dialog;
    private static final int REQUEST_CODE_ZHIZHAO = 0x120;
    private static final int REQUEST_CODE_KUAIDI = 0x121;
    private static final int REQUEST_CODE_FAD = 0x122;
    private static final int REQUEST_CODE_WANGD = 0x123;
    private static final int REQUEST_CODE_WANGDX = 0x124;
    private static final int REQUEST_CODE_CAIWB = 0x125;
    private static final int REQUEST_CODE_JIEH = 0x126;
    private static final int REQUEST_CODE_SHENQR = 0x127;

    private static final String TAG = "ApplydataActivity";

    int[] imageArr = new int[]{R.drawable.picture_sign};

    List<ImageVo> zhiZhaoList = new ArrayList<>();
    List<ImageVo> kuaidiList = new ArrayList<>();
    List<ImageVo> fadList = new ArrayList<>();
    List<ImageVo> wangDlist = new ArrayList<>();
    List<ImageVo> wangDXList = new ArrayList<>();
    List<ImageVo> caiWbList = new ArrayList<>();
    List<ImageVo> jiehList = new ArrayList<>();
    List<ImageVo> shenQList = new ArrayList<>();

    ImageViewPageAdapter imageViewPageAdapter;
    ImageViewPageAdapter kuaiDiAdapter;
    ImageViewPageAdapter fadAdapter;
    ImageViewPageAdapter wangDAdapter;
    ImageViewPageAdapter wangDXAdapter;
    ImageViewPageAdapter caiWbAdapter;
    ImageViewPageAdapter jieHAdapter;
    ImageViewPageAdapter shenQrAdapter;

    OrderwdsjshVo orderwdsjshVo;

    private boolean isPerMission = false;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applydata);
        ButterKnife.bind(this);
        title.setText("随借随还资料");
        initDate();
        intent = getIntent();
        orderwdsjshVo = (OrderwdsjshVo) intent.getSerializableExtra("orderwdsjshVo");
        name.setText(orderwdsjshVo.getRealName());
        phone.setText(orderwdsjshVo.getApplyMobile());
        ic.setText(orderwdsjshVo.getApplyIdentityNo());
        wangdian.setText(orderwdsjshVo.getApplyEnterpriseName());
        //area.setText(orderwdsjshVo.getApplyEnterpriseProvince()+orderwdsjshVo.getApplyEnterpriseCity()+orderwdsjshVo.getApplyEnterpriseDistrict());
        if(orderwdsjshVo.getApplyEnterpriseReigionName()==null){
            area.setText(orderwdsjshVo.getApplyEnterpriseProvince()+" "+orderwdsjshVo.getApplyEnterpriseCity()+" "+orderwdsjshVo.getApplyEnterpriseDistrict());
        }else{
            area.setText(orderwdsjshVo.getApplyEnterpriseProvince()+" "+orderwdsjshVo.getApplyEnterpriseCity()+" "+orderwdsjshVo.getApplyEnterpriseDistrict()+" "+orderwdsjshVo.getApplyEnterpriseReigionName());
        }

        address.setText(orderwdsjshVo.getApplyEnterpriseProvince()+" "+orderwdsjshVo.getApplyEnterpriseCity()+" "+orderwdsjshVo.getApplyEnterpriseDistrict()+" "+orderwdsjshVo.getApplyEnterpriseAddress());
        initImage();
        zhizGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(REQUEST_CODE_ZHIZHAO);
                }
            }
        });

        kuaidiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(REQUEST_CODE_KUAIDI);
                }
            }
        });

        fadingGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(REQUEST_CODE_FAD);
                }
            }
        });


        wangdianjingGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(REQUEST_CODE_WANGD);
                }
            }
        });


        wangdainxiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(REQUEST_CODE_WANGDX);
                }
            }
        });


        caiwubaoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(REQUEST_CODE_CAIWB);
                }
            }
        });

        jiehunzhengGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(REQUEST_CODE_JIEH);
                }
            }
        });

        shenqingrenGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(REQUEST_CODE_SHENQR);
                }
            }
        });


    }

    private void initImage(){
        OkGo.<String>get(URL.GET_IMAGE+orderwdsjshVo.getId()+"/files").tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray =  new JSONObject(jsonObject.getString("_embedded")).getJSONArray("orderwdsjshFiles");
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
                    case "快递经营许可证":
                        kuaidiList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "法人、企业征信报告":
                        fadList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "网点经营场地照片":
                        wangDlist.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "网点系统截图":
                        wangDXList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "财务报表":
                        caiWbList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "结婚证":
                        jiehList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "配偶身份证":
                        shenQList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage ()));
                        break;

                }
            } catch (JSONException e) {
                e.printStackTrace();
                ToastUtils.getInstance(this).showMessage("异常");
            }
        }
        imageViewPageAdapter.notifyDataSetChanged();
        kuaiDiAdapter.notifyDataSetChanged();
        fadAdapter.notifyDataSetChanged();
        wangDAdapter.notifyDataSetChanged();
        wangDXAdapter.notifyDataSetChanged();
        caiWbAdapter.notifyDataSetChanged();
        jieHAdapter.notifyDataSetChanged();
        shenQrAdapter.notifyDataSetChanged();
    }

    private void initPermission(int code) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            choosePic(code);
        } else {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
    }


    private void initDate() {
        zhiZhaoList.add(new ImageVo(0 + "", "null"));
        imageViewPageAdapter = new ImageViewPageAdapter(zhiZhaoList);
        zhizGridView.setAdapter(imageViewPageAdapter);

        kuaidiList.add(new ImageVo(0 + "", "null"));
        kuaiDiAdapter = new ImageViewPageAdapter(kuaidiList);
        kuaidiGridView.setAdapter(kuaiDiAdapter);

        fadList.add(new ImageVo(0 + "", "null"));
        fadAdapter = new ImageViewPageAdapter(fadList);
        fadingGridView.setAdapter(fadAdapter);

        wangDlist.add(new ImageVo(0 + "", "null"));
        wangDAdapter = new ImageViewPageAdapter(wangDlist);
        wangdianjingGridView.setAdapter(wangDAdapter);

        wangDXList.add(new ImageVo(0 + "", "null"));
        wangDXAdapter = new ImageViewPageAdapter(wangDXList);
        wangdainxiGridView.setAdapter(wangDXAdapter);

        caiWbList.add(new ImageVo(0 + "", "null"));
        caiWbAdapter = new ImageViewPageAdapter(caiWbList);
        caiwubaoGridView.setAdapter(caiWbAdapter);

        jiehList.add(new ImageVo(0 + "", "null"));
        jieHAdapter = new ImageViewPageAdapter(jiehList);
        jiehunzhengGridView.setAdapter(jieHAdapter);

        shenQList.add(new ImageVo(0 + "", "null"));
        shenQrAdapter = new ImageViewPageAdapter(shenQList);
        shenqingrenGridView.setAdapter(shenQrAdapter);

    }

    @OnClick({R.id.back, R.id.zhiz, R.id.kuaid, R.id.fad, R.id.wangd, R.id.wangdx, R.id.caiwb, R.id.jieh, R.id.shenqr, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.zhiz:
                choosePic(REQUEST_CODE_ZHIZHAO);
                break;
            case R.id.kuaid:
                choosePic(REQUEST_CODE_KUAIDI);
                break;
            case R.id.fad:
                choosePic(REQUEST_CODE_FAD);
                break;
            case R.id.wangd:
                choosePic(REQUEST_CODE_WANGD);
                break;
            case R.id.wangdx:
                choosePic(REQUEST_CODE_WANGDX);
                break;
            case R.id.caiwb:
                choosePic(REQUEST_CODE_CAIWB);
                break;
            case R.id.jieh:
                choosePic(REQUEST_CODE_JIEH);
                break;
            case R.id.shenqr:
                choosePic(REQUEST_CODE_SHENQR);
                break;
            case R.id.submit:
                ToastUtils.getInstance(this).showMessage("提交成功");
                break;
        }
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

    private String uriUpdate(Intent intentDate) {
        List<Uri> mSelected = Matisse.obtainResult(intentDate);
        if (getRealFilePath(this, mSelected.get(0)) == null) {
            String path = Environment.getExternalStorageDirectory() + mSelected.get(0).getPath().replace("my_images", "Pictures");
            return path;
        } else {
            return getRealFilePath(this, mSelected.get(0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            imageViewPageAdapter.notifyDataSetChanged();
        } else {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case REQUEST_CODE_ZHIZHAO:
                        ImageVo imageVozhiZhao = new ImageVo(++id + "", uriUpdate(data));
                        zhiZhaoList.add(imageVozhiZhao);
                        imageViewPageAdapter.notifyDataSetChanged();
                        compressImage(new File(uriUpdate(data)), "uploadBusinessLicense","资产证明类", "营业执照",imageVozhiZhao,zhiZhaoList,imageViewPageAdapter);
                        break;
                    case REQUEST_CODE_KUAIDI:
                        ImageVo imageVokuaidi = new ImageVo(++id + "", uriUpdate(data));
                        kuaidiList.add(imageVokuaidi);
                        kuaiDiAdapter.notifyDataSetChanged();
                        compressImage(new File(uriUpdate(data)),"uploadExpressBusinessCertificate", "资产证明类", "快递经营许可证",imageVokuaidi,kuaidiList,kuaiDiAdapter);
                        break;
                    case REQUEST_CODE_FAD:
                        ImageVo imageVofad = new ImageVo(++id + "", uriUpdate(data));
                        fadList.add(imageVofad);
                        fadAdapter.notifyDataSetChanged();
                        compressImage(new File(uriUpdate(data)), "uploadCreditReport","资产证明类", "法人、企业征信报告",imageVofad,fadList,fadAdapter);
                        break;
                    case REQUEST_CODE_WANGD:
                        ImageVo imageVowangD = new ImageVo(++id + "", uriUpdate(data));
                        wangDlist.add(imageVowangD);
                        wangDAdapter.notifyDataSetChanged();
                        compressImage(new File(uriUpdate(data)), "uploadPlaceOfBranchPhoto","资产证明类", "网点经营场地照片",imageVowangD,wangDlist,wangDAdapter);
                        break;
                    case REQUEST_CODE_WANGDX:
                        ImageVo imageVoWangDX = new ImageVo(++id + "", uriUpdate(data));
                        wangDXList.add(imageVoWangDX);
                        wangDXAdapter.notifyDataSetChanged();
                        compressImage(new File(uriUpdate(data)),"uploadBranchScreenshot", "资产证明类", "网点系统截图",imageVoWangDX,wangDXList,wangDXAdapter);
                        break;
                    case REQUEST_CODE_CAIWB:
                        ImageVo imageVocaiWb = new ImageVo(++id + "", uriUpdate(data));
                        caiWbList.add(imageVocaiWb);
                        caiWbAdapter.notifyDataSetChanged();
                        compressImage(new File(uriUpdate(data)), "uploadCompanyAssetsList","资产证明类", "财务报表",imageVocaiWb,caiWbList,caiWbAdapter);
                        break;
                    case REQUEST_CODE_JIEH:
                        ImageVo imageVojieh = new ImageVo(++id + "", uriUpdate(data));
                        jiehList.add(imageVojieh);
                        jieHAdapter.notifyDataSetChanged();
                        compressImage(new File(uriUpdate(data)), "uploadMarriageCertificate","身份证明类", "结婚证",imageVojieh,jiehList,jieHAdapter);
                        break;
                    case REQUEST_CODE_SHENQR:
                        ImageVo imageVoshenQ = new ImageVo(++id + "", uriUpdate(data));
                        shenQList.add(imageVoshenQ);
                        shenQrAdapter.notifyDataSetChanged();
                        compressImage(new File(uriUpdate(data)), "uploadSpouseIDCard","身份证明类", "配偶身份证",imageVoshenQ,shenQList,shenQrAdapter);
                        break;
                }
            }
        }
    }

    private void uploadFile(File file, final String sortName, final String topType, final String subType, final ImageVo imageVo, final List<ImageVo> imageVoList, final ImageViewPageAdapter imageViewPageAdapter) {
        OkGo.<String>post(URL.UPDATE_IMAGE).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("fileType", "image")
                .params("file", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        UpLoadImageVo upLoadImageVo = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            upLoadImageVo = new Gson().fromJson(jsonObject.getString("result"),UpLoadImageVo.class) ;
                            upLoadImageVo.setTopcategory(topType);
                            upLoadImageVo.setSubcategory(subType);
                            bindFile(upLoadImageVo,sortName,imageVo,imageVoList,imageViewPageAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.getInstance(ApplydataActivity.this).showMessage("上传失败");
                            imageVoList.remove(imageVo);
                            imageViewPageAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplydataActivity.this).showMessage("上传失败");
                        imageVoList.remove(imageVo);
                        imageViewPageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        super.uploadProgress(progress);
                    }


                });
    }

    private void bindFile(UpLoadImageVo upLoadImageVo, String sortName,final ImageVo imageVo, final List<ImageVo> imageVoList, final ImageViewPageAdapter imageViewPageAdapter) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileObject",upLoadImageVo);
        params.put("act", sortName);
        OkGo.<String>patch(URL.BIND_IMAGE+orderwdsjshVo.getId())
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(new Gson().toJson(params).toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplydataActivity.this).showMessage("上传成功");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplydataActivity.this).showMessage("上传失败");
                        imageVoList.remove(imageVo);
                        imageViewPageAdapter.notifyDataSetChanged();
                    }
                });
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
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
            ViewHolder imageViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(ApplydataActivity.this).inflate(R.layout.add_iamge, null);
                imageViewHolder = new ViewHolder(view);
                view.setTag(imageViewHolder);
            }
            imageViewHolder = (ViewHolder) view.getTag();
            if (imageVosList.get(i).getId().equals("0")) {
                imageViewHolder.addImage.setImageResource(R.drawable.picture_sign);
            } else {
                Glide.with(ApplydataActivity.this).load(imageVosList.get(i).getPath()).into(imageViewHolder.addImage);
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

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //图片压缩
    public void compressImage(File file, final String sortName, final String top, final String sub, final ImageVo imageVo, final List<ImageVo> imageVoList, final ImageViewPageAdapter imageViewPageAdapter) {
        Luban.with(this)
                .putGear(10)
                .load(file)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        dialog = LoadingDialog.createLoadingDialog(ApplydataActivity.this, "正在上传");
                        uploadFile(file,sortName, top, sub,imageVo,imageVoList,imageViewPageAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(dialog!=null&&dialog.isShowing()){
            OkGo.getInstance().cancelTag(this);
        }
    }
}
