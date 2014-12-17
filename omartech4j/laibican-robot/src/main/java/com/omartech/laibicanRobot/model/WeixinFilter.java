package com.omartech.laibicanRobot.model;

import com.omartech.laibicanRobot.model.reply.ReplyMessage;

/**
 * Created by omar on 14-12-17.
 */
public class WeixinFilter {

    private String keyword;

    private ReplyMessage replyMessage;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ReplyMessage getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(ReplyMessage replyMessage) {
        this.replyMessage = replyMessage;
    }
}
