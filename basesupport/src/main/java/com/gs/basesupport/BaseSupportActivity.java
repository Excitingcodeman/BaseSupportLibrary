package com.gs.basesupport;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.gs.basesupport.broadcast.NetChangeObserver;
import com.gs.basesupport.broadcast.NetStateReceiver;
import com.gs.basesupport.broadcast.NetType;
import com.gs.basesupport.event.BusFactory;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

/**
 * @author husky
 * create on 2019-05-14-11:00
 * 基类 activity
 */
public abstract class BaseSupportActivity<VB extends ViewDataBinding> extends AppCompatActivity {
    protected VB binding;

    protected BaseSupportActivity mActivity;

    protected Window mWindow;

    protected Dialog mDialog;

    protected Bundle mSavedInstanceState;

    protected RxPermissions rxPermissions;
    /**
     * 网络观察者
     */
    protected NetChangeObserver mNetChangeObserver = null;


    /**
     * 记录处于前台的Activityddd
     */
    private static BaseSupportActivity mForegroundActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mSavedInstanceState = savedInstanceState;
        binding = DataBindingUtil.setContentView(this, getLayout());
        mWindow = getWindow();
        rxPermissions = new RxPermissions(mActivity);
        // 网络改变的一个回掉类
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetType type) {
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                onNetworkDisConnected();
            }
        };
        //开启广播去监听 网络 改变事件
        NetStateReceiver.registerObserver(mNetChangeObserver);
        initView();
        initData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSavedInstanceState = outState;
    }

    /**
     * @return 布局文件id
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * 初始化相关视图
     */
    protected void initView() {

    }

    /**
     * 初始化相关数据
     */
    protected void initData() {

    }

    /**
     * 消失对话框
     */
    protected void dismissDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mForegroundActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mForegroundActivity = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            dismissDialog();
        }
        if (useEventBus()) {
            BusFactory.getBus().unregister(this);
        }
    }

    protected boolean useEventBus() {
        return false;
    }

    /**
     * 网络连接状态
     *
     * @param type 网络状态
     */
    protected void onNetworkConnected(NetType type) {

    }


    /**
     * 网络断开的时候调用
     */
    protected void onNetworkDisConnected() {

    }

    /**
     * APP是否在前台
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseSupportActivity getForegroundActivity() {
        return mForegroundActivity;
    }
}
