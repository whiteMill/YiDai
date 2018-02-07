package com.sdot.yidai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.beidou.BorrowEduActivity;
import com.sdot.yidai.model.CreditcardVo;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyBorrowActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.jiekaun_time)
    TextView jiekaunTime;
    @BindView(R.id.huankaun_qixian)
    TextView huankaunQixian;
    @BindView(R.id.rixi)
    TextView rixi;
    @BindView(R.id.tixian_money)
    EditText tixianMoney;
    @BindView(R.id.button)
    Button button;

    private Intent intent;

    private CreditcardVo creditcardVo;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_borrow);
        ButterKnife.bind(this);
        title.setText("提现申请");
        intent = getIntent();
        creditcardVo = (CreditcardVo) intent.getSerializableExtra("creditcardVo");
        jiekaunTime.setText(sdf.format(new Date()));

        tixianMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    rixi.setText(ToolUtils.formatDoubleNumber(Double.valueOf(editable.toString())*0.000417)+"元");
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                EventBus.getDefault().post(new MessageEvent("beidou_finish"));
                this.finish();
                break;
            case R.id.button:
                if(ToolUtils.CheckEmpty(tixianMoney)){
                    ToastUtils.getInstance(this).showMessage("请输入金额");
                }else if(Double.valueOf(tixianMoney.getText().toString())<1000){
                    ToastUtils.getInstance(this).showMessage("提现金额要大于1000元");
                }else  if(Double.valueOf(creditcardVo.getCreditBalance())<Double.valueOf(tixianMoney.getText().toString())){
                    ToastUtils.getInstance(this).showMessage("提现金额不应大于账户剩余额度");
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("creditcardVo",creditcardVo);
                    startActivity(new Intent(this,BorrowEduActivity.class).putExtras(bundle).putExtra("loanAmount",tixianMoney.getText().toString()));
                }
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("beidou_finish"));
    }
}
