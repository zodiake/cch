package com.by.service.impl;

import com.by.json.CouponJson;
import com.by.model.Member;
import com.by.model.Sequence;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import com.by.repository.ShopCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.DuplicateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-8.
 */
@Service
@Transactional
public class ShopCouponMemberServiceImpl implements ShopCouponMemberService {
    private final String reason = "";
    private final DateFormat format = new SimpleDateFormat("yyyyMMddS");
    @Autowired
    private ShopCouponMemberRepository repository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ShopCouponService shopCouponService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private CouponService couponService;

    @Override
    public ShopCouponMember exchangeCoupon(Member member, ShopCoupon coupon, int total) {
        int count = total;
        Optional<Member> memberOptional = memberService.findById(member.getId());
        ShopCoupon shopCoupon = shopCouponService.findOne(coupon.getId());
        if (coupon.getDuplicate().equals(DuplicateEnum.ISDUPLICATE)) {
            for (int i = 1; i <= total; i++) {
                save(shopCoupon, memberOptional.get());
            }
        } else {
            count = 1;
            save(shopCoupon, memberOptional.get());
        }
        memberService.minusScore(memberOptional.get(), memberOptional.get().getScore() - count, reason);
        return null;
    }

    @Override
    public ShopCouponMember useCoupon(String code, Member member) {
        return null;
    }

    @Override
    public ShopCouponMember save(ShopCouponMember coupon) {
        return repository.save(coupon);
    }

    @Override
    public ShopCouponMember save(ShopCoupon coupon, Member member) {
        Sequence sequence = sequenceService.save(new Sequence());
        ShopCouponMember sm = new ShopCouponMember(member, coupon);
        sm.setCode(format.format(Calendar.getInstance().getTime()) + sequence.getId());
        return save(sm);
    }

    @Override
    public Long countByCoupon(ShopCoupon coupon) {
        return repository.countByCoupon(coupon);
    }

    @Override
    public List<ShopCouponMember> findByCouponAndMember(ShopCoupon coupon, Member member) {
        return repository.findByCouponAndMember(coupon, member);
    }

    @Override
    public List<CouponJson> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member, pageable).getContent()
                .stream()
                .filter(i -> {
                    return i.getUsedTime() == null && couponService.isWithinValidDate(i.getCoupon());
                }).map(i -> {
                    ShopCoupon c = i.getCoupon();
                    return new CouponJson(c.getId(), c.getName(), c.getEndTime(), i.getCoupon().getShop().getName());
                })
                .collect(Collectors.toList());
    }
}
