package com.by.service.impl;

import com.by.exception.*;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import com.by.repository.PreferentialCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.DuplicateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by yagamai on 15-12-3.
 */
@Service
@Transactional
public class PreferentialCouponMemberServiceImpl implements PreferentialCouponMemberService {
    private final String reason = "礼品兑换";
    @Autowired
    private PreferentialCouponMemberRepository repository;
    @Autowired
    private PreferentialCouponService preferentialCouponService;
    @Autowired
    private PreferentialCouponUseHistoryService useHistoryService;
    @Autowired
    private PreferentialCouponExchangeHistoryService exchangeHistoryService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponService couponService;

    @Override
    public PreferentialCouponMember useCoupon(PreferentialCoupon coupon, Member member, int total) {
        PreferentialCouponMember couponMember = repository.findByCouponAndMember(coupon, member);
        PreferentialCoupon sourceCoupon = couponMember.getCoupon();
        int sourceTotal = couponMember.getTotal();
        if (couponMember == null)
            throw new NotEnoughCouponException();
        if (sourceTotal < total)
            throw new NotEnoughCouponException();
        if (!couponService.isValidCoupon(sourceCoupon))
            throw new NotValidException();
        couponMember.setTotal(sourceTotal - total);
        useHistoryService.save(coupon, member, total);
        return couponMember;
    }

    @Override
    public PreferentialCouponMember exchangeCoupon(PreferentialCoupon coupon, Member member, int total) {
        Optional<Member> sourceMember = memberService.findById(member.getId());
        PreferentialCoupon sourceCoupon = preferentialCouponService.findOne(coupon.getId());
        if (!sourceMember.isPresent())
            throw new MemberNotFoundException();
        if (!couponService.isValidCoupon(sourceCoupon))
            throw new NotValidException();
        if (coupon.getScore() * total > member.getScore())
            throw new NotEnoughScoreException();
        PreferentialCouponMember pcm = repository.findByCouponAndMember(coupon, member);
        if (coupon.getDuplicate().equals(DuplicateEnum.ISDUPLICATE)) {
            if (pcm != null) {
                pcm.setTotal(pcm.getTotal() + total);
            } else {
                pcm = save(sourceCoupon, sourceMember.get(), total);
            }
        } else {
            if (pcm == null)
                pcm = save(sourceCoupon, sourceMember.get(), total);
            else
                throw new AlreadyExchangeException();
        }
        memberService.updateScore(member, sourceMember.get().getScore() - total, reason);
        exchangeHistoryService.save(member, coupon, total);
        return pcm;
    }

    public PreferentialCouponMember findByCouponAndMember(PreferentialCoupon coupon, Member member) {
        return repository.findByCouponAndMember(coupon, member);
    }

    @Override
    public Long sumTotalGroupByCoupon(PreferentialCoupon coupon) {
        return repository.sumTotalGroupByCoupon(coupon);
    }

    public PreferentialCouponMember save(PreferentialCouponMember pcm) {
        return repository.save(pcm);
    }

    public PreferentialCouponMember save(PreferentialCoupon coupon, Member member, int total) {
        return repository.save(new PreferentialCouponMember(member, coupon, total));
    }


}
