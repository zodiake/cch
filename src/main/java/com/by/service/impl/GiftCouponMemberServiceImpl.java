package com.by.service.impl;

import com.by.exception.AlreadyExchangeException;
import com.by.exception.AlreadyUsedException;
import com.by.json.CouponJson;
import com.by.model.*;
import com.by.repository.GiftCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.ScoreHistoryEnum;
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
 * Created by yagamai on 15-12-3.
 */
@Service
@Transactional
public class GiftCouponMemberServiceImpl implements GiftCouponMemberService {
    private final String reason = "礼品兑换";
    private final DateFormat format = new SimpleDateFormat("yyyyMMddS");
    @Autowired
    private GiftCouponMemberRepository repository;
    @Autowired
    private GiftCouponService giftCouponService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private SequenceService sequenceService;

    @Override
    public GiftCouponMember useCoupon(String code, Member member) {
        GiftCouponMember pcm = repository.findByCode(code);
        if (pcm.getUsedTime() != null)
            throw new AlreadyUsedException();
        pcm.setUsedTime(Calendar.getInstance());
        return pcm;
    }

    @Override
    public void exchangeCoupon(GiftCoupon coupon, Member member, int total) {
        int count = total;
        Optional<Member> memberOptional = memberService.findById(member.getId());
        GiftCoupon sourceCoupon = giftCouponService.findOne(coupon.getId());
        if (couponService.isDuplicateCoupon(sourceCoupon)) {
            for (int i = 1; i <= total; i++) {
                save(sourceCoupon, memberOptional.get());
            }
        } else if (sourceCoupon != null && !couponService.isDuplicateCoupon(sourceCoupon)) {
            throw new AlreadyExchangeException();
        } else {
            count = 1;
            save(sourceCoupon, memberOptional.get());
        }
        memberService.minusScore(memberOptional.get(), sourceCoupon.getScore() * count, reason,
                ScoreHistoryEnum.COUPONEXCHANGE);
    }

    public List<GiftCouponMember> findByCouponAndMember(GiftCoupon coupon, Member member) {
        return repository.findByCouponAndMember(coupon, member);
    }

    @Override
    public Long sumTotalGroupByCoupon(GiftCoupon coupon) {
        return repository.countByCoupon(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponJson> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member, pageable).getContent().stream()
                .filter(i -> i.getUsedTime() == null && couponService.isValidCoupon(i.getCoupon()))
                .map(i -> {
                    Coupon c = i.getCoupon();
                    return new CouponJson(c);
                }).collect(Collectors.toList());
    }

    public GiftCouponMember save(GiftCouponMember pcm) {
        return repository.save(pcm);
    }

    public GiftCouponMember save(GiftCoupon coupon, Member member) {
        Sequence sequence = sequenceService.save(new Sequence());
        GiftCouponMember pcm = new GiftCouponMember(member, coupon);
        pcm.setCode(format.format(Calendar.getInstance().getTime()) + sequence.getId());
        return save(pcm);
    }
}
