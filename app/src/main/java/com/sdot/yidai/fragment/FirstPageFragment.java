package com.sdot.yidai.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdot.yidai.R;
import com.sdot.yidai.app.YDaiApplication;
import com.sdot.yidai.miandan.MainDanActivity;
import com.sdot.yidai.rongzizl.RongZiActivity;
import com.sdot.yidai.sjsh.SjshDanActivity;
import com.sdot.yidai.ui.LoginActivity;
import com.sdot.yidai.ui.NewsCenterActivity;
import com.sdot.yidai.utils.MessageEvent;
import com.sdot.yidai.utils.URL;
import com.sdot.yidai.wangdian.WangDiActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.badgeview.BGABadgeImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPageFragment extends Fragment {

    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.tips_button)
    TextView tipsButton;
    Unbinder unbinder;
    @BindView(R.id.chanpin_text)
    TextView chanpinText;
    @BindView(R.id.txt_layout)
    RelativeLayout txtLayout;
    @BindView(R.id.red_dot)
    BGABadgeImageView redDot;
    private static final String TAG = "FirstPageFragment";
    public FirstPageFragment() {
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
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) redDot.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight();
        redDot.setLayoutParams(layoutParams);

        EventBus.getDefault().register(this);
        if (YDaiApplication.getInstance().isLoginsState()) {
            getRedDot();
        }
        return view;
    }
    
    public void reFreshDot() {
        if (YDaiApplication.getInstance().isLoginsState()) {
            getRedDot();
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case "news_refresh":
                if (YDaiApplication.getInstance().isLoginsState()) {
                    getRedDot();
                }
            case "user_login":
                getRedDot();
                break;
            case "user_exit":
                redDot.hiddenBadge();
                break;
        }
    }

    @OnClick({R.id.apply_sjsh, R.id.apply_wdxyd, R.id.apply_rzzl, R.id.red_dot,R.id.apply_md})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red_dot:
                if (YDaiApplication.getInstance().isLoginsState()) {
                    startActivity(new Intent(getActivity(), NewsCenterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.apply_sjsh:
                startActivity(new Intent(getActivity(), SjshDanActivity.class));
                break;
            case R.id.apply_wdxyd:
                startActivity(new Intent(getActivity(), WangDiActivity.class));
                break;
            case R.id.apply_rzzl:
                startActivity(new Intent(getActivity(), RongZiActivity.class));
                break;
            case R.id.apply_md:
                startActivity(new Intent(getActivity(), MainDanActivity.class));
                break;
        }
    }

    private void getRedDot() {
        OkGo.<String>get(URL.GET_RED_DOT).tag(this)
                .headers("cookie", "session=" + YDaiApplication.getInstance().getCookieValue())
                .params("userid", YDaiApplication.getInstance().getUserVo().getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!response.body().equals("0")) {
                            redDot.showCirclePointBadge();
                        } else {
                            redDot.hiddenBadge();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        redDot.hiddenBadge();
                    }
                });
    }

}
