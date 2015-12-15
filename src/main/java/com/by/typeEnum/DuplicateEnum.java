package com.by.typeEnum;

import java.util.Locale;

/**
 * Created by yagamai on 15-11-30.
 */
public enum DuplicateEnum {
    NOTDUPLICATE,
    ISDUPLICATE;

    public static DuplicateEnum fromString(String source) {
        try {
            return DuplicateEnum.valueOf(source.toUpperCase(Locale.US));
        } catch (Exception e) {
            return null;
        }
    }
}
