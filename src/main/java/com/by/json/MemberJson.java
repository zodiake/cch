package com.by.json;

import com.by.model.Member;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by yagamai on 15-12-7.
 */
public class MemberJson {
    @NotNull
    @Length(min = 11, max = 11)
    private String mobile;

    @NotNull
    @Length(min = 4, max = 10)
    private String password;

    private int score;

    public MemberJson() {
    }

    public MemberJson(Member member) {
        this.mobile = member.getName();
        this.score = member.getScore();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
