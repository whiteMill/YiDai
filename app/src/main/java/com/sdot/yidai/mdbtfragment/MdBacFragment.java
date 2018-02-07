package com.sdot.yidai.mdbtfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sdot.yidai.billfragment.BaseFragment;
import com.sdot.yidai.mdbtfragment.mdback.MdBackPlanActivity;
import com.sdot.yidai.model.MdBorrowRecordVo;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.weight.SpacesItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MdBacFragment extends BaseFragment {


    @BindView(R.id.backRecyclerView)
    RecyclerView recordRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.refresh)
    SmartRefreshLayout smartFresh;

    List<MdBorrowRecordVo> mdBorrowRecordVoList = new ArrayList<>();
    List<MdBorrowRecordVo> dBorrowRecordVoList = new ArrayList<>();
    ApplyRecordAdater applyRecordAdater;

    private static final String TAG = "MdBacFragment";
    @BindView(R.id.empty_data)
    TextView emptyData;

    public MdBacFragment() {
    }

    @Override
    protected void loadData() {
        smartFresh.autoRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_md_bac, container, false);
        unbinder = ButterKnife.bind(this, view);
        smartFresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getMonthUseMoney();
            }

        });
        applyRecordAdater = new ApplyRecordAdater(R.layout.md_borr_adapte_layout, mdBorrowRecordVoList);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recordRecyclerView.setAdapter(applyRecordAdater);
        recordRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        smartFresh.setEnableLoadmore(false);
     /*   applyRecordAdater.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.back_plan:

                        break;
                    case R.id.back_now:
                        break;
                }
            }
        });*/
        applyRecordAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), MdBackPlanActivity.class).putExtra("mdBorrowRecordVo", mdBorrowRecordVoList.get(position)));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //获取当月已经借款金额
    private void getMonthUseMoney() {
        OkGo.<String>get(URL.YI_MD_BORROW_RECORD).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .params("loanType", "ORDERMDBT")
                .params("stateCode", "PAYOF")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        Log.i(TAG, "onSuccess: " + response.body());
                        if (smartFresh != null) {
                            smartFresh.finishRefresh();
                        }
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
            helper.setText(R.id.no, item.getOrderNo());
            helper.setText(R.id.no_state, "已还清");
            helper.setText(R.id.jiekuan, ToolUtils.formatStringNumber(item.getPrincipal()));
            helper.setText(R.id.lixi, ToolUtils.formatDoubleNumber(Double.valueOf(item.getTotalInterest())));
            helper.setText(R.id.jieri_txt, item.getCreatedAt().split("T")[0]);
            helper.setText(R.id.huanri_txt, getDateThreeMonth(item.getCreatedAt().split("T")[0]));
            helper.getView(R.id.back_now).setVisibility(View.GONE);
           /* helper.addOnClickListener(R.id.back_plan);
            helper.addOnClickListener(R.id.back_now);*/
        }
    }

    public String getDateThreeMonth(String dat) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 90);
        String defaultStartDate = sdf.format(now.getTime());    //格式化前3月的时间
        return defaultStartDate;
    }


}
