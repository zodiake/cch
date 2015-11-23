package com.by.service.impl;

import java.util.Optional;

import com.by.model.*;
import com.by.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.LicenseCannotBeNull;
import com.by.exception.MemberNotFoundException;
import com.by.exception.NoCouponException;
import com.by.exception.NotEnoughCouponException;
import com.by.exception.NotEnoughScore;
import com.by.exception.NotFoundException;
import com.by.form.AdminCouponForm;
import com.by.repository.ParkingCouponMemberRepository;

@Service
@Transactional
public class ParkingCouponMemberServiceImpl implements ParkingCouponMemberService {
    @Autowired
    private ParkingCouponMemberRepository repository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ParkingCouponExchangeHistroyService exchangeHistroyService;
    @Autowired
    private ParkingCouponUseHistoryService useHistoryService;
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private ParkingCouponService parkingCouponService;
    @Autowired
    private ScoreHistoryService scoreHistoryService;

    @Override
    public ParkingCouponMember save(ParkingCoupon coupon, Member m, int total) {
        ParkingCouponMember pcm = new ParkingCouponMember();
        pcm.setCoupon(coupon);
        pcm.setMember(m);
        pcm.setTotal(total);
        return repository.save(pcm);
    }

    @Override
    public ParkingCouponMember getCoupon(AdminCouponForm form, Shop shop) {
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
        exchangeHistroyService.save(m, form.getTotal(), shop);
        return save(pc.get(), m, form.getTotal());
    }

    @Override
    public ParkingCouponMember useCoupon(Member member, int total, String license) {
        Optional<ParkingCouponMember> pcm = repository.findByMember(member);
        // 如果该用户无停车券
        if (!pcm.isPresent())
            throw new NoCouponException();
        ParkingCouponMember couponMember = pcm.get();
        // 该用户的停车券数量为0，或者希望使用的停车券数量大于该用户现有数量
        if (total > pcm.get().getTotal() || pcm.get().getTotal() == 0)
            throw new NotEnoughCouponException();
        int source = couponMember.getTotal();
        pcm.get().setTotal(source - total);
        // 记录该用户对应的车牌
        licenseService.save(member, license);
        // 记录兑换历史
        if (license == null)
            throw new LicenseCannotBeNull();
        useHistoryService.save(member, total, license, couponMember.getCoupon());
        return couponMember;
    }

    @Override
    public Optional<ParkingCouponMember> findByMember(Member member) {
        return repository.findByMember(member);
    }

    @Override
    public ParkingCouponMember exchangeCoupon(Member member, ParkingCoupon coupon, int count) {
        Optional<Member> mem = memberService.findById(member.getId());
        if (!mem.isPresent())
            throw new MemberNotFoundException();
        Optional<ParkingCoupon> pc = parkingCouponService.findById(coupon.getId());
        if (!pc.isPresent())
            throw new NoCouponException();
        Member m = mem.get();
        ParkingCoupon spc = pc.get();
        int score = m.getScore();
        if (m.getScore() < spc.getScore() * count) {
            throw new NotEnoughScore();
        }
        scoreHistoryService.save(mem.get(), spc.getScore());
        m.setScore(score - spc.getScore() * count);
        Optional<ParkingCouponMember> pcm = repository.findByMember(member);
        if (pcm.isPresent()) {
            int sourceTotal = pcm.get().getTotal();
            pcm.get().setTotal(count + sourceTotal);
            return pcm.get();
        } else {
            return save(coupon, m, count);
        }
    }

    @Override
    public ParkingCouponMember update(ParkingCouponMember coupon) {
        ParkingCouponMember pcm = repository.findByMemberAndCoupon(coupon.getMember(), coupon.getCoupon());
        pcm.setTotal(coupon.getTotal());
        return pcm;
    }
}
