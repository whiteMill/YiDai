package com.sdot.yidai.tencreceive;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static com.tencent.android.tpush.common.Constants.LogTag;

/**
 * Created by Administrator on 2017/12/4.
 */

public class TentReceive extends XGPushBaseReceiver{

    private static final String TAG = "TentReceive";
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
        Log.i(TAG, "onRegisterResult: "+xgPushRegisterResult.getAccount());
    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        Log.i(TAG, "onTextMessage: "+xgPushTextMessage.getTitle());
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
    /*    if (context == null || message == null) {
            ToastUtils.getInstance(context).showMessage("123");
        }
        String strJSON = message.getCustomContent();
        String activityName = message.getActivityName();
        Log.i(TAG, "onNotifactionClickedResult: "+activityName+"====="+strJSON);
        ToastUtils.getInstance(context).showMessage("3");*/

        if (context == null || message == null) {
            return;
        }

        String text = "";
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + message;
            Intent intentPush = new Intent();
            intentPush.setAction("com.wl.action.PUSH_MESSAGE");
            context.sendBroadcast(intentPush);

        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + message;
        }
       // ToastUtils.getInstance(context).showMessage("广播接收到通知被点击:" + message.toString());
      /*  Intent intent = new Intent(context, NewsCenterActivity.class);
        context.startActivity(intent);*/


        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理的过程。。。
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        //ToastUtils.getInstance(context).showMessage(xgPushShowedResult.getContent());
    }
}
