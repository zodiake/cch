package com.by.service.impl;

import com.by.exception.AlreadyExchangeException;
import com.by.exception.NotEnoughCouponException;
import com.by.exception.NotValidException;
import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import com.by.repository.PreferentialCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.DuplicateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (!couponService.couponIsWithinValidDate(sourceCoupon))
            throw new NotValidException();
        couponMember.setTotal(sourceTotal - total);
        useHistoryService.save(coupon, member, total);
        return couponMember;
    }

    @Override
    public PreferentialCouponMember exchangeCoupon(PreferentialCoupon coupon, Member member, int total) {
        Optional<Member> memberOptional = memberService.findById(member.getId());
        PreferentialCoupon sourceCoupon = preferentialCouponService.findOne(coupon.getId());
        PreferentialCouponMember pcm = repository.findByCouponAndMember(coupon, member);
        if (coupon.getDuplicate().equals(DuplicateEnum.ISDUPLICATE)) {
            if (pcm != null) {
                pcm.setTotal(pcm.getTotal() + total);
            } else {
                pcm = save(sourceCoupon, memberOptional.get(), total);
            }
        } else {
            if (pcm == null)
                pcm = save(sourceCoupon, memberOptional.get(), total);
            else
                throw new AlreadyExchangeException();
        }
        memberService.updateScore(memberOptional.get(), memberOptional.get().getScore() - total, reason);
        exchangeHistoryService.save(memberOptional.get(), coupon, total);
        return pcm;
    }

    public PreferentialCouponMember findByCouponAndMember(PreferentialCoupon coupon, Member member) {
        return repository.findByCouponAndMember(coupon, member);
    }

    @Override
    public Long sumTotalGroupByCoupon(PreferentialCoupon coupon) {
        return repository.sumTotalGroupByCoupon(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponJson> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member, pageable)
                .getContent()
                .stream()
                .filter(i -> {
                    return couponService.isWithinValidDate(i.getCoupon());
                })
                .map(i -> {
                    Coupon c = i.getCoupon();
                    return new CouponJson(c.getId(), c.getName(), c.getEndTime(), i.getTotal());
                }).collect(Collectors.toList());
    }

    public PreferentialCouponMember save(PreferentialCouponMember pcm) {
        return repository.save(pcm);
    }

    public PreferentialCouponMember save(PreferentialCoupon coupon, Member member, int total) {
        return repository.save(new PreferentialCouponMember(member, coupon, total));
    }


}
