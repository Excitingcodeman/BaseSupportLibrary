package com.gs.basesupport.event;


/**
 * 事件
 *
 * @author husky
 * @date 2018/8/16
 */

public class BaseEvent {

    /**
     * 事件动作
     */
    protected String noticeAction;

    public BaseEvent(String noticeAction) {
        this.noticeAction = noticeAction;
    }

    public String getNoticeAction() {
        return noticeAction;
    }

    public void setNoticeAction(String noticeAction) {
        this.noticeAction = noticeAction;
    }

}
