package com.by.json;

import com.by.model.CardRule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by yagamai on 15-12-10.
 */
public class RuleJson {
    private String beginTime;
    private String endTime;
    private String cardName;
    private String category;

    public RuleJson() {
    }

    public RuleJson(CardRule rule) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.beginTime = format.format(rule.getBeginTime().getTime());
        this.endTime = format.format(rule.getEndTime().getTime());
        this.cardName = rule.getCard().getName();
        this.category = rule.getRuleCategory().getName();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
