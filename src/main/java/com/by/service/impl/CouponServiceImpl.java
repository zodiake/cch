package com.by.service.impl;

import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.ParkingCoupon;
import com.by.model.PreferentialCoupon;
import com.by.model.ShopCoupon;
import com.by.repository.CouponRepository;
import com.by.service.CouponService;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository repository;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean isWithinValidDate(Coupon coupon) {
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
        coupon.getCouponEndTime().add(Calendar.DATE, 1);
        if (coupon.getCouponEndTime().before(today))
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
    public boolean withinValidDate(Coupon couponSummary) {
        Calendar today = Calendar.getInstance();
        couponSummary.getEndTime().add(1, Calendar.DATE);
        return couponSummary.getBeginTime().before(today) && couponSummary.getEndTime().after(today);
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
            } else if (i instanceof PreferentialCoupon) {
                json.setType("c");
            }
            return json;
        }).collect(Collectors.toList());
        return new PageImpl<CouponJson>(results, pageable, coupons.getTotalElements());
    }
}
