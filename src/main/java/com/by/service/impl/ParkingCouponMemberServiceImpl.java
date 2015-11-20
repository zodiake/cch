package com.by.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.LicenseCannotBeNull;
import com.by.exception.MemberNotFoundException;
import com.by.exception.NoCouponException;
import com.by.exception.NotEnoughCouponException;
import com.by.exception.NotFoundException;
import com.by.form.AdminCouponForm;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.repository.ParkingCouponMemberRepository;
import com.by.service.LicenseService;
import com.by.service.MemberService;
import com.by.service.ParkingCouponMemberHistroyService;
import com.by.service.ParkingCouponMemberService;
import com.by.service.ParkingCouponService;

@Service
@Transactional
public class ParkingCouponMemberServiceImpl implements ParkingCouponMemberService {
	@Autowired
	private ParkingCouponMemberRepository repository;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ParkingCouponMemberHistroyService historyService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private ParkingCouponService parkingCouponService;

	@Override
	public ParkingCouponMember save(ParkingCoupon coupon, Member m, int total) {
		ParkingCouponMember pcm = new ParkingCouponMember();
		pcm.setCoupon(coupon);
		pcm.setMember(m);
		pcm.setTotal(total);
		return repository.save(pcm);
	}

	@Override
	public ParkingCouponMember getCoupon(AdminCouponForm form) {
		// 未找到会员报错
		Optional<Member> member = memberService.findByName(form.getMobile());
		if (!member.isPresent())
			throw new MemberNotFoundException();
		Member m = member.get();
		// 未找到parkingcoupon报错
		Optional<ParkingCoupon> pc = parkingCouponService.findById(form.getCouponTemplateId());
		if (!pc.isPresent())
			throw new NotFoundException();
		Optional<ParkingCouponMember> pcm = repository.findByMember(m);
		// 如果该会员已经有停车券，则更新
		if (pcm.isPresent()) {
			ParkingCouponMember p = pcm.get();
			int sourceTotal = p.getTotal();
			p.setTotal(sourceTotal + form.getTotal());
			return p;
		}
		// 否则新增一条该用户的停车券记录
		historyService.save(m, form.getTotal(), null);
		return save(pc.get(), m, form.getTotal());
	}

	@Override
	public ParkingCouponMember useCoupon(Member member, int total, String license) {
		Optional<ParkingCouponMember> pcm = repository.findByMember(member);
		//如果该用户无停车券
		if (!pcm.isPresent())
			throw new NoCouponException();
		//该用户的停车券数量为0，或者希望使用的停车券数量大于该用户现有数量
		if (total > pcm.get().getTotal() || pcm.get().getTotal() == 0)
			throw new NotEnoughCouponException();
		int source = pcm.get().getTotal();
		pcm.get().setTotal(source - total);
		// 记录该用户对应的车牌
		licenseService.save(member, license);
		// 记录兑换历史
		if (license == null)
			throw new LicenseCannotBeNull();
		historyService.save(member, total, license);
		return pcm.get();
	}

	@Override
	public Optional<ParkingCouponMember> findByMember(Member member) {
		return repository.findByMember(member);
	}
}
