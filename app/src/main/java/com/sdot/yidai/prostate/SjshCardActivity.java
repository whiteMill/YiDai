package com.sdot.yidai.prostate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.adapter.BillFragmentAdapter;
import com.sdot.yidai.billfragment.BackFragment;
import com.sdot.yidai.billfragment.BorrowFragment;
import com.sdot.yidai.billfragment.EduFragment;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.MessageEvent;
import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SjshCardActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.bill_viewpager)
    ViewPager billViewpager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private Intent intent;


    private BillFragmentAdapter pagerAdapter;
    private EduFragment eduFragment;
    private BorrowFragment borrowFragment;
    private BackFragment backFragment;
    private ProductStateVo.SjshVo sjshVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjsh_card);
        ButterKnife.bind(this);
        title.setText("随借随还");
        intent = getIntent();
        sjshVo = (ProductStateVo.SjshVo) intent.getSerializableExtra("sjshVo");
        initView();

    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                EventBus.getDefault().post(new MessageEvent("refresh"));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("refresh"));
    }

    private void initView() {
        billViewpager.setCurrentItem(0);

        eduFragment = new EduFragment();
        borrowFragment = new BorrowFragment();
        backFragment = new BackFragment();

        pagerAdapter = new BillFragmentAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(eduFragment, "额度管理");
        pagerAdapter.addFragment(borrowFragment, "当前借款");
        pagerAdapter.addFragment(backFragment, "历史借款");

        billViewpager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(billViewpager);
        billViewpager.setOffscreenPageLimit(2);

        billViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


}
