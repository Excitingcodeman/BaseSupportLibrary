package com.gs.basesupport.broadcast;

/**
 * @author husky
 * create on 2018/11/10-11:08
 * 网络改变观察者，观察网络改变后回调的方法
 */
public interface NetChangeObserver {
    /**
     * 网络连接回调
     *
     * @param type type为网络类型
     */
    void onNetConnected(NetType type);

    /**
     * 没有网络
     */
    void onNetDisConnect();
}
