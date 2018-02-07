package com.sdot.yidai.fragment;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.model.ProductStateVo;
import com.sdot.yidai.model.ProductVo;
import com.sdot.yidai.model.UserVo;
import com.sdot.yidai.prostate.MdbtCardActivity;
import com.sdot.yidai.prostate.MdbtStateActivity;
import com.sdot.yidai.prostate.RzzlCardActivity;
import com.sdot.yidai.prostate.RzzlStateActivity;
import com.sdot.yidai.prostate.SjshCardActivity;
import com.sdot.yidai.prostate.SjshStateActivity;
import com.sdot.yidai.prostate.WdxyCardActivity;
import com.sdot.yidai.prostate.WdxyStateActivity;
import com.sdot.yidai.ui.LoginActivity;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.ScreenUtil;
import com.sdot.yidai.utils.SharedPreferencesUtils;
import com.sdot.yidai.utils.ToastUtils;
import com.sdot.yidai.utils.ToolUtils;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.weight.EduLineView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCardFragment extends Fragment {


    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.mRefresh)
    SmartRefreshLayout mRefresh;
    Unbinder unbinder;

    List<ProductVo> productVoList = new ArrayList<>();
    List<ProductVo> productList = new ArrayList<>();

    private static final String TAG = "ProductCardFragment";
    @BindView(R.id.login_layout)
    RelativeLayout logoLayout;
    @BindView(R.id.paddingView)
    View paddingView;
    @BindView(R.id.pic_null_layout)
    RelativeLayout picNullLayout;
    private ProductStateVo productStateVo;

    ProductStateVo.SjshVo sjshVo;
    ProductStateVo.RzzlVo rzzlVo;
    ProductStateVo.WdxydVo wdxydVo;
    ProductStateVo.MdbtVo mdbtVo;
    ProductStateVo.CompanyMDBT companyMDBT;

    //CardAdapter cardAdapter;
    CarMulAdapter carMulAdapter;

    public ProductCardFragment() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "user_login":
                allGone();
                mRefresh.autoRefresh();
                break;
            case "user_exit":
                loginVisible();
                break;
            case "refresh":
                getProductSort();
                break;
        }
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_card, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        EventBus.getDefault().register(this);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) paddingView.getLayoutParams();
        layoutParams.height = getStatusBarHeight();
        paddingView.setLayoutParams(layoutParams);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
       // cardAdapter = new CardAdapter(R.layout.card_pass_adapter_layout, productVoList);
        carMulAdapter = new CarMulAdapter(productVoList);
        mRecycleView.setAdapter(carMulAdapter);
        if (YDaiApplication.getInstance().isLoginsState()) {
            mRefresh.autoRefresh();
        } else {
            loginVisible();
        }
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (YDaiApplication.getInstance().isLoginsState()) {
                    getIsBoss();
                    initPerson();
                } else {
                    mRefresh.finishRefresh();
                    loginVisible();
                }
            }
        });

        mRefresh.setEnableLoadmore(false);
        carMulAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (productVoList.get(position).getCardSort()) {
                    case "sjsh":
                        if (sjshVo.getState() == null) {
                            ToastUtils.getInstance(getActivity()).showMessage("请先申请随借随还");
                        } else {
                            if (sjshVo.getCardId() == null) {
                                startActivity(new Intent(getActivity(), SjshStateActivity.class));
                            } else {
                                startActivity(new Intent(getActivity(), SjshCardActivity.class));
                            }
                        }
                        break;

                    case "mdbt":
                        if (YDaiApplication.getInstance().getCompanyRole().equals("null")) {
                            if (mdbtVo.getState() == null) {
                                ToastUtils.getInstance(getActivity()).showMessage("请先申请面单白条");
                            } else {
                                if (mdbtVo.getCardId() == null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("mdbtVo", mdbtVo);
                                    startActivity(new Intent(getActivity(), MdbtStateActivity.class).putExtras(bundle));
                                } else {
                                    startActivity(new Intent(getActivity(), MdbtCardActivity.class));
                                }
                            }
                        } else if (YDaiApplication.getInstance().getCompanyRole().equals("manager")) {
                            if (mdbtVo.getState() == null) {
                                ToastUtils.getInstance(getActivity()).showMessage("请先申请面单白条");
                            } else {
                                if (mdbtVo.getCardId() == null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("mdbtVo", mdbtVo);
                                    startActivity(new Intent(getActivity(), MdbtStateActivity.class).putExtras(bundle));
                                } else {
                                    startActivity(new Intent(getActivity(), MdbtCardActivity.class));
                                }
                            }
                        } else {
                            if (companyMDBT != null) {
                                if (companyMDBT.getState() == null) {
                                    ToastUtils.getInstance(getActivity()).showMessage("请先申请面单白条");
                                } else {
                                    if (companyMDBT.getCardId() == null) {
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("mdbtVo", companyMDBT);
                                        startActivity(new Intent(getActivity(), MdbtStateActivity.class).putExtras(bundle));
                                    } else {
                                        startActivity(new Intent(getActivity(), MdbtCardActivity.class));
                                    }
                                }
                            } else {
                                ToastUtils.getInstance(getActivity()).showMessage("请先申请面单白条");
                            }

                        }

                        break;

                    case "wdxyd":
                        if (wdxydVo.getState() == null) {
                            ToastUtils.getInstance(getActivity()).showMessage("请先申请网点信用贷");
                        } else {
                            if (wdxydVo.getCardId() == null) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("wdxydVo", wdxydVo);
                                startActivity(new Intent(getActivity(), WdxyStateActivity.class).putExtras(bundle));
                            } else {
                                startActivity(new Intent(getActivity(), WdxyCardActivity.class));
                            }
                        }
                        break;
                    case "rzzl":
                        if (rzzlVo.getState() == null) {
                            ToastUtils.getInstance(getActivity()).showMessage("请先申请融资租赁");
                        } else {
                            if (rzzlVo.getCardId() == null) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("rzzlVo", rzzlVo);
                                startActivity(new Intent(getActivity(), RzzlStateActivity.class).putExtras(bundle));
                            } else {
                                startActivity(new Intent(getActivity(), RzzlCardActivity.class));
                            }
                        }
                        break;
                }
            }
        });
        return view;
    }

    /**
     * 获取当前设备状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void reFreshView() {
        if (YDaiApplication.getInstance().isLoginsState()) {
            getProductSort();
        } else {
            loginVisible();
        }
    }

    private void loginVisible() {
        logoLayout.setVisibility(View.VISIBLE);
        mRecycleView.setVisibility(View.INVISIBLE);
        picNullLayout.setVisibility(View.INVISIBLE);
    }

    private void freshVisible() {
        logoLayout.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    private void allGone() {
        logoLayout.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.INVISIBLE);
        picNullLayout.setVisibility(View.INVISIBLE);
    }

    private void initDate(ProductStateVo mProductStateVo) {
        sjshVo = mProductStateVo.getSjsh();
        wdxydVo = mProductStateVo.getWdxyd();
        rzzlVo = mProductStateVo.getRzzl();
        mdbtVo = mProductStateVo.getMdbt();
        companyMDBT = mProductStateVo.getCompanyMDBT();

        UserVo userVo = YDaiApplication.getInstance().getUserVo();
        Log.i(TAG, "initDate: " + userVo);

        productList.clear();
        productList.add(new ProductVo("sjsh",
                mProductStateVo.getSjsh().getState(),
                mProductStateVo.getSjsh().getReamin(),
                mProductStateVo.getSjsh().getTotal(),
                mProductStateVo.getSjsh().isApply(),
                mProductStateVo.getSjsh().getCardId()));


        if (YDaiApplication.getInstance().getCompanyRole().equals("null")) {
            productList.add(new ProductVo("mdbt",
                    mProductStateVo.getMdbt().getState(),
                    mProductStateVo.getMdbt().getReamin(),
                    mProductStateVo.getMdbt().getTotal(),
                    mProductStateVo.getMdbt().isApply(),
                    mProductStateVo.getMdbt().getCardId()));
        } else if (YDaiApplication.getInstance().getCompanyRole().equals("manager")) {
            productList.add(new ProductVo("mdbt",
                    mProductStateVo.getMdbt().getState(),
                    mProductStateVo.getMdbt().getReamin(),
                    mProductStateVo.getMdbt().getTotal(),
                    mProductStateVo.getMdbt().isApply(),
                    mProductStateVo.getMdbt().getCardId()));
        } else {
            if (mProductStateVo.getCompanyMDBT() != null) {
                productList.add(new ProductVo("mdbt",
                        mProductStateVo.getCompanyMDBT().getState(),
                        mProductStateVo.getCompanyMDBT().getReamin(),
                        mProductStateVo.getCompanyMDBT().getTotal(),
                        mProductStateVo.getCompanyMDBT().isApply(),
                        mProductStateVo.getCompanyMDBT().getCardId()));
            } else {
                productList.add(new ProductVo("mdbt",
                        mProductStateVo.getMdbt().getState(),
                        mProductStateVo.getMdbt().getReamin(),
                        mProductStateVo.getMdbt().getTotal(),
                        mProductStateVo.getMdbt().isApply(),
                        mProductStateVo.getMdbt().getCardId()));
            }
        }

        productList.add(new ProductVo("wdxyd",
                mProductStateVo.getWdxyd().getState(),
                mProductStateVo.getWdxyd().getReamin(),
                mProductStateVo.getWdxyd().getTotal(),
                mProductStateVo.getWdxyd().isApply(),
                mProductStateVo.getWdxyd().getCardId()));

        productList.add(new ProductVo("rzzl",
                mProductStateVo.getRzzl().getState(),
                mProductStateVo.getRzzl().getReamin(),
                mProductStateVo.getRzzl().getTotal(),
                mProductStateVo.getRzzl().isApply(),
                mProductStateVo.getRzzl().getCardId()));

        productVoList.clear();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getState() != null) {
                productVoList.add(productList.get(i));
            }
        }

        for (ProductVo pro:productVoList) {
              if(pro.getCardId()==null){
                  pro.setItemType(ProductVo.UNPASS);
              }else{
                  pro.setItemType(ProductVo.PASS);
              }
        }

        if(productVoList.size()==0){
            picNullLayout.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.INVISIBLE);
        }else{
            picNullLayout.setVisibility(View.INVISIBLE);
            mRecycleView.setVisibility(View.VISIBLE);
        }
        carMulAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initPerson() {
        OkGo.<String>get(URL.INIT_PERSON).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("ErrorCode").equals("0")) {
                                YDaiApplication.getInstance().getUserVo().setPerson(new UserVo.PersonVo(jsonObject.getInt("date")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void getProductSort() {
        OkGo.<String>get(URL.GET_SORT_PRODUCT).tag(this)
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (mRefresh != null) {
                            mRefresh.finishRefresh();
                        }
                        Log.i(TAG, "onSuccess: " + response.body());
                        JSONObject jsonObject = null;
                        freshVisible();
                        try {
                            jsonObject = new JSONObject(response.body());
                            productStateVo = new Gson().fromJson(jsonObject.getString("data"), ProductStateVo.class);
                            initDate(productStateVo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            allGone();
                            productVoList.clear();
                            carMulAdapter.notifyDataSetChanged();
                            ToastUtils.getInstance(getActivity()).showMessage("数据异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mRefresh != null) {
                            mRefresh.finishRefresh();
                        }
                        allGone();
                        productVoList.clear();
                        carMulAdapter.notifyDataSetChanged();
                        ToastUtils.getInstance(getActivity()).showMessage("网络请求超时");
                    }
                });

    }

    private void getIsBoss() {
        OkGo.<String>get(URL.QUERU_IS_BOSS).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("ErrorCode").equals("0")) {
                                YDaiApplication.getInstance().setCompanyRole(jsonObject.getString("data"));
                                SharedPreferencesUtils.setParam(getActivity(), "companyRole", jsonObject.getString("data"));
                                getProductSort();
                            } else {
                                ToastUtils.getInstance(getActivity()).showMessage(jsonObject.getString("ErrorInfo"));
                                productVoList.clear();
                                carMulAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            productVoList.clear();
                            carMulAdapter.notifyDataSetChanged();
                            ToastUtils.getInstance(getActivity()).showMessage("数据异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: " + response.body());
                        ToastUtils.getInstance(getActivity()).showMessage("网络请求超时");
                        productVoList.clear();
                        carMulAdapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick(R.id.login_first)
    public void onClick() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    class CardAdapter extends BaseQuickAdapter<ProductVo, BaseViewHolder> {

        public CardAdapter(int layoutResId, @Nullable List<ProductVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductVo item) {
            ImageView cardState = helper.getView(R.id.card_state);
            TextView moneyApply = helper.getView(R.id.money_apply);
            EduLineView eduLineView = helper.getView(R.id.eduLine);
            ImageView slidePic = helper.getView(R.id.slidePic);
            RelativeLayout cardLayout = helper.getView(R.id.card_layout);
            switch (item.getCardSort()) {
                case "sjsh":
                    helper.setText(R.id.protuct_title, "随借随还");
                    helper.setImageResource(R.id.pro_logo, R.mipmap.icon_sjsh);
                    if (item.getState() == null) {
                        cardState.setVisibility(View.VISIBLE);
                        cardLayout.setVisibility(View.INVISIBLE);
                        moneyApply.setVisibility(View.INVISIBLE);
                    } else {
                        switch (item.getState()) {
                            //禁用
                            case "DISABLED":
                                break;
                            //已删除
                            case "DELETED":
                                break;

                            //贝兜通过
                            case "ENABLED":
                                cardState.setVisibility(View.INVISIBLE);
                                cardLayout.setVisibility(View.VISIBLE);
                                moneyApply.setVisibility(View.VISIBLE);
                                YDaiApplication.getInstance().setCreditCardId(productStateVo.getSjsh().getCardId());
                                helper.setText(R.id.use_money, "剩余: " + ToolUtils.qianweifenge(Double.valueOf(item.getReamin())));
                                helper.setText(R.id.unuse_money, ToolUtils.qianweifenge(Double.valueOf(item.getTotal())));
                                float percent = Float.valueOf(item.getReamin()) / Float.valueOf(item.getTotal());
                                eduLineView.setmProgress(percent);
                                RelativeLayout.LayoutParams slideParams = (RelativeLayout.LayoutParams) slidePic.getLayoutParams();
                                if (percent == 0) {
                                    slideParams.leftMargin = (int) (getResources().getDimension(R.dimen.x24) - getResources().getDimension(R.dimen.x10));
                                } else {
                                    slideParams.leftMargin = (int) getResources().getDimension(R.dimen.x24) + (int) ((ScreenUtil.getScreenWidth(getActivity()) - 2 * getResources().getDimension(R.dimen.x24)) * percent - getResources().getDimension(R.dimen.x10));
                                }
                                slidePic.setLayoutParams(slideParams);
                                break;
                            //待审核
                            case "CREATED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                //cardState.setText("审核中");
                                cardState.setImageResource(R.mipmap.pic_shenhe);
                                break;
                            //物流钱庄通过
                            case "ADOPT":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                //cardState.setText("初审通过");
                                cardState.setImageResource(R.mipmap.icon_cstg);
                                break;
                            //用户已签约
                            case "SIGNED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                //cardState.setText("已签约");
                                cardState.setImageResource(R.mipmap.icon_cstg);
                                break;
                            //用户已提交贝兜
                            case "SUBMISSION":
                                break;
                            //贝兜未通过
                            case "NOTPASS":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                //cardState.setText("审核失败");
                                cardState.setImageResource(R.mipmap.icon_fall);
                                break;
                            //物流钱庄未通过
                            case "DENIED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                //cardState.setText("初审失败");
                                cardState.setImageResource(R.mipmap.icon_fall);
                                break;
                        }
                    }
                    break;

                case "wdxyd":
                    helper.setText(R.id.protuct_title, "网点信用贷");
                    helper.setImageResource(R.id.pro_logo, R.mipmap.icon_wangdxy);
                    if (item.getState() == null) {
                        cardState.setVisibility(View.VISIBLE);
                        cardLayout.setVisibility(View.INVISIBLE);
                        moneyApply.setVisibility(View.INVISIBLE);
                    } else {
                        switch (item.getState()) {
                            //待审核
                            case "CREATED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.pic_shenhe);
                                break;
                            //有效
                            case "ENABLED":
                                // wdxydState.setText("剩余额度:" + ToolUtils.formatStringNumber(wdxydVo.getReamin()) + "元");
                                break;
                            //禁用
                            case "DISABLED":
                                break;
                            //删除
                            case "DELETED":
                                break;
                            //未通过
                            case "DENIED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.icon_fall);
                                break;
                            //通过
                            case "ADOPT":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.icon_cstg);
                                break;
                        }
                    }

                    break;

                case "rzzl":
                    helper.setText(R.id.protuct_title, "融资租赁");
                    helper.setImageResource(R.id.pro_logo, R.mipmap.icon_rongz);
                    if (item.getState() == null) {
                        cardState.setVisibility(View.VISIBLE);
                        cardLayout.setVisibility(View.INVISIBLE);
                        moneyApply.setVisibility(View.INVISIBLE);
                    } else {
                        switch (item.getState()) {
                            //待审核
                            case "CREATED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.pic_shenhe);
                                break;
                            //有效
                            case "ENABLED":
                                //rzzlState.setText("剩余额度:" + ToolUtils.formatStringNumber(rzzlVo.getReamin()) + "元");
                                break;
                            //禁用
                            case "DISABLED":
                                break;
                            //删除
                            case "DELETED":
                                break;
                            //未通过
                            case "DENIED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.icon_fall);
                                break;
                            //通过
                            case "ADOPT":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.icon_cstg);
                                break;
                        }
                    }
                    break;

                case "mdbt":
                    helper.setText(R.id.protuct_title, "面单白条");
                    helper.setImageResource(R.id.pro_logo, R.mipmap.icon_bait);
                    if (item.getState() == null) {
                        cardState.setVisibility(View.VISIBLE);
                        cardLayout.setVisibility(View.INVISIBLE);
                        moneyApply.setVisibility(View.INVISIBLE);
                    } else {
                        switch (item.getState()) {
                            //待审核
                            case "CREATED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.pic_shenhe);
                                break;
                            //有效
                            case "ENABLED":
                                cardState.setVisibility(View.INVISIBLE);
                                cardLayout.setVisibility(View.VISIBLE);
                                moneyApply.setVisibility(View.VISIBLE);
                                if (YDaiApplication.getInstance().getCompanyRole() == null) {
                                    YDaiApplication.getInstance().setMdbtId(productStateVo.getMdbt().getCardId());
                                } else if (YDaiApplication.getInstance().getCompanyRole().equals("manager")) {
                                    YDaiApplication.getInstance().setMdbtId(productStateVo.getMdbt().getCardId());
                                } else {
                                    YDaiApplication.getInstance().setMdbtId(productStateVo.getCompanyMDBT().getCardId());
                                }
                                helper.setText(R.id.use_money, "剩余: " + ToolUtils.qianweifenge(Double.valueOf(item.getReamin())));
                                helper.setText(R.id.unuse_money, ToolUtils.qianweifenge(Double.valueOf(item.getTotal())));
                                float percent = Float.valueOf(item.getReamin()) / Float.valueOf(item.getTotal());
                                eduLineView.setmProgress(percent);
                                RelativeLayout.LayoutParams slideParams = (RelativeLayout.LayoutParams) slidePic.getLayoutParams();
                                if (percent == 0) {
                                    slideParams.leftMargin = (int) (getResources().getDimension(R.dimen.x24) - getResources().getDimension(R.dimen.x10));
                                } else {
                                    slideParams.leftMargin = (int) getResources().getDimension(R.dimen.x24) + (int) ((ScreenUtil.getScreenWidth(getActivity()) - 2 * getResources().getDimension(R.dimen.x24)) * percent - getResources().getDimension(R.dimen.x10));
                                }
                                slidePic.setLayoutParams(slideParams);
                                break;
                            //禁用
                            case "DISABLED":
                                break;
                            //删除
                            case "DELETED":
                                break;
                            //未通过
                            case "DENIED":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.icon_fall);
                                break;
                            //通过
                            case "ADOPT":
                                cardState.setVisibility(View.VISIBLE);
                                cardLayout.setVisibility(View.INVISIBLE);
                                moneyApply.setVisibility(View.INVISIBLE);
                                cardState.setImageResource(R.mipmap.icon_cstg);
                                break;
                        }
                    }
                    break;
            }
        }
    }

    class CarMulAdapter extends BaseQuickAdapter<ProductVo,BaseViewHolder>{

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public CarMulAdapter(List<ProductVo> data) {
            super(data);
            setMultiTypeDelegate(new MultiTypeDelegate<ProductVo>() {
                @Override
                protected int getItemType(ProductVo productVo) {
                    //根据你的实体类来判断布局类型
                    return productVo.getItemType();
                }
            });

            getMultiTypeDelegate()
                    .registerItemType(ProductVo.PASS, R.layout.card_pass_adapter_layout)
                    .registerItemType(ProductVo.UNPASS, R.layout.card_unpass_adapter_layout);

        }

        @Override
        protected void convert(BaseViewHolder helper, ProductVo item) {
            switch (helper.getItemViewType()) {
                case ProductVo.PASS:
                    EduLineView eduLineView = helper.getView(R.id.eduLine);
                    ImageView slidePic = helper.getView(R.id.slidePic);
                    switch (item.getCardSort()) {
                        case "sjsh":
                            helper.setText(R.id.protuct_title, "随借随还");
                            helper.setImageResource(R.id.pro_logo, R.mipmap.icon_sjsh);
                                switch (item.getState()) {
                                    //禁用
                                    case "DISABLED":
                                        break;
                                    //已删除
                                    case "DELETED":
                                        break;

                                    //贝兜通过
                                    case "ENABLED":
                                        YDaiApplication.getInstance().setCreditCardId(productStateVo.getSjsh().getCardId());
                                        helper.setText(R.id.use_money, "剩余: " + ToolUtils.qianweifenge(Double.valueOf(item.getReamin())));
                                        helper.setText(R.id.unuse_money, ToolUtils.qianweifenge(Double.valueOf(item.getTotal())));
                                        float percent = Float.valueOf(item.getReamin()) / Float.valueOf(item.getTotal());
                                        eduLineView.setmProgress(percent);
                                        RelativeLayout.LayoutParams slideParams = (RelativeLayout.LayoutParams) slidePic.getLayoutParams();
                                        if (percent == 0) {
                                            slideParams.leftMargin = (int) (getResources().getDimension(R.dimen.x24) - getResources().getDimension(R.dimen.x10));
                                        } else {
                                            slideParams.leftMargin = (int) getResources().getDimension(R.dimen.x24) + (int) ((ScreenUtil.getScreenWidth(getActivity()) - 2 * getResources().getDimension(R.dimen.x24)) * percent - getResources().getDimension(R.dimen.x10));
                                        }
                                        slidePic.setLayoutParams(slideParams);
                                        break;
                                    //待审核
                                    case "CREATED":
                                        break;
                                    //物流钱庄通过
                                    case "ADOPT":
                                        break;
                                    //用户已签约
                                    case "SIGNED":
                                        break;
                                    //用户已提交贝兜
                                    case "SUBMISSION":
                                        break;
                                    //贝兜未通过
                                    case "NOTPASS":
                                        break;
                                    //物流钱庄未通过
                                    case "DENIED":
                                        break;

                            }
                            break;

                        case "wdxyd":
                            helper.setText(R.id.protuct_title, "网点信用贷");
                            helper.setImageResource(R.id.pro_logo, R.mipmap.icon_wangdxy);
                                switch (item.getState()) {
                                    //待审核
                                    case "CREATED":
                                        break;
                                    //有效
                                    case "ENABLED":
                                        // wdxydState.setText("剩余额度:" + ToolUtils.formatStringNumber(wdxydVo.getReamin()) + "元");
                                        break;
                                    //禁用
                                    case "DISABLED":
                                        break;
                                    //删除
                                    case "DELETED":
                                        break;
                                    //未通过
                                    case "DENIED":
                                        break;
                                    //通过
                                    case "ADOPT":
                                        break;

                            }

                            break;

                        case "rzzl":
                            helper.setText(R.id.protuct_title, "融资租赁");
                            helper.setImageResource(R.id.pro_logo, R.mipmap.icon_rongz);

                                switch (item.getState()) {
                                    //待审核
                                    case "CREATED":
                                        break;
                                    //有效
                                    case "ENABLED":
                                        //rzzlState.setText("剩余额度:" + ToolUtils.formatStringNumber(rzzlVo.getReamin()) + "元");
                                        break;
                                    //禁用
                                    case "DISABLED":
                                        break;
                                    //删除
                                    case "DELETED":
                                        break;
                                    //未通过
                                    case "DENIED":
                                        break;
                                    //通过
                                    case "ADOPT":
                                        break;

                            }
                            break;

                        case "mdbt":
                            helper.setText(R.id.protuct_title, "面单白条");
                            helper.setImageResource(R.id.pro_logo, R.mipmap.icon_bait);
                                switch (item.getState()) {
                                    //待审核
                                    case "CREATED":
                                        break;
                                    //有效
                                    case "ENABLED":
                                        if (YDaiApplication.getInstance().getCompanyRole() == null) {
                                            YDaiApplication.getInstance().setMdbtId(productStateVo.getMdbt().getCardId());
                                        } else if (YDaiApplication.getInstance().getCompanyRole().equals("manager")) {
                                            YDaiApplication.getInstance().setMdbtId(productStateVo.getMdbt().getCardId());
                                        } else {
                                            YDaiApplication.getInstance().setMdbtId(productStateVo.getCompanyMDBT().getCardId());
                                        }
                                        helper.setText(R.id.use_money, "剩余: " + ToolUtils.qianweifenge(Double.valueOf(item.getReamin())));
                                        helper.setText(R.id.unuse_money, ToolUtils.qianweifenge(Double.valueOf(item.getTotal())));
                                        float percent = Float.valueOf(item.getReamin()) / Float.valueOf(item.getTotal());
                                        eduLineView.setmProgress(percent);
                                        RelativeLayout.LayoutParams slideParams = (RelativeLayout.LayoutParams) slidePic.getLayoutParams();
                                        if (percent == 0) {
                                            slideParams.leftMargin = (int) (getResources().getDimension(R.dimen.x24) - getResources().getDimension(R.dimen.x10));
                                        } else {
                                            slideParams.leftMargin = (int) getResources().getDimension(R.dimen.x24) + (int) ((ScreenUtil.getScreenWidth(getActivity()) - 2 * getResources().getDimension(R.dimen.x24)) * percent - getResources().getDimension(R.dimen.x10));
                                        }
                                        slidePic.setLayoutParams(slideParams);
                                        break;
                                    //禁用
                                    case "DISABLED":
                                        break;
                                    //删除
                                    case "DELETED":
                                        break;
                                    //未通过
                                    case "DENIED":
                                        break;
                                    //通过
                                    case "ADOPT":
                                        break;

                            }
                            break;
                    }
                    break;
                case ProductVo.UNPASS:
                    switch (item.getCardSort()) {
                        case "sjsh":
                            helper.setText(R.id.protuct_title, "随借随还");
                            helper.setImageResource(R.id.pro_logo, R.mipmap.icon_sjsh);
                            switch (item.getState()) {
                                    //禁用
                                    case "DISABLED":
                                        break;
                                    //已删除
                                    case "DELETED":
                                        break;

                                    //贝兜通过
                                    case "ENABLED":
                                        break;
                                    //待审核
                                    case "CREATED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.pic_shenhe);
                                        break;
                                    //物流钱庄通过
                                    case "ADOPT":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_cstg);
                                        break;
                                    //用户已签约
                                    case "SIGNED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_cstg);
                                        break;
                                    //用户已提交贝兜
                                    case "SUBMISSION":
                                        break;
                                    //贝兜未通过
                                    case "NOTPASS":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_fall);
                                        break;
                                    //物流钱庄未通过
                                    case "DENIED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_fall);
                                        break;
                                }
                            break;

                        case "wdxyd":
                            helper.setText(R.id.protuct_title, "网点信用贷");
                            helper.setImageResource(R.id.pro_logo, R.mipmap.icon_wangdxy);
                                switch (item.getState()) {
                                    //待审核
                                    case "CREATED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.pic_shenhe);
                                        break;
                                    //有效
                                    case "ENABLED":
                                        // wdxydState.setText("剩余额度:" + ToolUtils.formatStringNumber(wdxydVo.getReamin()) + "元");
                                        break;
                                    //禁用
                                    case "DISABLED":
                                        break;
                                    //删除
                                    case "DELETED":
                                        break;
                                    //未通过
                                    case "DENIED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_fall);
                                        break;
                                    //通过
                                    case "ADOPT":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_cstg);
                                        break;
                                }


                            break;

                        case "rzzl":
                            helper.setText(R.id.protuct_title, "融资租赁");
                            helper.setImageResource(R.id.pro_logo, R.mipmap.icon_rongz);
                                switch (item.getState()) {
                                    //待审核
                                    case "CREATED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.pic_shenhe);
                                        break;
                                    //有效
                                    case "ENABLED":
                                        //rzzlState.setText("剩余额度:" + ToolUtils.formatStringNumber(rzzlVo.getReamin()) + "元");
                                        break;
                                    //禁用
                                    case "DISABLED":
                                        break;
                                    //删除
                                    case "DELETED":
                                        break;
                                    //未通过
                                    case "DENIED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_fall);
                                        break;
                                    //通过
                                    case "ADOPT":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_cstg);
                                        break;
                                }

                            break;

                        case "mdbt":
                            helper.setText(R.id.protuct_title, "面单白条");
                            helper.setImageResource(R.id.pro_logo, R.mipmap.icon_bait);
                                switch (item.getState()) {
                                    //待审核
                                    case "CREATED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.pic_shenhe);
                                        break;
                                    //有效
                                    case "ENABLED":

                                        break;
                                    //禁用
                                    case "DISABLED":
                                        break;
                                    //删除
                                    case "DELETED":
                                        break;
                                    //未通过
                                    case "DENIED":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_fall);
                                        break;
                                    //通过
                                    case "ADOPT":
                                        helper.setImageResource(R.id.card_state,R.mipmap.icon_cstg);
                                        break;
                                }

                            break;
                    }
                    break;
            }

        }
    }

}
