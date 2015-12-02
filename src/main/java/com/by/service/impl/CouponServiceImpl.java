package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.model.Member;
import com.by.repository.CouponRepository;
import com.by.service.CouponService;
import com.by.service.MemberService;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {
	@Autowired
	private CouponRepository repository;
	@Autowired
	private MemberService memberService;

	@Override
	public Coupon bindMember(CouponSummary summary, Member member) {
		memberService.useScore(member, summary.getScore());
		List<Coupon> lists = repository.findBySummary(summary);
		Coupon c = lists.stream().filter(i -> i.getMember() == null).collect(Collectors.toList()).get(0);
		c.setMember(member);
		return c;
	}

	public Coupon useCoupon(Coupon coupon) {
		return null;
	}

	@Override
	public Coupon save(Coupon coupon) {
		return repository.save(coupon);
	}

	@Override
	public Long count() {
		return repository.count();
	}

	@Override
	public Coupon findById(Long id) {
		Coupon coupon = repository.findOne(id);
		coupon.getSummary();
		return coupon;
	}

	@Override
	public Long countBySummaryWhereMemberIsNull(CouponSummary summary) {
		return repository.countBySummaryWhereMemberIsNull(summary);
	}

	@Override
	public Long countBySummary(CouponSummary summary) {
		return repository.countBySummary(summary);
	}

	@Override
	public Long countBySummaryAndMember(CouponSummary summary, Member member) {
		return repository.countBySummaryAndMember(summary, member);
	}
}
