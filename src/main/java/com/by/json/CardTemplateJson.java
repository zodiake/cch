package com.by.json;

import com.by.model.Card;
import com.by.model.CardRule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-23.
 */
public class CardTemplateJson {
    private String state;
    private int total;
    private String name;
    private int initScore;
    private String summary;
    private List<RuleJson> register;
    private List<RuleJson> trading;

    public CardTemplateJson() {
    }

    public CardTemplateJson(Card card, int total, List<CardRule> registerRules, List<CardRule> tradingRules) {
        this.state = card.getValid().toString();
        this.total = total;
        this.name = card.getName();
        this.initScore = card.getInitScore();
        this.summary = card.getSummary();
        this.register = registerRules.stream().map(RuleJson::new).collect(Collectors.toList());
        this.trading = tradingRules.stream().map(RuleJson::new).collect(Collectors.toList());
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitScore() {
        return initScore;
    }

    public void setInitScore(int initScore) {
        this.initScore = initScore;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<RuleJson> getRegister() {
        return register;
    }

    public void setRegister(List<RuleJson> register) {
        this.register = register;
    }

    public List<RuleJson> getTrading() {
        return trading;
    }

    public void setTrading(List<RuleJson> trading) {
        this.trading = trading;
    }
}
