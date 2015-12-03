package com.by.actor;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.by.message.CouponMessage;
import com.by.model.CouponSummary;
import com.by.service.CouponService;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;

import akka.actor.UntypedActor;

/**
 * Created by yagamai on 15-12-1.
 */
@Component("CouponActor")
@Scope("prototype")
public class CouponActor extends UntypedActor {
	@Autowired
	private CouponService couponService;

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof CouponMessage) {
			CouponMessage coupon = (CouponMessage) message;
			CouponSummary couponSummary = coupon.getSummary();
			// 卡券无效，不能兑换
			if (couponSummary.getValid().equals(ValidEnum.VALID)) {
				// 如果开始截止日期都非空，则判断是否有效期内
				if (couponSummary.getBeginTime() != null && couponSummary.getEndTime() != null) {
					Calendar today = Calendar.getInstance();
					// 如果在有效期内
					// 截止日期应为设定的截止日期后一天
					couponSummary.getEndTime().add(1, Calendar.DATE);
					if (couponSummary.getBeginTime().before(today) && couponSummary.getEndTime().after(today)) {
						// 判断是否可以重复兑换
						if (couponSummary.getDuplicate().equals(DuplicateEnum.NOTDUPLICATE)) {
							Long count = couponService.countBySummaryAndMember(couponSummary, coupon.getMember());
							if (count > 0) {
								sender().tell("duplicate", null);
							}
						}
						Long total = couponService.countBySummaryWhereMemberIsNull(couponSummary);
						// 是否有剩余
						if (total == 0) {
							// 全部兑换
							sender().tell("out of storage", null);
						} else {
							couponService.bindMember(couponSummary, coupon.getMember());
							sender().tell("success", null);
						}
					} else {
						sender().tell("out of date", null);
					}
				} else {
					couponService.bindMember(couponSummary, coupon.getMember());
					sender().tell("success", null);
				}
			} else {
				sender().tell("not valid", null);
			}
		} else {
			unhandled(message);
		}
	}
}
