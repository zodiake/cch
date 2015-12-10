package com.by.form;

import com.by.json.RuleJson;

import java.util.List;

/**
 * Created by yagamai on 15-12-10.
 */
public class CardForm {
    private String name;
    private List<RuleJson> register;
    private List<RuleJson> trading;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
