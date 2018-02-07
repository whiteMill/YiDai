package com.sdot.yidai.mdbtfragment.mdback;

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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.BackPlanVo;
import com.sdot.yidai.model.ImageVo;
import com.sdot.yidai.model.OverdueVo;
import com.sdot.yidai.model.UpLoadImageVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.LoadingDialog;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PlanApplyDateActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.plan_no)
    TextView planNo;
    @BindView(R.id.back_state)
    TextView backState;
    @BindView(R.id.picGridView)
    GridView picGridView;
    Intent intent;
    BackPlanVo backPlanVo;
    String loan;
    String position;
    Dialog dialog;
    @BindView(R.id.back_date)
    TextView backDate;
    @BindView(R.id.back_date_layout)
    RelativeLayout backDateLayout;

    OverdueVo overdueVo;

    List<ImageVo> backImageList = new ArrayList<>();
    ImageViewPageAdapter imageViewPageAdapter;

    private static final String TAG = "PlanApplyDateActivity";
    @BindView(R.id.back_money)
    EditText backMoney;
    TimePickerView beginpvTime;
    SimpleDateFormat sdfss = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat secondformat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    @BindView(R.id.wait_money)
    TextView waitMoney;
    @BindView(R.id.yuqi_money)
    TextView yuqiMoney;
    @BindView(R.id.yuqi_layout)
    RelativeLayout yuqiLayout;
    @BindView(R.id.benqi_money)
    TextView benqiMoney;
    @BindView(R.id.benqi_layout)
    RelativeLayout benqiLayout;
    private int id = 1;
    private List<File> fileList = new ArrayList<>();
    String recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_apply_date);
        ButterKnife.bind(this);
        title.setText("还款资料");
        intent = getIntent();
        backPlanVo = (BackPlanVo) intent.getSerializableExtra("backPlan");
        loan = intent.getStringExtra("loan");
        position = intent.getStringExtra("position");
        initPosition();
        beginpvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                backDate.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .build();
        backDate.setText(sdfss.format(new Date()));
        benqiMoney.setText(ToolUtils.formatStringNumber(backPlanVo.getLeftAmount()));

        queryState();
        queryYuQi();
        backImageList.add(new ImageVo(0 + "", "null"));
        imageViewPageAdapter = new ImageViewPageAdapter(backImageList);
        picGridView.setAdapter(imageViewPageAdapter);
        picGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    initPermission(1);
                }
            }
        });
    }

    private void initPermission(int code) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            choosePic(code);
        } else {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {

        } else {
            if (resultCode == RESULT_OK) {
                ImageVo imageVo = null;
                switch (requestCode) {
                    case 1:
                        imageVo = new ImageVo(++id + "", uriUpdate(data));
                        backImageList.add(imageVo);
                        compressImage(new File(uriUpdate(data)));
                        break;
                }
                imageViewPageAdapter.notifyDataSetChanged();
            }
        }
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
                        fileList.add(file);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();

    }

    private void uploadFile(File file, final int position) {
        OkGo.<String>post(URL.UPDATE_IMAGE).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("fileType", "image")
                .params("file", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        UpLoadImageVo upLoadImageVo = null;
                        Log.i(TAG, "onSuccess: " + response.body());
                        try {
                            jsonObject = new JSONObject(response.body());
                            upLoadImageVo = new Gson().fromJson(jsonObject.getString("result"), UpLoadImageVo.class);
                            bindFile(upLoadImageVo, position);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (position == fileList.size() - 1) {
                                LoadingDialog.closeDialog(dialog);
                                ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (position == fileList.size() - 1) {
                            LoadingDialog.closeDialog(dialog);
                            ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请失败");
                        }
                    }
                });
    }

    private void bindFile(UpLoadImageVo upLoadImageVo, final int pos) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileObject", upLoadImageVo);
        params.put("act", "uploadRepayment");
        OkGo.<String>patch(URL.BIND_BACK_IMAGE + recordId).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(new Gson().toJson(params).toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        if (pos == fileList.size() - 1) {
                            LoadingDialog.closeDialog(dialog);
                        }
                        if (response.body() == "") {
                            setResult(0x8852);
                            PlanApplyDateActivity.this.finish();
                            ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请成功");
                        } else {
                            if (pos == fileList.size() - 1) {
                                LoadingDialog.closeDialog(dialog);
                                ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: " + response.body());
                        if (pos == fileList.size() - 1) {
                            LoadingDialog.closeDialog(dialog);
                            ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请失败");
                        }
                    }
                });
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

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initPosition() {
        switch (position) {
            case "0":
                planNo.setText("第一期");
                break;
            case "1":
                planNo.setText("第二期");
                break;
            case "2":
                planNo.setText("第三期");
                break;
        }
    }

    @OnClick({R.id.back, R.id.back_date_layout, R.id.sure_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.back_date_layout:
                beginpvTime.show();
                break;
            case R.id.sure_back:
                if (ToolUtils.CheckEmpty(backMoney)) {
                    ToastUtils.getInstance(this).showMessage("请输入还款金额");
                } else if (Float.valueOf(backMoney.getText().toString().trim()) > Float.valueOf(waitMoney.getText().toString())) {
                    ToastUtils.getInstance(this).showMessage("还款金额不能大于共计应还");
                } else if (backImageList.size() == 1) {
                    ToastUtils.getInstance(this).showMessage("请上传还款凭证");
                } else {
                    dialog = LoadingDialog.createLoadingDialog(this, "请稍后");
                    applyBack();
                }
                break;
        }
    }

    private void applyBack() {
        String seTime = secondformat.format(new Date());
        String currTime = backDate.getText().toString() + " " + seTime;
        String debtorActualRepaymentDate = null;
        try {
            debtorActualRepaymentDate = sdf.format(sdf.parse(currTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<>();
        params.put("loan", "/rest/loans/" + loan);
        params.put("creditrepayplan", "/rest/creditrepayplans/" + backPlanVo.getId());
        params.put("loanSn", backPlanVo.getLoanSn());
        params.put("planSn", backPlanVo.getPlanSn());
        params.put("act", "Create");
        params.put("repaymentType", "ORDERMDBT");
        params.put("debtorName", backPlanVo.getDebtorName());
        params.put("creditcard", "/rest/creditcards/" + YDaiApplication.getInstance().getMdbtId());
        params.put("debtorActualRepaymentDate", debtorActualRepaymentDate);
        params.put("debtorRepaymentDate", backPlanVo.getDebtorRepaymentDate());
        params.put("fees", backPlanVo.getLeftAmount());
        params.put("payAmount", backMoney.getText().toString().trim());
        params.put("punishinterest", overdueVo==null?"0.00":overdueVo.getPenalSum());
        JSONObject js = new JSONObject(params);
        OkGo.<String>post(URL.MD_BACK_FEES).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(js)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        if (response.body() == "") {
                            getRecordIdVo();
                        } else {
                            LoadingDialog.closeDialog(dialog);
                            ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: "+response.body());
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请失败");
                    }
                });
    }

    private void getRecordIdVo() {
        OkGo.<String>get(URL.QUERT_NEW_RECORD).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("planId", backPlanVo.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            recordId = jsonObject.getString("id");
                            for (int i = 0; i < fileList.size(); i++) {
                                uploadFile(fileList.get(i), i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoadingDialog.closeDialog(dialog);
                            ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LoadingDialog.closeDialog(dialog);
                        ToastUtils.getInstance(PlanApplyDateActivity.this).showMessage("提交申请失败");
                    }
                });
    }

    private void queryState() {
        OkGo.<String>get("http://test.edairisk.com/rest/creditrepayplans/" + backPlanVo.getId() + "/state").tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            backState.setText(jsonObject.getString("label"));
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

    //逾期查询
    private void queryYuQi() {
        OkGo.<String>get(URL.QUERY_YUQI).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("loanId", loan)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        overdueVo = new Gson().fromJson(response.body(), OverdueVo.class);
                        yuqiMoney.setText(ToolUtils.formatStringNumber(overdueVo.getPenalSum()));
                        if (backPlanVo.getLeftAmount() == null) {
                            waitMoney.setText(ToolUtils.formatDoubleNumber(Double.valueOf(overdueVo.getPenalSum())));
                        } else {
                            waitMoney.setText(ToolUtils.formatStringNumber(Double.valueOf(backPlanVo.getLeftAmount()) + Double.valueOf(overdueVo.getPenalSum()) + ""));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        yuqiMoney.setText("0.00");
                        if (backPlanVo.getLeftAmount() == null) {
                            waitMoney.setText("0.00");
                        } else {
                            waitMoney.setText(ToolUtils.formatStringNumber(backPlanVo.getLeftAmount()));
                        }
                    }
                });
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
                view = LayoutInflater.from(PlanApplyDateActivity.this).inflate(R.layout.add_iamge, null);
                imageViewHolder = new ViewHolder(view);
                view.setTag(imageViewHolder);
            }
            imageViewHolder = (ViewHolder) view.getTag();
            if (imageVosList.get(i).getId().equals("0")) {
                imageViewHolder.addImage.setImageResource(R.drawable.picture_sign);
            } else {
                Glide.with(PlanApplyDateActivity.this).load(imageVosList.get(i).getPath()).into(imageViewHolder.addImage);
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
}
