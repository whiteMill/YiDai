package com.sdot.yidai.billfragment;


import android.annotation.SuppressLint;
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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.BackRecordVo;
import com.sdot.yidai.model.BeiDouRecordVo;
import com.sdot.yidai.model.BorrowRecordVo;
import com.sdot.yidai.ui.CurrentBackActivity;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackFragment extends BaseFragment {

    @BindView(R.id.borrowRecyclerView)
    RecyclerView borrowRecyclerView;

    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private static final String TAG = "BorrowFragment";
    @BindView(R.id.tip_txt)
    TextView tipTxt;
    private BorrowAdapter borrowAdapter;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
    SimpleDateFormat sdfBorrow = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    private List<BeiDouRecordVo> beiDouRecordVoList = new ArrayList<>();

    public BackFragment() {
    }

    @Override
    protected void loadData() {
        refreshLayout.autoRefresh();
    }

    private List<BorrowRecordVo> borrowRecordVoList = new ArrayList<>();
    private List<BackRecordVo> backRecordVoList = new ArrayList<>();
    List<BorrowRecordVo> bborrList = new ArrayList<>();
    List<BackRecordVo> babrrList = new ArrayList<>();

    private Map<BorrowRecordVo, List<BackRecordVo>> borrowRecordVoListMap = new HashMap<>();

    private Map<BorrowRecordVo, List<BackRecordVo>> borrowMap = new HashMap<>();
    private Map<BorrowRecordVo, List<BackRecordVo>> backMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow, container, false);
        unbinder = ButterKnife.bind(this, view);
        borrowAdapter = new BorrowAdapter(R.layout.borrow_adapter_layout, borrowRecordVoList);
        borrowRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        borrowRecyclerView.setAdapter(borrowAdapter);
        borrowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("borrowRecordVo", borrowRecordVoList.get(position));
                bundle.putSerializable("backRecordList", (Serializable) backMap.get(borrowRecordVoList.get(position)));
                startActivity(new Intent(getActivity(), CurrentBackActivity.class).putExtras(bundle));
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getBorrowRecorder();
            }
        });
        refreshLayout.setEnableLoadmore(false);
        return view;
    }

    private void getBorrowRecorder() {
        OkGo.<String>get(URL.BORROW_RECORDER + YDaiApplication.getInstance().getCreditCardId() + "/loans").tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("loans");
                            beiDouRecordVoList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BeiDouRecordVo beiDouRecordVo = new Gson().fromJson(jsonArray.get(i).toString(), BeiDouRecordVo.class);
                                beiDouRecordVoList.add(beiDouRecordVo);
                            }
                            bborrList.clear();
                            for (int i = 0; i < beiDouRecordVoList.size(); i++) {
                                BorrowRecordVo borrowRecordVo = new Gson().fromJson(beiDouRecordVoList.get(i).getInformation(), BorrowRecordVo.class);
                                borrowRecordVo.setLevelId(beiDouRecordVoList.get(i).getId());
                                bborrList.add(borrowRecordVo);
                            }
                            getBackRecorder();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            borrowRecordVoList.clear();
                            backRecordVoList.clear();
                            borrowAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        borrowRecordVoList.clear();
                        backRecordVoList.clear();
                        borrowAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getBackRecorder() {
        OkGo.<String>get(URL.BACK_RECORDER + YDaiApplication.getInstance().getCreditCardId() + "/creditrepayments").tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray = new JSONObject(jsonObject.getString("_embedded")).getJSONArray("creditrepayments");
                            babrrList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BackRecordVo backRecordVo = new Gson().fromJson(jsonArray.getJSONObject(i).getString("information"), BackRecordVo.class);
                                babrrList.add(backRecordVo);
                            }
                            resolveDate(bborrList, babrrList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            borrowRecordVoList.clear();
                            backRecordVoList.clear();
                            borrowAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        borrowRecordVoList.clear();
                        backRecordVoList.clear();
                        borrowAdapter.notifyDataSetChanged();
                    }
                });
    }


    private void resolveDate(List<BorrowRecordVo> bborrList, List<BackRecordVo> babrrList) {
        borrowRecordVoListMap.clear();
        borrowMap.clear();
        backMap.clear();

        List<BackRecordVo> backResoVoList = null;
        for (BackRecordVo backRecordVo : babrrList) {
            String payTime = backRecordVo.getPayDate();
            Date date = null;
            try {
                date = sdf.parse(payTime.substring(0, 4) + "-" + payTime.substring(4, 6) + "-" + payTime.substring(6, 8) + " " + payTime.substring(8, 10) + "-" + payTime.substring(10, 12));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            backRecordVo.setPayDate(sdf.format(date));
        }
        ComparatorDate comparatorDate = new ComparatorDate();
        Collections.sort(babrrList, comparatorDate);

        for (BorrowRecordVo borrowRecordVo : bborrList) {
            backResoVoList = new ArrayList<>();
            for (BackRecordVo backRecordVo : babrrList) {
                if (backRecordVo.getLoanUuid().equals(borrowRecordVo.getId())) {
                    backResoVoList.add(backRecordVo);
                }
            }
            borrowRecordVoListMap.put(borrowRecordVo, backResoVoList);
        }

        for (int i = 0; i < borrowRecordVoListMap.size(); i++) {
            List<BackRecordVo> bakList = borrowRecordVoListMap.get(bborrList.get(i));
            if (borrowRecordVoListMap.get(bborrList.get(i)).size() == 0 || bakList.get(bakList.size() - 1).getRemainAmount() != 0) {
                borrowMap.put(bborrList.get(i), bakList);
            } else {
                backMap.put(bborrList.get(i), bakList);
            }
        }
        bborrList.clear();
        babrrList.clear();
        Iterator<BorrowRecordVo> iter = backMap.keySet().iterator();
        while (iter.hasNext()) {
            BorrowRecordVo borrowRecordVo = iter.next();
            bborrList.add(borrowRecordVo);
        }

        Collections.sort(bborrList, new ComparatorBorrowkDate());
        borrowRecordVoList.clear();
        borrowRecordVoList.addAll(bborrList);
        if(borrowRecordVoList.size()==0){
            borrowRecyclerView.setVisibility(View.GONE);
            tipTxt.setText("你还没有已经还清的借款哦");
            tipTxt.setVisibility(View.VISIBLE);
        }else{
            borrowRecyclerView.setVisibility(View.VISIBLE);
            tipTxt.setVisibility(View.GONE);
            borrowAdapter.notifyDataSetChanged();
        }
    }


    class ComparatorDate implements Comparator {

        public int compare(Object obj1, Object obj2) {

            BackRecordVo backRecordVo1 = (BackRecordVo) obj1;
            BackRecordVo backRecordVo2 = (BackRecordVo) obj2;

            Date date1 = null;
            Date date2 = null;
            try {
                date1 = sdf.parse(backRecordVo1.getPayDate());
                date2 = sdf.parse(backRecordVo2.getPayDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date1.after(date2)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    class ComparatorBorrowkDate implements Comparator {

        public int compare(Object obj1, Object obj2) {
            BorrowRecordVo borrowRecordVo1 = (BorrowRecordVo) obj1;
            BorrowRecordVo borrowRecordVo2 = (BorrowRecordVo) obj2;
            String id1 = null;
            String id2 = null;
            String time1 = borrowRecordVo1.getLevelId();
            String time2 = borrowRecordVo2.getLevelId();

            if (Integer.valueOf(time1) > Integer.valueOf(time2)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
/*
    class ComparatorBeiDouDate implements Comparator {

        public int compare(Object obj1, Object obj2) {

            BeiDouRecordVo beiDouRecordVo1 = (BeiDouRecordVo) obj1;
            BeiDouRecordVo beiDouRecordVo2 = (BeiDouRecordVo) obj2;
            String time1 = beiDouRecordVo1.getCreatedAt();
            String time2 = beiDouRecordVo2.getCreatedAt();
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = sdfBorrow.parse(time1.substring(0,4)+
                        "-"+time1.substring(5,7)+"-"+time1.substring(8,10)+" "
                        +time1.substring(11,13)+"-"+time1.substring(14,16)+"-"+time1.substring(17,19));
                date2 = sdfBorrow.parse(time2.substring(0,4)+
                        "-"+time2.substring(5,7)+"-"+time2.substring(8,10)+" "
                        +time2.substring(11,13)+"-"+time2.substring(14,16)+"-"+time2.substring(17,19));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date1.after(date2)) {
                return -1;
            } else {
                return 1;
            }
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class BorrowAdapter extends BaseQuickAdapter<BorrowRecordVo, BaseViewHolder> {
        public BorrowAdapter(int layoutResId, @Nullable List<BorrowRecordVo> data) {
            super(layoutResId, data);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        protected void convert(BaseViewHolder helper, BorrowRecordVo item) {
            helper.setText(R.id.no, item.getId());
            TextView noState = helper.getView(R.id.no_state);
            noState.setText("已还清");
            noState.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
            helper.setText(R.id.jiekuan, ToolUtils.formatStringNumber(item.getLoanAmount()));
            helper.setText(R.id.shengyu, "0.00");
            helper.setText(R.id.jieri_txt, item.getLoanStartDate());
            helper.setText(R.id.huanri_txt, item.getLoanEndDate());
        }
    }
}
