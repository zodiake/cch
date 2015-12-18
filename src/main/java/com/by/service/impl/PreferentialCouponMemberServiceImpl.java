package com.by.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.AlreadyUsedException;
import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import com.by.model.Sequence;
import com.by.repository.PreferentialCouponMemberRepository;
import com.by.service.CouponService;
import com.by.service.MemberService;
import com.by.service.PreferentialCouponMemberService;
import com.by.service.PreferentialCouponService;
import com.by.service.SequenceService;
import com.by.typeEnum.CouponStateEnum;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ScoreHistoryEnum;

/**
 * Created by yagamai on 15-12-3.
 */
@Service
@Transactional
public class PreferentialCouponMemberServiceImpl implements PreferentialCouponMemberService {
	private final String reason = "礼品兑换";
	private final DateFormat format = new SimpleDateFormat("yyyyMMddS");
	@Autowired
	private PreferentialCouponMemberRepository repository;
	@Autowired
	private PreferentialCouponService preferentialCouponService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private SequenceService sequenceService;

	@Override
	public PreferentialCouponMember useCoupon(String code, Member member) {
		PreferentialCouponMember pcm = repository.findByCode(code);
		if (pcm.getState().equals(CouponStateEnum.USED))
			throw new AlreadyUsedException();
		pcm.setUsedTime(Calendar.getInstance());
		return pcm;
	}

	@Override
	public void exchangeCoupon(PreferentialCoupon coupon, Member member, int total) {
		int count = total;
		Optional<Member> memberOptional = memberService.findById(member.getId());
		PreferentialCoupon sourceCoupon = preferentialCouponService.findOne(coupon.getId());
		if (coupon.getDuplicate().equals(DuplicateEnum.ISDUPLICATE)) {
			for (int i = 1; i <= total; i++) {
				save(sourceCoupon, memberOptional.get());
			}
		} else {
			count = 1;
			save(sourceCoupon, memberOptional.get());
		}
		memberService.minusScore(memberOptional.get(), sourceCoupon.getScore()*count, reason, ScoreHistoryEnum.COUPONEXCHANGE);
	}

	public List<PreferentialCouponMember> findByCouponAndMember(PreferentialCoupon coupon, Member member) {
		return repository.findByCouponAndMember(coupon, member);
	}

	@Override
	public Long sumTotalGroupByCoupon(PreferentialCoupon coupon) {
		return repository.countByCoupon(coupon);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CouponJson> findByMember(Member member, Pageable pageable) {
		return repository.findByMember(member, pageable).getContent().stream().filter(i -> {
			return i.getUsedTime() == null && couponService.isValidCoupon(i.getCoupon());
		}).map(i -> {
			Coupon c = i.getCoupon();
			return new CouponJson(c.getId(), c.getName(), c.getEndTime(), null);
		}).collect(Collectors.toList());
	}

	public PreferentialCouponMember save(PreferentialCouponMember pcm) {
		return repository.save(pcm);
	}

	public PreferentialCouponMember save(PreferentialCoupon coupon, Member member) {
		Sequence sequence = sequenceService.save(new Sequence());
		PreferentialCouponMember pcm = new PreferentialCouponMember(member, coupon);
		pcm.setCode(format.format(Calendar.getInstance().getTime()) + sequence.getId());
		return save(pcm);
	}

}
