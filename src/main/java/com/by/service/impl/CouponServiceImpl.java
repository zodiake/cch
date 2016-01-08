package com.by.service.impl;

import com.by.form.BaseCouponForm;
import com.by.json.CouponJson;
import com.by.model.*;
import com.by.repository.GiftCouponMemberRepository;
import com.by.repository.ShopCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.CouponAdminStateEnum;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private GiftCouponMemberRepository giftCouponMemberRepository;
    @Autowired
    private ShopCouponMemberRepository shopCouponMemberRepository;
    @Autowired
    private ParkingCouponService parkingCouponService;
    @Autowired
    private GiftCouponService giftCouponService;
    @Autowired
    private ShopCouponService shopCouponService;
    @Autowired
    private MemberService memberService;

    @Override
    public boolean isValidCoupon(Coupon coupon) {
        if (isValid(coupon)) {
            Calendar today = Calendar.getInstance();
            if (coupon.getBeginTime() == null && coupon.getEndTime() == null)
                return true;
            if (coupon.getBeginTime() != null && coupon.getEndTime() != null) {
                if (coupon.getBeginTime().before(today) && coupon.getEndTime().after(today)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean couponIsWithinValidDate(Coupon coupon) {
        if (coupon.getCouponEndTime() == null)
            return true;
        Calendar today = Calendar.getInstance();
        if (coupon.getCouponEndTime().after(today))
            return true;
        return false;
    }

    public boolean noStorageLimited(Coupon coupon) {
        return coupon.getTotal() == 0;
    }

    public boolean isValid(Coupon coupon) {
        return coupon.getValid().equals(ValidEnum.VALID);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean withinValidDate(Coupon coupon) {
        Calendar today = Calendar.getInstance();
        return coupon.getBeginTime().before(today) && coupon.getEndTime().after(today);
    }

    @Override
    public boolean isDuplicateCoupon(Coupon couponSummary) {
        return couponSummary.getDuplicate().equals(DuplicateEnum.ISDUPLICATE);
    }

    @Override
    public boolean isPermanent(Coupon couponSummary) {
        return couponSummary.getBeginTime() == null && couponSummary.getEndTime() == null;
    }

    @Override
    public <T extends Coupon> List<Predicate> getPredicateList(BaseCouponForm form, Root<T> root, CriteriaBuilder cb) {
        List<Predicate> criteria = new ArrayList<>();
        if (form.getState() != null) {
            Calendar today = Calendar.getInstance();
            if (form.getState().equals(CouponAdminStateEnum.CLOSED)) {
                criteria.add(cb.equal(root.get("valid"), ValidEnum.INVALID));
            } else if (form.getState().equals(CouponAdminStateEnum.NOEXPIRE)) {
                criteria.add(cb.greaterThan(root.get("beginTime"), today));
            } else if (form.getState().equals(CouponAdminStateEnum.USING)) {
                criteria.add(cb.or(
                        cb.and(cb.lessThanOrEqualTo(root.get("beginTime"), today),
                                cb.greaterThanOrEqualTo(root.get("endTime"), today)),
                        cb.and(cb.isNull(root.get("beginTime"))), cb.isNull(root.get("endTime"))));
            } else if (form.getState().equals(CouponAdminStateEnum.EXPIRE)) {
                criteria.add(cb.lessThan(root.get("endTime"), today));
            }
        }
        if (form.getBeginTime() != null) {
            criteria.add(cb.or(cb.lessThanOrEqualTo(root.get("beginTime"), form.getBeginTime()),
                    cb.isNull(root.get("endTime"))));
        }
        if (form.getEndTime() != null)
            criteria.add(cb.or(cb.greaterThanOrEqualTo(root.get("endTime"), form.getEndTime()),
                    cb.isNull(root.get("endTime"))));
        return criteria;
    }

    @Override
    public boolean canUpdate(Coupon coupon) {
        Calendar today = Calendar.getInstance();
        return coupon.getBeginTime().after(today);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponJson> findAll(Pageable pageable) {
        Page<GiftCoupon> giftCoupons = giftCouponService.findAllByValidAndDateBetween(ValidEnum.VALID, Calendar.getInstance(), pageable);
        Page<ShopCoupon> shopCoupons = shopCouponService.findAllByValidAndDateBetween(ValidEnum.VALID, Calendar.getInstance(), pageable);
        List<CouponJson> results = new ArrayList<>();
        List<CouponJson> giftJson = giftCoupons.getContent().stream().map(i -> {
            CouponJson json = new CouponJson(i);
            json.setType("g");
            return json;
        }).collect(Collectors.toList());
        List<CouponJson> shopJson = shopCoupons.getContent().stream().map(i -> {
            CouponJson json = new CouponJson(i);
            json.setType("s");
            return json;
        }).collect(Collectors.toList());
        results.addAll(giftJson);
        results.addAll(shopJson);
        results.sort((o1, o2) -> {
            if (o1.getCouponTime().before(o2.getCouponTime()))
                return 1;
            return -1;
        });
        if (pageable.getPageNumber() == 0) {
            ParkingCoupon parkingCoupon = parkingCouponService.findActivate();
            CouponJson json = new CouponJson(parkingCoupon);
            json.setType("p");
            results.add(0, json);
        }
        Long max = Math.max(giftCoupons.getTotalElements(), shopCoupons.getTotalElements());
        return new PageImpl<>(results.stream().limit(15).collect(Collectors.toList()), pageable, max);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponJson> findByMember(Member member, Pageable pageable) {
        Page<GiftCouponMember> giftCouponList = giftCouponMemberRepository.findByMemberAndValid(member, ValidEnum.VALID, Calendar.getInstance(), pageable);
        Page<ShopCouponMember> shopCouponMemberList = shopCouponMemberRepository.findByMemberAndValid(member, ValidEnum.VALID, Calendar.getInstance(), pageable);
        List<CouponJson> results = new ArrayList<>();
        List<CouponJson> preferentialJson = giftCouponList.getContent().stream()
                .map(i -> {
                    CouponJson json = new CouponJson(i.getCoupon());
                    json.setType("g");
                    json.setCode(i.getCode());
                    return json;
                }).collect(Collectors.toList());
        List<CouponJson> shopJson = shopCouponMemberList.getContent().stream()
                .map(i -> {
                    CouponJson json = new CouponJson(i.getCoupon());
                    json.setType("s");
                    json.setCode(i.getCode());
                    return json;
                }).collect(Collectors.toList());
        results.addAll(preferentialJson);
        results.addAll(shopJson);
        results.sort((o1, o2) -> {
            if (o1.getCouponTime().before(o2.getCouponTime()))
                return 1;
            return -1;
        });
        if (pageable.getPageNumber() == 0) {
            Member m = memberService.findOne(member.getId());
            int total = m.getTotalParkingCoupon();
            CouponJson json = new CouponJson();
            json.setTotal(total);
            json.setType("p");
            results.add(0, json);
        }
        Long max = Math.max(shopCouponMemberList.getTotalElements(), giftCouponList.getTotalElements());
        return new PageImpl<>(results.stream().limit(pageable.getPageSize()).collect(Collectors.toList()), pageable, max);
    }
}
