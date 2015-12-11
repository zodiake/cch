package com.by.form;

import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-11.
 */
public class UserQueryForm {
    private String name;
    private ValidEnum valid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ValidEnum getValid() {
        return valid;
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
    }
}
