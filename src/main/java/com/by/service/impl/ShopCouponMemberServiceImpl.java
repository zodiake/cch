package com.by.service.impl;

import com.by.exception.*;
import com.by.json.CouponJson;
import com.by.model.Member;
import com.by.model.Sequence;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import com.by.repository.ShopCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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
    public void exchangeCoupon(Member member, ShopCoupon coupon, int total) {
        int count = total;
        Member m = memberService.findOne(member.getId());
        ShopCoupon shopCoupon = shopCouponService.findOne(coupon.getId());
        List<ShopCouponMember> lists = findByCouponAndMember(shopCoupon, m);
        if (shopCoupon == null)
            throw new NotFoundException();
        if (couponService.isValidCoupon(coupon)) {
            // 如果不能重复
            if (!couponService.isDuplicateCoupon(coupon)) {
                if (lists.size() > 0)
                    throw new AlreadyExchangeException();
                count = 1;
            }
            // 判断是否有库存
            if (!couponService.noStorageLimited(coupon)) {
                if (outOfStorage(coupon, count))
                    throw new OutOfStorageException();
            }
            for (int i = 1; i <= count; i++) {
                save(shopCoupon, m);
            }
            memberService.minusScore(m, shopCoupon.getScore() * count, reason, ScoreHistoryEnum.COUPONEXCHANGE);
        } else {
            throw new NotValidException();
        }
    }

    private boolean outOfStorage(ShopCoupon coupon, int count) {
        Long total = countByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    @Override
    public ShopCouponMember useCoupon(String code, Member member) {
        ShopCouponMember shopCouponMember = repository.findByCode(code);
        if (shopCouponMember.getUsedTime() != null)
            throw new AlreadyUsedException();
        shopCouponMember.setUsedTime(Calendar.getInstance());
        return shopCouponMember;
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
        return repository.findByMemberAndValid(member, ValidEnum.VALID, Calendar.getInstance(), pageable)
                .getContent()
                .stream()
                .map(i -> {
                    ShopCoupon c = i.getCoupon();
                    return new CouponJson(c);
                }).collect(Collectors.toList());
    }
}
