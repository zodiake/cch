package com.by.json;

import com.by.model.Authority;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by yagamai on 16-1-7.
 */
public class AuthorityJson {
    private int id;
    private String name;
    private String state;
    private String updatedTime;

    public AuthorityJson(Authority authority) {
        this.id = authority.getId();
        this.name = authority.getName();
        this.state = authority.getValid().toString();
        DateFormat format = new SimpleDateFormat();
        if (authority.getUpdatedTime() != null)
            this.updatedTime = format.format(authority.getUpdatedTime().getTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
