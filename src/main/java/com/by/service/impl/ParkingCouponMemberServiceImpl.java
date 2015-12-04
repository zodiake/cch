package com.by.service.impl;

import com.by.exception.*;
import com.by.form.AdminCouponForm;
import com.by.json.ParkingCouponJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.model.Shop;
import com.by.repository.ParkingCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParkingCouponMemberServiceImpl implements ParkingCouponMemberService {
    private final String reason = "";
    @Autowired
    private ParkingCouponMemberRepository repository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ParkingCouponExchangeHistoryService exchangeHistoryService;
    @Autowired
    private ParkingCouponUseHistoryService useHistoryService;
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private ParkingCouponService parkingCouponService;
    @Autowired
    private CouponService couponService;

    @Override
    public ParkingCouponMember save(ParkingCoupon coupon, Member m, int total) {
        ParkingCouponMember pcm = new ParkingCouponMember();
        pcm.setCoupon(coupon);
        pcm.setMember(m);
        pcm.setTotal(total);
        return repository.save(pcm);
    }

    @Override
    public ParkingCouponMember save(ParkingCouponMember parkingCouponMember) {
        return repository.save(parkingCouponMember);
    }

    @Override
    public ParkingCouponMember getCouponFromShop(AdminCouponForm form, Shop shop) {
        return null;
    }

    @Override
    public ParkingCouponMember useCoupon(Member member, ParkingCoupon parkingCoupon, int total, String license) {
        ParkingCouponMember couponMember = repository.findByMemberAndCoupon(member, parkingCoupon);
        ParkingCoupon sourceCoupon = couponMember.getCoupon();
        int sourceTotal = couponMember.getTotal();
        if (couponMember == null)
            throw new NotEnoughCouponException();
        if (sourceTotal < total)
            throw new NotEnoughCouponException();
        if (!couponService.isValidCoupon(sourceCoupon))
            throw new NotValidException();
        couponMember.setTotal(sourceTotal - total);
        licenseService.save(member, license);
        useHistoryService.save(member, total, license, parkingCoupon);
        return couponMember;
    }

    @Override
    public ParkingCouponMember exchangeCoupon(Member member, ParkingCoupon coupon, int total) {
        Optional<Member> sourceMember = memberService.findById(member.getId());
        ParkingCoupon sourceCoupon = parkingCouponService.findOne(coupon.getId());
        if (!sourceMember.isPresent())
            throw new MemberNotFoundException();
        if (sourceMember.get().getValid().equals(ValidEnum.INVALID))
            throw new MemberNotValidException();
        if (!couponService.isValidCoupon(sourceCoupon))
            throw new NotValidException();
        if (sourceCoupon.getScore() * total > sourceMember.get().getScore())
            throw new NotEnoughScoreException();
        ParkingCouponMember pcm = repository.findByMemberAndCoupon(member, coupon);
        if (sourceCoupon.getDuplicate().equals(DuplicateEnum.ISDUPLICATE)) {
            if (pcm != null) {
                pcm.setTotal(pcm.getTotal() + total);
            } else {
                pcm = save(sourceCoupon, sourceMember.get(), total);
            }
        } else {
            if (pcm == null)
                pcm = save(sourceCoupon, sourceMember.get(), 1);
            else
                throw new AlreadyExchangeException();
        }
        memberService.updateScore(sourceMember.get(), -total * sourceCoupon.getScore(), reason);
        exchangeHistoryService.save(sourceMember.get(), sourceCoupon, total);
        return pcm;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingCouponMember> findByMember(Member member) {
        List<ParkingCouponMember> lists = repository.findByMember(member);
        return lists.stream()
                .filter(i -> {
                    return couponService.isValidCoupon(i.getCoupon());
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingCouponJson> findByMemberJson(Member member) {
        return findByMember(member).stream()
                .map(i -> {
                    ParkingCouponJson json = new ParkingCouponJson();
                    json.setId(i.getCoupon().getId());
                    json.setName(i.getCoupon().getName());
                    return json;
                }).collect(Collectors.toList());
    }

    @Override
    public ParkingCouponMember update(ParkingCouponMember coupon) {
        ParkingCouponMember pcm = repository.findByMemberAndCoupon(coupon.getMember(), coupon.getCoupon());
        if (pcm != null)
            pcm.setTotal(coupon.getTotal());
        else
            repository.save(coupon);
        return pcm;
    }

    @Override
    public ParkingCouponMember findByCouponAndMember(Member member, ParkingCoupon parkingCoupon) {
        return repository.findByMemberAndCoupon(member, parkingCoupon);
    }

    @Override
    public Long sumTotalGroupByCoupon(ParkingCoupon coupon) {
        return repository.sumTotalGroupByCoupon(coupon);
    }

    @Override
    public Long countByCouponAndMember(ParkingCoupon parkingCoupon, Member member) {
        return repository.countByCouponAndMember(parkingCoupon, member);
    }
}
