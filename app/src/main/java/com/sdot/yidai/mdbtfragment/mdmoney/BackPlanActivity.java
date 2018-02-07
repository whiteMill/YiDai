package com.sdot.yidai.mdbtfragment.mdmoney;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.ui.BaseActivity;
import com.sdot.yidai.utils.ToolUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Internal;

public class BackPlanActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.first_txt)
    TextView firstTxt;
    @BindView(R.id.first_money)
    TextView firstMoney;
    @BindView(R.id.second_txt)
    TextView secondTxt;
    @BindView(R.id.second_money)
    TextView secondMoney;
    @BindView(R.id.third_txt)
    TextView thirdTxt;
    @BindView(R.id.third_money)
    TextView thirdMoney;

    Intent intent;
    String total;
    String lilv;
    String date;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_plan);
        ButterKnife.bind(this);
        title.setText("还款计划");
        intent =getIntent();
        total = intent.getStringExtra("total");
        lilv  = intent.getStringExtra("lilv");
        date = intent.getStringExtra("date");
        try {
            firstTxt.setText(getDateOneMonth(sdf.parse(date))+" 前应还利息");
            secondTxt.setText(getDateTwoMonth(sdf.parse(date))+" 前应还利息");
            thirdTxt.setText(getDateThreeMonth(sdf.parse(date))+" 前应还利息本金");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        firstMoney.setText(ToolUtils.formatDoubleNumber(Double.valueOf(total)*Double.valueOf(lilv)/100/12)+"元");
        secondMoney.setText(ToolUtils.formatDoubleNumber(Double.valueOf(total)*Double.valueOf(lilv)/100/12)+"元");
        thirdMoney.setText(ToolUtils.formatDoubleNumber(Double.valueOf(total)*Double.valueOf(lilv)/100/12+Double.valueOf(total))+"元");
    }

    @OnClick(R.id.back)
    public void onClick() {
        this.finish();
    }

    public  String getDateOneMonth(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 29);
        String defaultStartDate = sdf.format(now.getTime());    //格式化前3月的时间
        return defaultStartDate;
    }

    public  String getDateTwoMonth(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 59);
        String defaultStartDate = sdf.format(now.getTime());    //格式化前3月的时间
        return  defaultStartDate;
    }

    public  String getDateThreeMonth(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 89);
        String defaultStartDate = sdf.format(now.getTime());    //格式化前3月的时间
        return  defaultStartDate;
    }
}
