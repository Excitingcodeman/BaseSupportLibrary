package com.gs.basesupport;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import com.gs.basesupport.event.BusFactory;

/**
 * @author husky
 * create on 2019-05-14-11:06
 */
public abstract class BaseSupportFragment<VB extends ViewDataBinding> extends Fragment {

    protected boolean isCreate = false;

    protected boolean isLoadData = false;


    protected VB binding;

    protected AppCompatActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AppCompatActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        isCreate = true;
        initView();
        initData();
        return binding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreate && !isLoadData) {
            lazyLoadData();
        }
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
     * 懒加载数据
     */
    protected void lazyLoadData() {
        isLoadData = true;
    }


    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (useEventBus()){
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (useEventBus()){
            BusFactory.getBus().unregister(this);
        }
    }
}
