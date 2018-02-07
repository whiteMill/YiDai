package com.sdot.yidai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdot.yidai.R;
import com.sdot.yidai.model.BackRecordVo;
import com.sdot.yidai.utils.ToolUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackHisActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.his_recycle)
    RecyclerView hisRecycle;

    Intent intent;

    List<BackRecordVo> backRecordVoList = null;

    HisAdapter hisAdapter;
    @BindView(R.id.tip_txt)
    TextView tipTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_his);
        ButterKnife.bind(this);
        title.setText("还款历史");
        intent = getIntent();
        backRecordVoList = (List<BackRecordVo>) intent.getSerializableExtra("backRecordList");
        hisAdapter = new HisAdapter(R.layout.his_adapter_layout, backRecordVoList);
        hisRecycle.setLayoutManager(new LinearLayoutManager(this));
        hisRecycle.setAdapter(hisAdapter);
        if(backRecordVoList.size()==0){
            tipTxt.setVisibility(View.VISIBLE);
            hisRecycle.setVisibility(View.INVISIBLE);
        }else{
            tipTxt.setVisibility(View.INVISIBLE);
            hisRecycle.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.back)
    public void onClick() {
        this.finish();
    }


    public class HisAdapter extends BaseQuickAdapter<BackRecordVo, BaseViewHolder> {
        public HisAdapter(int layoutResId, @Nullable List<BackRecordVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BackRecordVo item) {
            helper.setText(R.id.his_money, ToolUtils.formatStringNumber(String.valueOf(item.getPayAmount())));
            helper.setText(R.id.his_time, item.getPayDate());

        }
    }
}
