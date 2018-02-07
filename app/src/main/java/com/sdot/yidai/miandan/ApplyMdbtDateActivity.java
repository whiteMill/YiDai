package com.sdot.yidai.miandan;

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
import com.sdot.yidai.model.ApplyDateVo;
import com.sdot.yidai.model.ImageVo;
import com.sdot.yidai.model.OrderMdbtVo;
import com.sdot.yidai.model.ProductImageVo;
import com.sdot.yidai.model.UpLoadImageVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;
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

public class ApplyMdbtDateActivity extends BaseActivity {

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


    OrderMdbtVo orderMdbtVo;
    Intent intent;
    View headView;

    List<ImageVo> zhiZhaoList = new ArrayList<>();
    List<ImageVo> yeWuList = new ArrayList<>();
    List<ImageVo> fuQiList = new ArrayList<>();

    private int id = 1;
    List<ApplyDateVo> applyDateVoList;
    DateBaseAdapter dateBaseAdapter;
    Dialog dialog;

    private static final String TAG = "ApplyMdbtDateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_mdbt_date);
        ButterKnife.bind(this);
        title.setText("面单白条资料");
        initApplyDate();
        headView = LayoutInflater.from(this).inflate(R.layout.apply_date_headview, null);
        initView();
        dateListView.addHeaderView(headView);
        dateBaseAdapter = new DateBaseAdapter();
        dateListView.setAdapter(dateBaseAdapter);
        intent = getIntent();
        orderMdbtVo = (OrderMdbtVo) intent.getSerializableExtra("orderMdbtVo");
        name.setText(orderMdbtVo.getRealName());
        phone.setText(orderMdbtVo.getApplyMobile());
        ic.setText(orderMdbtVo.getApplyIdentityNo());
        wangdian.setText(orderMdbtVo.getApplyEnterpriseName());
        area.setText(orderMdbtVo.getApplyEnterpriseProvince() + " " + orderMdbtVo.getApplyEnterpriseCity() + " " + orderMdbtVo.getApplyEnterpriseDistrict());
        address.setText("暂无");
        initImage();
    }

    private void initImage(){
        OkGo.<String>get(URL.BIND_MDBT_IMAGE+orderMdbtVo.getId()+"/files").tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray =  new JSONObject(jsonObject.getString("_embedded")).getJSONArray("ordermdbtFiles");
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
                        yeWuList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                    case "快递业务经营许可证":
                        fuQiList.add(new ImageVo(productImageVo.getId()+"","http://files.xiaojinpingtai.com/"+productImageVo.getSmallImage()));
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                ToastUtils.getInstance(this).showMessage("异常");
            }
        }

        Collections.sort(zhiZhaoList, new ComparatorDate());
        Collections.sort(yeWuList, new ComparatorDate());
        Collections.sort(fuQiList, new ComparatorDate());

        dateBaseAdapter.notifyDataSetChanged();
    }

    private void initApplyDate() {
        zhiZhaoList.add(new ImageVo(0 + "", "null"));
        yeWuList .add(new ImageVo(0 + "", "null"));
        fuQiList.add(new ImageVo(0 + "", "null"));
        applyDateVoList = new ArrayList<>();

        applyDateVoList.add(new ApplyDateVo("120", "营业执照", zhiZhaoList));
        applyDateVoList.add(new ApplyDateVo("121", "快递业务经营许可证", yeWuList));
        applyDateVoList.add(new ApplyDateVo("122", "夫妻双方身份证", fuQiList));
    }

    private void initView() {
        name = headView.findViewById(R.id.name);
        phone = headView.findViewById(R.id.phone);
        ic = headView.findViewById(R.id.ic);
        wangdian = headView.findViewById(R.id.wangdian);
        area = headView.findViewById(R.id.area);
        address = headView.findViewById(R.id.address);
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
                        compressImage(new File(uriUpdate(data)),"uploadBusinessLicense","营业执照",imageVo,applyDateVoList.get(0).getImageVoList());
                        break;
                    case 121:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(1).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadExpressBusinessCertificate", "开户许可证",imageVo,applyDateVoList.get(1).getImageVoList());
                        break;
                    case 122:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        applyDateVoList.get(2).getImageVoList().add(imageVo);
                        compressImage(new File(uriUpdate(data)),"uploadSpouseIDCard", "快递业务经营许可证",imageVo,applyDateVoList.get(2).getImageVoList());
                        break;
                }
                dateBaseAdapter.notifyDataSetChanged();
            }
        }
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
                        dialog = LoadingDialog.createLoadingDialog(ApplyMdbtDateActivity.this, "正在上传");
                        uploadFile(file,sort,sortName,imageVoList,imageVo);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();

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
                            ToastUtils.getInstance(ApplyMdbtDateActivity.this).showMessage("上传失败");
                            imageVoList.remove(imageVo);
                            dateBaseAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyMdbtDateActivity.this).showMessage("上传失败");
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
        OkGo.<String>patch(URL.BIND_MDBT_IMAGE+orderMdbtVo.getId())
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(new Gson().toJson(params).toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyMdbtDateActivity.this).showMessage("上传成功");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(ApplyMdbtDateActivity.this).showMessage("上传失败");
                        imageVoList.remove(imageVo);
                        dateBaseAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initPermission(String code) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            choosePic(Integer.valueOf(code));
        } else {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
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
                convertView = LayoutInflater.from(ApplyMdbtDateActivity.this).inflate(R.layout.apply_date_adapter_layout, null);
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
                view = LayoutInflater.from(ApplyMdbtDateActivity.this).inflate(R.layout.add_iamge, null);
                imageViewHolder = new ImageViewPageAdapter.ViewHolder(view);
                view.setTag(imageViewHolder);
            }
            imageViewHolder = (ImageViewPageAdapter.ViewHolder) view.getTag();
            if (imageVosList.get(i).getId().equals("0")) {
                imageViewHolder.addImage.setImageResource(R.drawable.picture_sign);
            } else {
                Glide.with(ApplyMdbtDateActivity.this).load(imageVosList.get(i).getPath()).into(imageViewHolder.addImage);
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
