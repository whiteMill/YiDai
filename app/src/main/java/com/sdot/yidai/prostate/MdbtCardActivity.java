package com.sdot.yidai.prostate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.adapter.BillFragmentAdapter;
import com.sdot.yidai.mdbtfragment.MdBacFragment;
import com.sdot.yidai.mdbtfragment.MdBorrFragment;
import com.sdot.yidai.mdbtfragment.MdEduFragment;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MdbtCardActivity extends BaseActivity {

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
    private MdEduFragment mdEduFragment;
    private MdBorrFragment mdBorrFragment;
    private MdBacFragment mdBacFragment;
    private ProductStateVo.MdbtVo mdbtVo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdbt_card);
        ButterKnife.bind(this);
         intent = getIntent();
         mdbtVo = (ProductStateVo.MdbtVo) intent.getSerializableExtra("mdbtVo");
        title.setText("面单白条");
        initView();
    }

    @OnClick(R.id.back)
    public void onClick() {
        EventBus.getDefault().post(new MessageEvent("refresh"));
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("refresh"));
    }

    private void initView() {
        billViewpager.setCurrentItem(0);

        mdEduFragment = new MdEduFragment();
        mdBorrFragment = new MdBorrFragment();
        mdBacFragment = new MdBacFragment();

        pagerAdapter = new BillFragmentAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(mdEduFragment, "额度管理");
        pagerAdapter.addFragment(mdBorrFragment, "当前借款");
        pagerAdapter.addFragment(mdBacFragment, "历史借款");

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
