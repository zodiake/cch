package com.by.service.impl;

import com.by.model.Coupon;
import com.by.service.CouponService;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class CouponServiceImpl implements CouponService {

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
}
