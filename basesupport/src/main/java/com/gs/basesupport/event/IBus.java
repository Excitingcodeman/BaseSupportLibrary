package com.gs.basesupport.event;

/**
 *
 * @author husky
 * @date 2016/11/28
 */

public interface IBus {

    void register(Object object);

    void unregister(Object object);

    void post(BaseEvent event);

    void postSticky(BaseEvent event);

   boolean isRegister(Object object);




}
