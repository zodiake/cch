package com.by.controller;

import com.by.exception.MemberNotFoundException;
import com.by.exception.NotEnoughScoreException;
import com.by.exception.NotValidException;
import com.by.model.Coupon;
import com.by.model.Member;
import com.by.service.CouponService;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-12-16.
 */
@Component
public class BaseController {
    @Autowired
    private CouponService couponService;

    protected void isValidMember(MemberService memberService, Member member) {
        Member cacheMember = memberService.findOneCache(member.getId());
        if (!cacheMember.getValid().equals(ValidEnum.VALID)) {
            throw new NotValidException();
        }
    }

    protected void validateCoupon(Member member, Coupon coupon, int total) {
        if (member == null)
            throw new MemberNotFoundException();
        if (coupon.getScore() * total > member.getScore())
            throw new NotEnoughScoreException();
        if (!couponService.isWithinValidDate(coupon))
            throw new NotValidException();
    }
}
