package com.gs.basesupport.event;

import org.greenrobot.eventbus.EventBus;

/**
 * @author husky
 * @date 2016/11/28
 */

public class EventBusImpl implements IBus {

    @Override
    public void register(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }


    @Override
    public void unregister(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    @Override
    public void post(BaseEvent event) {
        EventBus.getDefault().post(event);
    }

    @Override
    public void postSticky(BaseEvent event) {
        EventBus.getDefault().postSticky(event);
    }


    @Override
    public boolean isRegister(Object object) {
        return EventBus.getDefault().isRegistered(object);
    }

}
