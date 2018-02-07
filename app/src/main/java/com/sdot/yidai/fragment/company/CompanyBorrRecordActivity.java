package com.sdot.yidai.fragment.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.MdBorrowRecordVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.weight.SpacesItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyBorrRecordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.empty_data)
    TextView emptyData;
    @BindView(R.id.recordRecyclerView)
    RecyclerView recordRecyclerView;
    @BindView(R.id.smartFresh)
    SmartRefreshLayout smartFresh;
    Intent intent;
    String companyId;
    private static final String TAG = "CompanyBorrRecordActivi";
    List<MdBorrowRecordVo> mdBorrowRecordVoList = new ArrayList<>();
    List<MdBorrowRecordVo> dBorrowRecordVoList = new ArrayList<>();
    ApplyRecordAdater applyRecordAdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_borr_record);
        ButterKnife.bind(this);
        title.setText("公司借款记录");
        intent = getIntent();
        companyId = intent.getStringExtra("companyId");
        smartFresh.autoRefresh();
        smartFresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getCompanyRecord();
            }
        });
        smartFresh.setEnableLoadmore(false);
        applyRecordAdater = new ApplyRecordAdater(R.layout.company_record_adapter_layout, mdBorrowRecordVoList);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordRecyclerView.setAdapter(applyRecordAdater);
        recordRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
    }


    @OnClick(R.id.back)
    public void onClick() {
        this.finish();
    }

    private void getCompanyRecord(){
        OkGo.<String>get(URL.QUERY_COMPANY_BORR_RECORD).tag(this)
                .headers("cookie","session="+ YDaiApplication.getInstance().getCookieValue())
                .params("companyId",companyId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        JSONObject jsonObject = null;
                        if (smartFresh != null) {
                            smartFresh.finishRefresh();
                        }
                        Log.i(TAG, "onSuccess: " + response.body());
                        try {
                            jsonObject = new JSONObject(response.body());
                            dBorrowRecordVoList = new Gson().fromJson(new JSONObject(jsonObject.getString("_embedded")).getString("loans"), new TypeToken<List<MdBorrowRecordVo>>() {
                            }.getType());
                            mdBorrowRecordVoList.clear();
                            mdBorrowRecordVoList.addAll(dBorrowRecordVoList);
                            applyRecordAdater.notifyDataSetChanged();
                            if(mdBorrowRecordVoList.size()==0){
                                recordRecyclerView.setVisibility(View.INVISIBLE);
                                emptyData.setVisibility(View.VISIBLE);
                            }else{
                                recordRecyclerView.setVisibility(View.VISIBLE);
                                emptyData.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mdBorrowRecordVoList.clear();
                            applyRecordAdater.notifyDataSetChanged();
                            recordRecyclerView.setVisibility(View.INVISIBLE);
                            emptyData.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (smartFresh != null) {
                            smartFresh.finishRefresh();
                        }
                        mdBorrowRecordVoList.clear();
                        applyRecordAdater.notifyDataSetChanged();
                        recordRecyclerView.setVisibility(View.INVISIBLE);
                        emptyData.setVisibility(View.VISIBLE);
                    }
                });
    }

    class ApplyRecordAdater extends BaseQuickAdapter<MdBorrowRecordVo, BaseViewHolder> {

        public ApplyRecordAdater(int layoutResId, @Nullable List<MdBorrowRecordVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MdBorrowRecordVo item) {
            helper.setText(R.id.caozuoren, item.getDebtorName());
            helper.setText(R.id.principal, ToolUtils.formatStringNumber(item.getPrincipal()));
            helper.setText(R.id.totalInterest, item.getApplyDate().split("T")[0]);
            helper.setText(R.id.apply_date, item.getUseDate().split("T")[0]);
        }
    }
}
