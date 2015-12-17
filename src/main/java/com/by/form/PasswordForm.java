package com.by.form;

import javax.validation.constraints.NotNull;

/**
 * Created by yagamai on 15-12-17.
 */
public class PasswordForm {
    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
