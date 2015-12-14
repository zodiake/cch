package com.by.typeEnum;

import java.util.Locale;

/**
 * Created by yagamai on 15-12-14.
 */
public enum CouponAdminStateEnum {
    USING,//使用中
    CLOSED,//已关闭
    EXPIRE,//过期
    NOEXPIRE;//未到期

    public static CouponAdminStateEnum fromString(String source) {
        try {
            return CouponAdminStateEnum.valueOf(source.toUpperCase(Locale.US));
        } catch (Exception e) {
            return null;
        }
    }
}
