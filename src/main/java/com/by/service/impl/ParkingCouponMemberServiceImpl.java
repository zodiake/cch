package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.ParkingCouponMemberJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.repository.ParkingCouponMemberRepository;
import com.by.service.ParkingCouponMemberService;

@Service
@Transactional
public class ParkingCouponMemberServiceImpl implements ParkingCouponMemberService {
	@Autowired
	private ParkingCouponMemberRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Page<ParkingCouponMember> findByMember(Member member, Pageable pageable) {
		return repository.findByMember(member, pageable);
	}

	@Override
	public ParkingCouponMember save(ParkingCouponMember pcm) {
		return repository.save(pcm);
	}

	@Override
	public ParkingCouponMember save(Member member, ParkingCoupon pc, int total) {
		ParkingCouponMember pcm = new ParkingCouponMember();
		pcm.setCoupon(pc);
		pcm.setMember(member);
		pcm.setTotal(total);
		return pcm;
	}

	@Override
	public Page<ParkingCouponMemberJson> toJson(Page<ParkingCouponMember> page, Pageable pageable) {
		List<ParkingCouponMemberJson> json = page.getContent().stream().map(i -> new ParkingCouponMemberJson(i))
				.collect(Collectors.toList());
		return new PageImpl<>(json, pageable, page.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public ParkingCouponMember findByMemberAndCoupon(Member member, ParkingCoupon coupon) {
		return repository.findByMemberAndCoupon(member, coupon);
	}

	@Override
	public List<ParkingCouponMember> findByMember(Member member,Sort sort) {
		return repository.findByMember(member,sort);
	}
}
