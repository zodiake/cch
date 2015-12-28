package com.by.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.by.form.CouponQueryForm;
import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import com.by.repository.CouponRepository;
import com.by.repository.GiftCouponMemberRepository;
import com.by.repository.ParkingCouponMemberRepository;
import com.by.repository.ShopCouponMemberRepository;
import com.by.service.CouponService;
import com.by.typeEnum.CouponAdminStateEnum;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository repository;
    @Autowired
    private ParkingCouponMemberRepository parkingCouponMemberRepository;
    @Autowired
    private GiftCouponMemberRepository giftCouponMemberRepository;
    @Autowired
    private ShopCouponMemberRepository shopCouponMemberRepository;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean isValidCoupon(Coupon coupon) {
        if (isValid(coupon)) {
            if (coupon.getBeginTime() == null && coupon.getEndTime() == null)
                return true;
            if (coupon.getBeginTime() != null && coupon.getEndTime() != null) {
                Calendar today = Calendar.getInstance();
                if (coupon.getBeginTime().before(today) && coupon.getEndTime().after(today)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean isDuplicateCoupon(Coupon couponSummary) {
        return couponSummary.getDuplicate().equals(DuplicateEnum.ISDUPLICATE);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean isPermanent(Coupon couponSummary) {
        return couponSummary.getBeginTime() == null && couponSummary.getEndTime() == null;
    }

    public <T extends Coupon> Predicate[] getPredicateList(CouponQueryForm form, Root<T> root, CriteriaBuilder cb) {
        List<Predicate> criteria = new ArrayList<>();
        if (form.getState() != null) {
            Calendar today = Calendar.getInstance();
            if (form.getState().equals(CouponAdminStateEnum.CLOSED)) {
                criteria.add(cb.equal(root.get("valid"), ValidEnum.INVALID));
            } else if (form.getState().equals(CouponAdminStateEnum.NOEXPIRE)) {
                criteria.add(cb.greaterThan(root.get("beginTime"), today));
            } else if (form.getState().equals(CouponAdminStateEnum.USING)) {
                criteria.add(cb.lessThanOrEqualTo(root.get("beginTime"), today));
                criteria.add(cb.greaterThanOrEqualTo(root.get("endTime"), today));
            } else if (form.getState().equals(CouponAdminStateEnum.EXPIRE)) {
                criteria.add(cb.lessThan(root.get("endTime"), today));
            }
        }
        if (form.getBeginTime() != null) {
            criteria.add(cb.greaterThanOrEqualTo(root.get("beginTime"), form.getBeginTime()));
        }
        if (form.getEndTime() != null)
            criteria.add(cb.lessThanOrEqualTo(root.get("beginTime"), form.getEndTime()));
        return criteria.toArray(new Predicate[0]);
    }

    @Override
    public boolean canUpdate(Coupon coupon) {
        Calendar today = Calendar.getInstance();
        return coupon.getBeginTime().after(today);
    }

    @Override
    public Page<CouponJson> findAll(Pageable pageable) {
        Page<Coupon> coupons = repository.findByValid(ValidEnum.VALID, pageable);
        List<CouponJson> results = coupons.getContent().stream().map(i -> {
            CouponJson json = new CouponJson(i);
            if (i instanceof ShopCoupon) {
                json.setType("s");
            } else if (i instanceof ParkingCoupon) {
                json.setType("p");
            } else if (i instanceof GiftCoupon) {
                json.setType("g");
            }
            return json;
        }).collect(Collectors.toList());
        return new PageImpl<CouponJson>(results, pageable, coupons.getTotalElements());
    }

    @Override
    public Page<CouponJson> findByMember(Member member, Pageable pageable) {
        Page<ParkingCouponMember> parkingList = parkingCouponMemberRepository.findByMember(member, pageable);
        Page<GiftCouponMember> giftCouponList = giftCouponMemberRepository.findByMember(member, pageable);
        Page<ShopCouponMember> shopCouponMemberList = shopCouponMemberRepository.findByMember(member, pageable);
        List<CouponJson> parkingJson = parkingList.getContent()
                .stream()
                .filter(i ->
                        i.getCoupon().getValid().equals(ValidEnum.VALID) && i.getTotal() > 0
                )
                .map(i -> {
                    CouponJson json = new CouponJson(i.getCoupon());
                    json.setType("p");
                    json.setTotal(i.getTotal());
                    return json;
                }).collect(Collectors.toList());
        List<CouponJson> preferentialJson = giftCouponList.getContent()
                .stream()
                .filter(i ->
                        i.getCoupon().getValid().equals(ValidEnum.VALID) && i.getUsedTime() == null
                )
                .map(i -> {
                    CouponJson json = new CouponJson(i.getCoupon());
                    json.setType("g");
                    return json;
                })
                .collect(Collectors.toList());
        List<CouponJson> shopJson = shopCouponMemberList.getContent()
                .stream()
                .filter(i ->
                        i.getCoupon().getValid().equals(ValidEnum.VALID) && i.getUsedTime() == null
                )
                .map(i -> {
                    CouponJson json = new CouponJson(i.getCoupon());
                    json.setType("s");
                    return json;
                })
                .collect(Collectors.toList());
        parkingJson.addAll(preferentialJson);
        parkingJson.addAll(shopJson);
        parkingJson.sort((o1, o2) -> {
            if (o1.getCouponTime().before(o2.getCouponTime()))
                return 1;
            return -1;
        });
        Long max = Math.max(shopCouponMemberList.getTotalElements(), Math.max(parkingList.getTotalElements(), giftCouponList.getTotalElements()));
        return new PageImpl<>(parkingJson.stream().limit(15).collect(Collectors.toList()), pageable, max);
    }
}
