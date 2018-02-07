package com.sdot.yidai.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.NoticeVo;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.badgeview.BGABadgeView;

public class NewsCenterActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.newsRecycleview)
    RecyclerView newsRecycleview;
    @BindView(R.id.newsfreshLayout)
    SmartRefreshLayout newsfreshLayout;
    Unbinder unbinder;
    @BindView(R.id.tip_txt)
    TextView tipTxt;

    private List<NoticeVo> noticeVoList = new ArrayList<>();
    private List<NoticeVo> dateVoList = new ArrayList<>();

    NoticeAdapter noticeAdapter;
    private ProductStateVo productStateVo;
    boolean isFinish = false;

    private static final String TAG = "NewsCenterActivity";
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_center);
        ButterKnife.bind(this);
        title.setText("消息中心");
        noticeAdapter = new NoticeAdapter(R.layout.news_layout_adapter, noticeVoList);
        newsRecycleview.setLayoutManager(new LinearLayoutManager(this));
        newsRecycleview.setAdapter(noticeAdapter);
        newsfreshLayout.autoRefresh();

        newsfreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (YDaiApplication.getInstance().isLoginsState()) {
                    index = 0;
                    noticeVoList.clear();
                    initDate(index);
                }
            }
        });
        newsfreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                  index++;
                  initDate(index);
            }
        });

       // newsfreshLayout.setEnableLoadmore(false);
        noticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //  if(isFinish){
                if (!noticeVoList.get(position).getReading()) {
                    changeNewsState(noticeVoList.get(position).getId(), position);
                }
                   /* switch (noticeVoList.get(position).getLabel()){
                        case "网点随借随还":
                            if(noticeVoList.get(position).getToPage().equals("1")){
                                startActivity(new Intent(NewsCenterActivity.this, SjshStateActivity.class));
                            }else{
                                startActivity(new Intent(NewsCenterActivity.this, SjshCardActivity.class));
                            }
                            break;
                        case "融资租赁":
                            if(noticeVoList.get(position).getToPage().equals("1")){
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("rzzlVo", productStateVo.getRzzl());
                                startActivity(new Intent(NewsCenterActivity.this, RzzlStateActivity.class).putExtras(bundle));
                            }else{
                                startActivity(new Intent(NewsCenterActivity.this, RzzlCardActivity.class));
                            }
                            break;
                        case "网点信用贷":
                            if(noticeVoList.get(position).getToPage().equals("1")){
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("wdxydVo", wdxydVo);
                                startActivity(new Intent(NewsCenterActivity.this, WdxyStateActivity.class).putExtras(bundle));
                            }else{
                                startActivity(new Intent(NewsCenterActivity.this, WdxyCardActivity.class));
                            }
                            break;
                    }
                }else{
                    ToastUtils.getInstance(NewsCenterActivity.this).showMessage("服务器异常");
                }*/
            }
        });
        // getProductSort();
    }

    private void getProductSort() {
        OkGo.<String>get(URL.GET_SORT_PRODUCT).tag(this)
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            String errorCode = jsonObject.getString("ErrorCode");
                            if (errorCode.equals("0")) {
                                productStateVo = new Gson().fromJson(jsonObject.getString("data"), ProductStateVo.class);
                                if (productStateVo.getSjsh().getTotal() == null) {
                                    isFinish = false;
                                } else {
                                    float total_money = Float.valueOf(productStateVo.getSjsh().getTotal());
                                    if (total_money == 0) {
                                        isFinish = false;
                                    } else {
                                        isFinish = true;
                                        YDaiApplication.getInstance().setCreditCardId(productStateVo.getSjsh().getCardId());
                                    }
                                }
                            } else {
                                isFinish = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            isFinish = false;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        isFinish = false;
                    }
                });

    }

    private void changeNewsState(String newsId, final int position) {
        Map<String, String> stateMap = new HashMap<>();
        stateMap.put("reading", "true");
        JSONObject jsonObject = new JSONObject(stateMap);
        OkGo.<String>patch(URL.CHANGE_NEWS_STATE + newsId).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        noticeVoList.get(position).setReading(true);
                        noticeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        noticeVoList.get(position).setReading(false);
                        noticeAdapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick(R.id.back)
    public void onClick() {
        EventBus.getDefault().post(new MessageEvent("news_refresh"));
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("news_refresh"));
    }

    private void initDate(int page) {
        OkGo.<String>get(URL.GET_MESSAGE).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .params("sort", "id,desc")
                .params("page",page+"")
                .params("size","10")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        freshGone();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            dateVoList.clear();
                            dateVoList = new Gson().fromJson(jsonObject.getJSONObject("_embedded").getString("appMessages"), new TypeToken<List<NoticeVo>>(){}.getType());
                            if(index!=0&&dateVoList.size()==0){
                                    index--;
                            }
                            noticeVoList.addAll(dateVoList);
                            noticeAdapter.notifyDataSetChanged();
                            tipsVisible();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            tipsVisible();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        freshGone();
                        if(index!=0){
                           index--;
                        }
                        tipsVisible();
                        ToastUtils.getInstance(NewsCenterActivity.this).showMessage("服务器请求失败");
                    }
                });
    }

    private void freshGone(){
        if (newsfreshLayout != null) {
            newsfreshLayout.finishRefresh();
            newsfreshLayout.finishLoadmore();
        }
    }

    private void tipsVisible(){
        if(noticeVoList.size()==0){
            tipTxt.setVisibility(View.VISIBLE);
            newsRecycleview.setVisibility(View.INVISIBLE);
        }else{
            tipTxt.setVisibility(View.INVISIBLE);
            newsRecycleview.setVisibility(View.VISIBLE);
        }
    }

    public class NoticeAdapter extends BaseQuickAdapter<NoticeVo, BaseViewHolder> {
        public NoticeAdapter(int layoutResId, @Nullable List<NoticeVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NoticeVo item) {
            helper.setText(R.id.time, item.getPushTime().substring(0,16));
            helper.setText(R.id.title, item.getTitle());
            helper.setText(R.id.content, item.getContent());
            BGABadgeView bgaBadgeView = helper.getView(R.id.red_dot);
            //bgaBadgeView.showCirclePointBadge();
            if (!item.getReading()) {
                bgaBadgeView.showCirclePointBadge();
            } else {
                bgaBadgeView.hiddenBadge();
            }
        }
    }
}
