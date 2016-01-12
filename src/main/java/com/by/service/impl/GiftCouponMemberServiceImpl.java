package com.by.service.impl;

import com.by.exception.*;
import com.by.json.CouponMemberJson;
import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import com.by.model.Member;
import com.by.model.Sequence;
import com.by.repository.GiftCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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
        Member m = memberService.findOne(member.getId());
        GiftCoupon sourceCoupon = giftCouponService.findOne(coupon.getId());
        List<GiftCouponMember> giftCouponMember = findByCouponAndMember(coupon, member);
        if (sourceCoupon == null)
            throw new NotFoundException();
        if (couponService.isValidCoupon(coupon)) {
            // 如果不能重复
            if (!couponService.isDuplicateCoupon(coupon)) {
                if (giftCouponMember.size() > 0)
                    throw new AlreadyExchangeException();
                count = 1;
            }
            // 判断是否有库存
            if (!couponService.noStorageLimited(coupon)) {
                if (outOfStorage(coupon, count))
                    throw new OutOfStorageException();
            }
            for (int i = 1; i <= count; i++) {
                save(sourceCoupon, m);
            }
            memberService.minusScore(m, sourceCoupon.getScore() * count, reason, ScoreHistoryEnum.COUPONEXCHANGE);
        } else {
            throw new NotValidException();
        }
    }

    private boolean outOfStorage(GiftCoupon coupon, int count) {
        Long total = sumTotalGroupByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() + count > coupon.getTotal();
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
    public Page<CouponMemberJson> findByMemberAndValid(Member member, Pageable pageable) {
        Page<GiftCouponMember> lists = repository.findByMemberAndValid(member, ValidEnum.VALID, Calendar.getInstance(),
                pageable);
        return new PageImpl<>(toJson(lists), pageable, lists.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponMemberJson> findByMember(Member member, Pageable pageable) {
        Page<GiftCouponMember> lists = repository.findByMember(member, pageable);
        return new PageImpl<>(toJson(lists), pageable, lists.getTotalElements());
    }

    private List<CouponMemberJson> toJson(Page<GiftCouponMember> lists) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return lists.getContent().stream().map(i -> {
            CouponMemberJson c = new CouponMemberJson();
            c.setCode(i.getCode());
            c.setExchangedTime(format.format(i.getExchangedTime().getTime()));
            if (i.getUsedTime() != null)
                c.setUsedTime(format.format(i.getUsedTime().getTime()));
            c.setName(i.getCoupon().getName());
            return c;
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
