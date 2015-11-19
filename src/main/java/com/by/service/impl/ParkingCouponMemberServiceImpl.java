package com.by.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.NotFoundException;
import com.by.form.AdminCouponForm;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.model.ParkingCouponMemberHistory;
import com.by.repository.ParkingCouponMemberRepository;
import com.by.service.MemberService;
import com.by.service.ParkingCouponMemberHistroyService;
import com.by.service.ParkingCouponMemberService;

@Service
@Transactional
public class ParkingCouponMemberServiceImpl implements ParkingCouponMemberService {
	@Autowired
	private ParkingCouponMemberRepository repository;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ParkingCouponMemberHistroyService historyService;

	@Override
	public ParkingCouponMember save(AdminCouponForm form) {
		Optional<Member> member = memberService.findByName(form.getMobile());
		if (!member.isPresent())
			throw new NotFoundException();
		ParkingCoupon pc = new ParkingCoupon(form.getCouponTemplateId());
		ParkingCouponMember pcm = new ParkingCouponMember();
		pcm.setCoupon(pc);
		pcm.setMember(member.get());
		pcm.setTotal(form.getTotal());
		saveHistory(member.get(), form);
		return repository.save(pcm);
	}

	//流水日志
	private void saveHistory(Member member, AdminCouponForm form) {
		ParkingCouponMemberHistory history = new ParkingCouponMemberHistory();
		history.setMember(member);
		history.setTotal(form.getTotal());
		historyService.save(history);
	}

}
