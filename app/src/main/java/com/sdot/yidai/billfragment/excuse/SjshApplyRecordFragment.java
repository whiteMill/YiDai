package com.sdot.yidai.billfragment.excuse;


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
import com.sdot.yidai.beidou.BorrowEduActivity;
import com.sdot.yidai.billfragment.BaseFragment;
import com.sdot.yidai.model.CreditcardVo;
import com.sdot.yidai.model.ExceRecordVo;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SjshApplyRecordFragment extends BaseFragment {

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.tip_txt)
    TextView tipTxt;
    @BindView(R.id.sjsh_exc_recyclerview)
    RecyclerView sjshExcRecyclerview;
    private CreditcardVo creditcardVo;

    List<ExceRecordVo> exceRecordVoList = new ArrayList<>();
    List<ExceRecordVo> exceRecordList = new ArrayList<>();
    ExceptAdapter exceptAdapter;
    private static final String TAG = "SjshApplyRecordFragment";

    public SjshApplyRecordFragment() {

    }

    @Override
    protected void loadData() {
        smartRefreshLayout.autoRefresh();
    }

    public void initDate(CreditcardVo creditcardVo) {
        this.creditcardVo = creditcardVo;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sjsh_apply_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        exceptAdapter = new ExceptAdapter(R.layout.ex_record_layout, exceRecordVoList);
        sjshExcRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        sjshExcRecyclerview.setAdapter(exceptAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                findByTwoStateCode();
            }
        });

        exceptAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (exceRecordVoList.get(position).getState().equals("CREATED")) {
                    ToastUtils.getInstance(getActivity()).showMessage("您的申请正在审核中!");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("creditcardVo", creditcardVo);
                    startActivity(new Intent(getActivity(), BorrowEduActivity.class).putExtras(bundle).putExtra("loanAmount", exceRecordVoList.get(position).getLoanAmount()));
                }
            }
        });
        EventBus.getDefault().register(this);
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "beidou_finish":
                findByTwoStateCode();
                break;
        }
    }

    //查询待审核和已通过的借款记录
    private void findByTwoStateCode() {
        OkGo.<String>get(URL.TWO_STATE_USER).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("personId", YDaiApplication.getInstance().getUserVo().getPerson().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess:" + response.body());
                        JSONObject jsonObject = null;
                        try {
                            exceRecordList.clear();
                            jsonObject = new JSONObject(response.body());
                            exceRecordList = new Gson().fromJson(new JSONObject(jsonObject.getString("_embedded")).getString("approveLoans"), new TypeToken<List<ExceRecordVo>>() {
                            }.getType());
                            if (exceRecordList.size() == 0) {
                                if (smartRefreshLayout != null) {
                                    smartRefreshLayout.finishRefresh();
                                }
                                exceRecordVoList.clear();
                                exceRecordVoList.addAll(exceRecordList);
                                exceptAdapter.notifyDataSetChanged();
                                sjshExcRecyclerview.setVisibility(View.INVISIBLE);
                                tipTxt.setVisibility(View.VISIBLE);
                            } else {
                                sjshExcRecyclerview.setVisibility(View.VISIBLE);
                                tipTxt.setVisibility(View.INVISIBLE);
                                getState(exceRecordList.get(0).getId());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (smartRefreshLayout != null) {
                                smartRefreshLayout.finishRefresh();
                            }
                            exceRecordVoList.clear();
                            exceptAdapter.notifyDataSetChanged();
                            sjshExcRecyclerview.setVisibility(View.INVISIBLE);
                            tipTxt.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (smartRefreshLayout != null) {
                            smartRefreshLayout.finishRefresh();
                        }
                        exceRecordVoList.clear();
                        exceptAdapter.notifyDataSetChanged();
                        sjshExcRecyclerview.setVisibility(View.INVISIBLE);
                        tipTxt.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void getState(String approveLoanId) {
        OkGo.<String>get("http://test.edairisk.com/rest/approveLoans/" + approveLoanId + "/approveLoanState").tag(this)
                .headers("cookie", "seesion=" + YDaiApplication.getInstance().getCookieValue())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        if (smartRefreshLayout != null) {
                            smartRefreshLayout.finishRefresh();
                        }
                        try {
                            jsonObject = new JSONObject(response.body());
                            exceRecordList.get(0).setState(jsonObject.getString("stateCode"));
                            exceRecordVoList.clear();
                            exceRecordVoList.addAll(exceRecordList);
                            exceptAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            exceRecordVoList.clear();
                            exceptAdapter.notifyDataSetChanged();
                            sjshExcRecyclerview.setVisibility(View.INVISIBLE);
                            tipTxt.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (smartRefreshLayout != null) {
                            smartRefreshLayout.finishRefresh();
                        }
                        exceRecordVoList.clear();
                        exceptAdapter.notifyDataSetChanged();
                        sjshExcRecyclerview.setVisibility(View.INVISIBLE);
                        tipTxt.setVisibility(View.VISIBLE);
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    //2018-01-09 11:57:13
    class ExceptAdapter extends BaseQuickAdapter<ExceRecordVo, BaseViewHolder> {

        public ExceptAdapter(int layoutResId, @Nullable List<ExceRecordVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ExceRecordVo item) {
            helper.setText(R.id.apply_date, item.getCreateDate().substring(0, 16));
            helper.setText(R.id.apply_amount, ToolUtils.formatStringNumber(item.getLoanAmount()));
            switch (item.getState()) {
                case "CREATED":
                    helper.setText(R.id.apply_state, "审核中");
                    break;
                case "ADOPT":
                    helper.setText(R.id.apply_state, "审核通过");
                    break;
            }


        }
    }

}
