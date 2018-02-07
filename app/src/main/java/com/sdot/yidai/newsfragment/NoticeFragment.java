package com.sdot.yidai.newsfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.model.NoticeVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {


    @BindView(R.id.noticeRecyclerView)
    RecyclerView noticeRecyclerView;
    Unbinder unbinder;

    NoticeAdapter noticeAdapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    public NoticeFragment() {

    }

    private static final String TAG = "NoticeFragment";

    private List<NoticeVo> noticeVoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        unbinder = ButterKnife.bind(this, view);
        initDate();
        noticeAdapter = new NoticeAdapter(R.layout.news_layout_adapter, noticeVoList);
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        noticeRecyclerView.setAdapter(noticeAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initDate() {
       // noticeVoList.add(new NoticeVo("2017-2-2 14:00:00", "这是个Title", "测试"));
      //  noticeVoList.add(new NoticeVo("2017-2-2 14:00:00", "这是个Title", "测试"));
      //  noticeVoList.add(new NoticeVo("2017-2-2 14:00:00", "这是个Title", "测试"));
    }

    public class NoticeAdapter extends BaseQuickAdapter<NoticeVo, BaseViewHolder> {
        public NoticeAdapter(int layoutResId, @Nullable List<NoticeVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NoticeVo item) {
          //  helper.setText(R.id.time, item.getTime());
            helper.setText(R.id.title, item.getTitle());
            helper.setText(R.id.content, item.getContent());
        }
    }


}
