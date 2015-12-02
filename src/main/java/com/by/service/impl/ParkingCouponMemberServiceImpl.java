package com.by.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.CouponOutOfDateException;
import com.by.exception.MemberNotFoundException;
import com.by.exception.NoCouponException;
import com.by.exception.NotEnoughCouponException;
import com.by.exception.NotFoundException;
import com.by.form.AdminCouponForm;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.model.Shop;
import com.by.repository.ParkingCouponMemberRepository;
import com.by.service.LicenseService;
import com.by.service.MemberService;
import com.by.service.ParkingCouponExchangeHistoryService;
import com.by.service.ParkingCouponMemberService;
import com.by.service.ParkingCouponService;
import com.by.service.ParkingCouponUseHistoryService;

@Service
@Transactional
public class ParkingCouponMemberServiceImpl implements ParkingCouponMemberService {
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

    @Override
    public ParkingCouponMember save(ParkingCoupon coupon, Member m, int total) {
        ParkingCouponMember pcm = new ParkingCouponMember();
        pcm.setCoupon(coupon);
        pcm.setMember(m);
        pcm.setTotal(total);
        return repository.save(pcm);
    }

    @Override
    public ParkingCouponMember getCouponFromShop(AdminCouponForm form, Shop shop) {
        // 未找到会员报错
        Optional<Member> member = memberService.findByName(form.getMobile());
        if (!member.isPresent())
            throw new MemberNotFoundException();
        Member m = member.get();
        // 未找到parkingcoupon报错
        Optional<ParkingCoupon> pc = parkingCouponService.findById(form.getCouponTemplateId());
        if (!pc.isPresent())
            throw new NotFoundException();
        Optional<ParkingCouponMember> pcm = repository.findByMemberAndCoupon(m, pc.get());
        // 如果该会员已经有停车券，则更新
        if (pcm.isPresent()) {
            ParkingCouponMember p = pcm.get();
            int sourceTotal = p.getTotal();
            p.setTotal(sourceTotal + form.getTotal());
            exchangeHistoryService.save(m, form.getTotal(), shop);
            return p;
        }
        // 否则新增一条该用户的停车券记录
        exchangeHistoryService.save(m, form.getTotal(), shop);
        return save(pc.get(), m, form.getTotal());
    }

    @Override
    public ParkingCouponMember useCoupon(Member member, ParkingCoupon parkingCoupon, int total, String license) {
        Optional<ParkingCouponMember> pcm = repository.findByMemberAndCoupon(member, parkingCoupon);
        // 如果该用户无停车券
        if (!pcm.isPresent())
            throw new NoCouponException();
        ParkingCouponMember couponMember = pcm.get();
        // 该用户的停车券数量为0，或者希望使用的停车券数量大于该用户现有数量
        if (total > pcm.get().getTotal() || pcm.get().getTotal() == 0)
            throw new NotEnoughCouponException();
        if (couponMember.getCoupon().getCouponEndTime() != null && couponMember.getCoupon().getCouponEndTime().after(Calendar.getInstance()))
            throw new CouponOutOfDateException();
        int source = couponMember.getTotal();
        couponMember.setTotal(source - total);
        // 记录该用户对应的车牌
        licenseService.save(member, license);
        useHistoryService.save(member, -total, license, couponMember.getCoupon());
        return couponMember;
    }

    @Override
    public List<ParkingCouponMember> findByMember(Member member) {
        return repository.findByMember(member);
    }

    @Override
    public ParkingCouponMember exchangeCoupon(Member member, ParkingCoupon coupon, int count) {
        Optional<ParkingCoupon> pc = parkingCouponService.findById(coupon.getId());
        if (!pc.isPresent()) {
            throw new NoCouponException();
        }
        Member m = memberService.useScore(member, pc.get().getScore() * count);
        Optional<ParkingCouponMember> pcm = repository.findByMemberAndCoupon(member, coupon);
        //若果该用户已经兑换过停车券，更新数量
        if (pcm.isPresent()) {
            ParkingCouponMember parkingCouponMember = pcm.get();
            parkingCouponMember.setTotal(parkingCouponMember.getTotal() + count);
            return parkingCouponMember;
        }
        //如果没有，新增一条兑换记录
        exchangeHistoryService.save(m, count, null);
        return save(pc.get(), m, count);
    }

    private boolean isGenericParkingCoupon(ParkingCoupon parkingCoupon) {
        return parkingCoupon.getBeginTime() != null && parkingCoupon.getEndTime() != null && parkingCoupon.getCouponEndTime() != null;
    }

    private ParkingCouponMember genericExchangeCoupon(Member member, ParkingCoupon coupon, int count) {
        Member m = memberService.useScore(member, coupon.getScore() * count);
        Optional<ParkingCouponMember> pcm = repository.findByMemberAndCoupon(member, coupon);
        //若果该用户已经兑换过停车券，更新数量
        if (pcm.isPresent()) {
            ParkingCouponMember parkingCouponMember = pcm.get();
            parkingCouponMember.setTotal(parkingCouponMember.getTotal() + count);
            return parkingCouponMember;
        }
        //如果没有，新增一条兑换记录
        exchangeHistoryService.save(m, count, null);
        return save(coupon, m, count);
    }

    @Override
    public ParkingCouponMember update(ParkingCouponMember coupon) {
        Optional<ParkingCouponMember> pcm = repository.findByMemberAndCoupon(coupon.getMember(), coupon.getCoupon());
        pcm.get().setTotal(coupon.getTotal());
        return pcm.get();
    }

    @Override
    public Optional<ParkingCouponMember> findByMemberAndCoupon(Member member, ParkingCoupon parkingCoupon) {
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
