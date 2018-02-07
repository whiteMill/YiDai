package com.sdot.yidai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.beidou.BackEduActivity;
import com.sdot.yidai.model.BackRecordVo;
import com.sdot.yidai.model.BorrowRecordVo;
import com.sdot.yidai.utils.ToolUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrentBorrowActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.dingdan_no)
    TextView dingdanNo;
    @BindView(R.id.dingdan_state)
    TextView dingdanState;
    @BindView(R.id.mincheng)
    TextView mincheng;
    @BindView(R.id.jiekuan)
    TextView jiekuan;
    @BindView(R.id.rixi)
    TextView rixi;
    @BindView(R.id.yinghuan)
    TextView yinghuan;
    @BindView(R.id.yinhuan)
    TextView yinhuan;
    @BindView(R.id.huankan_btn)
    Button huankanBtn;

    Intent intent;

    BorrowRecordVo borrowRecordVo;

    List<BackRecordVo> backRecordVoList;

    @BindView(R.id.backHis_layout)
    RelativeLayout backHisLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_borrow);
        ButterKnife.bind(this);
        title.setText("当前借款");
        intent = getIntent();
        borrowRecordVo = (BorrowRecordVo) intent.getSerializableExtra("borrowRecordVo");
        backRecordVoList = (List<BackRecordVo>) intent.getSerializableExtra("backRecordList");
        dingdanNo.setText(borrowRecordVo.getId());
        dingdanState.setText("未还清");
        mincheng.setText("随借随还");
        jiekuan.setText(ToolUtils.formatStringNumber(borrowRecordVo.getLoanAmount()) + "元");
        rixi.setText(ToolUtils.formatDoubleNumber(Double.valueOf(borrowRecordVo.getLoanAmount()) * 0.000417) + "元");

        if(backRecordVoList==null){
            yinghuan.setText(ToolUtils.formatStringNumber(borrowRecordVo.getLoanAmount()) + "元");
        }else{
            if(backRecordVoList.size()==0){
                yinghuan.setText(ToolUtils.formatStringNumber(borrowRecordVo.getLoanAmount()) + "元");
            }else{
                yinghuan.setText(ToolUtils.formatDoubleNumber(backRecordVoList.get(backRecordVoList.size()-1).getRemainAmount()) + "元");
            }
        }
        yinhuan.setText(borrowRecordVo.getLoanStartDate() + "--" + borrowRecordVo.getLoanEndDate());
    }

    @OnClick({R.id.back, R.id.huankan_btn,R.id.backHis_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.huankan_btn:
                startActivity(new Intent(this, BackEduActivity.class).putExtra("phone", borrowRecordVo.getMobile()));
                break;
            case R.id.backHis_layout:
                Bundle bundle = new Bundle();
                bundle.putSerializable("backRecordList", (Serializable) backRecordVoList);
                startActivity(new Intent(this, BackHisActivity.class).putExtras(bundle));
                break;
        }
    }

}
