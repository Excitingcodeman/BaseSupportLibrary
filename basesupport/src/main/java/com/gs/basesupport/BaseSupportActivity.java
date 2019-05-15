package com.gs.basesupport;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tbruyelle.rxpermissions2.RxPermissions;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mSavedInstanceState = savedInstanceState;
        binding = DataBindingUtil.setContentView(this, getLayout());
        mWindow = getWindow();
        rxPermissions = new RxPermissions(mActivity);
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
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            dismissDialog();
        }
    }
}
