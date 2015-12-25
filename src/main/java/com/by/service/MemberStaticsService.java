package com.by.service;

import com.by.model.Member;
import com.by.model.MemberStatics;

/**
 * Created by yagamai on 15-12-25.
 */
public interface MemberStaticsService {
    MemberStatics findOne(Member member);
}
