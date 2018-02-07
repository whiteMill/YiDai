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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MdBorrFragment extends BaseFragment {


    @BindView(R.id.recordRecyclerView)
    RecyclerView recordRecyclerView;
    @BindView(R.id.smartFresh)
    SmartRefreshLayout smartFresh;
    Unbinder unbinder;

    private static final String TAG = "MdBorrFragment";

    List<MdBorrowRecordVo> mdBorrowRecordVoList = new ArrayList<>();
    List<MdBorrowRecordVo> dBorrowRecordVoList = new ArrayList<>();
    List<MdBorrowRecordVo> wshBorrowRecordVoList = new ArrayList<>();
    ApplyRecordAdater applyRecordAdater;
    @BindView(R.id.empty_data)
    TextView emptyData;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MdBorrFragment() {

    }

    @Override
    protected void loadData() {
        smartFresh.autoRefresh();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_md_borr, container, false);
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
        recordRecyclerView.addItemDecoration(new SpacesItemDecoration(30));
        smartFresh.setEnableLoadmore(false);
        applyRecordAdater.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.back_plan:

                        break;
                    case R.id.back_now:
                        startActivity(new Intent(getActivity(), MdBackPlanActivity.class).putExtra("mdBorrowRecordVo", mdBorrowRecordVoList.get(position)));
                        break;
                }
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
        OkGo.<String>get(URL.WEI_MD_BORROW_RECORD).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .params("loanType", "ORDERMDBT")
                .params("stateCode", "ENABLED")
                .params("remainAmount", "0")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        if (smartFresh != null) {
                            smartFresh.finishRefresh();
                        }
                        Log.i(TAG, "onSuccess: " + response.body());
                        try {
                            jsonObject = new JSONObject(response.body());
                            dBorrowRecordVoList = new Gson().fromJson(new JSONObject(jsonObject.getString("_embedded")).getString("loans"), new TypeToken<List<MdBorrowRecordVo>>() {
                            }.getType());
                            for (MdBorrowRecordVo mdBorrowRecordVo:dBorrowRecordVoList) {
                                mdBorrowRecordVo.setState("0");
                            }
                            mdBorrowRecordVoList.clear();
                            mdBorrowRecordVoList.addAll(dBorrowRecordVoList);
                            Collections.sort(mdBorrowRecordVoList,new ComparatorDate());
                            applyRecordAdater.notifyDataSetChanged();
                            if(mdBorrowRecordVoList.size()==0){
                                recordRecyclerView.setVisibility(View.INVISIBLE);
                                emptyData.setVisibility(View.VISIBLE);
                            }else{
                                recordRecyclerView.setVisibility(View.VISIBLE);
                                emptyData.setVisibility(View.INVISIBLE);
                            }
                            //getWeiShenHe();
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

    private void  getWeiShenHe(){
        OkGo.<String>get(URL.WEI_MD_BORROW_RECORD_NO).tag(this)
                .headers("cookie","session="+YDaiApplication.getInstance().getCookieValue())
                .params("userid",YDaiApplication.getInstance().getUserVo().getId())
                .params("loanType","ORDERMDBT")
                .params("stateCode","CREATED")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response.body());
                        if (smartFresh != null) {
                            smartFresh.finishRefresh();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            wshBorrowRecordVoList = new Gson().fromJson(new JSONObject(jsonObject.getString("_embedded")).getString("loans"), new TypeToken<List<MdBorrowRecordVo>>() {
                            }.getType());
                            for (MdBorrowRecordVo mdBorrowRecordVo:wshBorrowRecordVoList) {
                                mdBorrowRecordVo.setState("1");
                            }
                            mdBorrowRecordVoList.addAll(wshBorrowRecordVoList);
                            Collections.sort(mdBorrowRecordVoList,new ComparatorDate());
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
                        Log.i(TAG, "onError: "+response.body());
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

    class ComparatorDate implements Comparator {

        public int compare(Object obj1, Object obj2) {

            MdBorrowRecordVo mdBorrowRecordVo1 = (MdBorrowRecordVo) obj1;
            MdBorrowRecordVo mdBorrowRecordVo2 = (MdBorrowRecordVo) obj2;

            Date date1 = null;
            Date date2 = null;
            try {
                date1 = sdf.parse(mdBorrowRecordVo1.getUseDate());
                date2 = sdf.parse(mdBorrowRecordVo2.getUseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date1.after(date2)) {
                return -1;
            } else {
                return 1;
            }
        }
    }


    class ApplyRecordAdater extends BaseQuickAdapter<MdBorrowRecordVo, BaseViewHolder> {

        public ApplyRecordAdater(int layoutResId, @Nullable List<MdBorrowRecordVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MdBorrowRecordVo item) {
            helper.setText(R.id.no, item.getOrderNo());
            helper.setText(R.id.no_state, item.getState().equals("0")?"未还清":"审核中");
            helper.setText(R.id.jiekuan, ToolUtils.formatStringNumber(item.getPrincipal()));
            helper.setText(R.id.lixi, ToolUtils.formatDoubleNumber(Double.valueOf(item.getTotalInterest())));
            helper.setText(R.id.jieri_txt, item.getUseDate().substring(0,11));
            helper.setText(R.id.huanri_txt, getDateThreeMonth(item.getUseDate().substring(0,11)));
             TextView textView = helper.getView(R.id.back_now);
             if(item.getState().equals("0")){
                 textView.setVisibility(View.VISIBLE);
             }else{
                 textView.setVisibility(View.GONE);
             }
            helper.addOnClickListener(R.id.back_now);
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
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 89);
        String defaultStartDate = sdf.format(now.getTime());    //格式化前3月的时间
        return defaultStartDate;
    }

    private String getDatesThreeMonth(String dat) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Date d = null;
        try {
            d = sdf.parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(d);//把当前时间赋给日历
        calendar.add(calendar.MONTH, 3);  //设置为前3月
        Date dBefore = calendar.getTime();   //得到后3月的时间
        String defaultEndDate = sdf.format(dBefore); //格式化当前时间
        return defaultEndDate;
    }
}
