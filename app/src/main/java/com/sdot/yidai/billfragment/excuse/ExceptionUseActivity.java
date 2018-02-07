package com.sdot.yidai.billfragment.excuse;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.adapter.BillFragmentAdapter;
import com.sdot.yidai.model.CreditcardVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExceptionUseActivity extends BaseActivity implements SjshApplyFragment.OnChanageListener{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.apply_viewpager)
    ViewPager applyViewpager;
    Intent intent;
    private CreditcardVo creditcardVo;

    private BillFragmentAdapter pagerAdapter;
    SjshApplyFragment sjshApplyFragment;
    SjshApplyRecordFragment sjshApplyRecordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_use);
        ButterKnife.bind(this);
        title.setText("申请借款");
        initView();
        intent = getIntent();
        creditcardVo = (CreditcardVo) intent.getSerializableExtra("creditcardVo");
        sjshApplyFragment.initDate(creditcardVo);
        sjshApplyRecordFragment.initDate(creditcardVo);
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.back:
               EventBus.getDefault().post(new MessageEvent("beidou_finish"));
               this.finish();
               break;
       }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("beidou_finish"));
    }

    private void initView() {
        applyViewpager.setCurrentItem(0);

        sjshApplyFragment = new SjshApplyFragment();
        sjshApplyRecordFragment = new SjshApplyRecordFragment();
        sjshApplyFragment.setOnChanageListener(this);

        pagerAdapter = new BillFragmentAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(sjshApplyFragment, "借款申请");
        pagerAdapter.addFragment(sjshApplyRecordFragment, "申请记录");

        applyViewpager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(applyViewpager);
        applyViewpager.setOffscreenPageLimit(1);

        applyViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void change() {
        applyViewpager.setCurrentItem(1);
    }
}
