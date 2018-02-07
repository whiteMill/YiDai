package com.sdot.yidai.ui;

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
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.UpLoadImageVo;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.SharedPreferencesUtils;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class EditUserActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.user_image_layout)
    RelativeLayout userImageLayout;
    @BindView(R.id.nike_name)
    TextView nikeName;
    @BindView(R.id.user_name_layout)
    RelativeLayout userNameLayout;
    private boolean isPerMission = false;
    private Dialog dialog;
    private static final String TAG = "EditUserActivity";
    private static final int REQUEST_USER_IAMGE = 0x111;
    private static final int REQUEST_USER_NAME = 0x222;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
        title.setText("我的账号");
        if(YDaiApplication.getInstance().getUserVo().getNickname()==null){
           nikeName.setText(YDaiApplication.getInstance().getUserVo().getUsername());
        }else{
            nikeName.setText(YDaiApplication.getInstance().getUserVo().getNickname());
        }
        if(YDaiApplication.getInstance().getUserVo().getHeadimgurl()!=null){
            Glide.with(this).load(YDaiApplication.getInstance().getUserVo().getHeadimgurl()).into(userImage);
        }else{
            userImage.setImageResource(R.drawable.icon_head_yg);
        }
    }

    @OnClick({R.id.back, R.id.user_image_layout, R.id.user_name_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                EventBus.getDefault().post(new MessageEvent("user_info"));
                finish();
                break;
            case R.id.user_image_layout:
                initPermission();
                break;
            case R.id.user_name_layout:
                startActivityForResult(new Intent(this,UpdateUserNameActivity.class),REQUEST_USER_NAME);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("user_info"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {

        }else{
            if (resultCode == RESULT_OK){
                switch (requestCode){
                    case REQUEST_USER_IAMGE:
                        compressImage(new File(uriUpdate(data)));
                        break;
                    case REQUEST_USER_NAME:
                        if(YDaiApplication.getInstance().getUserVo().getNickname()==null){
                            nikeName.setText(YDaiApplication.getInstance().getUserVo().getUsername());
                        }else{
                            nikeName.setText(YDaiApplication.getInstance().getUserVo().getNickname());
                        }
                        break;
                }

            }
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

    private void initPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            choosePic(REQUEST_USER_IAMGE);
        } else {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
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

    //图片压缩
    public void compressImage(File file) {
        Luban.with(this)
                .putGear(10)
                .load(file)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        dialog = LoadingDialog.createLoadingDialog(EditUserActivity.this, "正在上传");
                        uploadFile(file);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();

    }

    private void uploadFile(File file){
        OkGo.<String>post(URL.UPDATE_IMAGE).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("fileType", "image")
                .params("file", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        Log.i(TAG, "onSuccess: "+response.body());
                        UpLoadImageVo upLoadImageVo = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            upLoadImageVo = new Gson().fromJson(jsonObject.getString("result"),UpLoadImageVo.class) ;
                            updateUserImage(upLoadImageVo.getNewFileName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoadingDialog.closeDialog(dialog);
                            ToastUtils.getInstance(EditUserActivity.this).showMessage("修改失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: "+response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(EditUserActivity.this).showMessage("修改失败");
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        super.uploadProgress(progress);
                    }

                });
    }

    private void updateUserImage(final String user_image){
        Map<String,String> imageMap = new HashMap<>();
        imageMap.put("headimgurl","http://files.xiaojinpingtai.com/"+user_image);
        JSONObject jsonObject  =  new JSONObject(imageMap);
        Log.i(TAG, "updateUserImage: "+new Gson().toJson(jsonObject));

        OkGo.<String>patch(URL.UPDATE_PASS+YDaiApplication.getInstance().getUserVo().getId()).tag(this)
                .headers("cookie","session="+YDaiApplication.getInstance().getCookieValue())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(EditUserActivity.this).showMessage("修改成功");
                        YDaiApplication.getInstance().getUserVo().setHeadimgurl("http://files.xiaojinpingtai.com/"+user_image);
                        SharedPreferencesUtils.setParam(EditUserActivity.this,"uservo",new Gson().toJson(YDaiApplication.getInstance().getUserVo()));
                        Glide.with(EditUserActivity.this).load("http://files.xiaojinpingtai.com/"+user_image).into(userImage);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(EditUserActivity.this).showMessage("修改失败");
                    }
                });
    }
}
