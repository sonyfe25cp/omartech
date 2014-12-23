package com.omartech.laibicanRobot.filter;

import com.omartech.laibicanRobot.model.reply.ReplyMessage;

/**
 * Created by omar on 14-12-18.
 */
public class Rule {


    private String word;
    private RuleType ruleType;
    private ReplyMessage replyMessage;
    private int replyId;

    public Rule(String word, RuleType ruleType, ReplyMessage replyMessage) {
        this.word = word;
        this.ruleType = ruleType;
        this.replyMessage = replyMessage;
    }

    public Rule() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }


    public boolean match(String query) {
        switch (ruleType) {
            case Contains:
                if (word.contains(query)) {
                    return true;
                }
            case FirstMatch:
                if (word.startsWith(query)) {
                    return true;
                }
            case FullMatch:
                if (word.equals(query)) {
                    return true;
                }
            default:
                return false;
        }
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public ReplyMessage getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(ReplyMessage replyMessage) {
        this.replyMessage = replyMessage;
    }
}
