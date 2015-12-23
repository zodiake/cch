package com.by.service.impl;

import com.by.exception.AlreadyExchangeException;
import com.by.exception.NotFoundException;
import com.by.exception.NotValidException;
import com.by.exception.OutOfStorageException;
import com.by.form.AdminCouponForm;
import com.by.json.CouponJson;
import com.by.json.CouponTemplateJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.model.Shop;
import com.by.repository.ParkingCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.ScoreHistoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
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
    private CouponService couponService;
    @Autowired
    private EntityManager em;

    @Override
    public ParkingCouponMember save(ParkingCoupon coupon, Member m, int total) {
        ParkingCouponMember pcm = repository.findByMemberAndCoupon(m, coupon);
        if (pcm != null) {
            ParkingCouponMember p = new ParkingCouponMember();
            p.setCoupon(coupon);
            p.setMember(m);
            p.setTotal(total);
            return p;
        }
        pcm.setTotal(pcm.getTotal() + total);
        exchangeHistoryService.save(m, coupon, total);
        return pcm;
    }

    @Override
    public ParkingCouponMember save(ParkingCouponMember parkingCouponMember) {
        return save(parkingCouponMember.getCoupon(), parkingCouponMember.getMember(), parkingCouponMember.getTotal());
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
        if (sourceTotal < total)
            throw new OutOfStorageException();
        if (!couponService.couponIsWithinValidDate(sourceCoupon))
            throw new NotValidException();
        couponMember.setTotal(sourceTotal - total);
        licenseService.save(member, license);
        useHistoryService.save(member, total, license, parkingCoupon);
        return couponMember;
    }

    @Override
    public void exchangeCoupon(Member member, ParkingCoupon coupon, int total) {
        int count = total;
        Member m = em.find(Member.class, member.getId());
        ParkingCoupon sourceCoupon = em.find(ParkingCoupon.class, coupon.getId());
        ParkingCouponMember pcm = repository.findByMemberAndCoupon(member, coupon);

        if (sourceCoupon == null)
            throw new NotFoundException();
        if (couponService.isValidCoupon(coupon)) {
            // 如果不能重复
            if (!couponService.isDuplicateCoupon(coupon)) {
                if (pcm != null)
                    throw new AlreadyExchangeException();
                count = 1;
            }
            // 判断是否有库存
            if (!couponService.noStorageLimited(coupon)) {
                if (outOfStorage(coupon, total))
                    throw new OutOfStorageException();
            }
            save(sourceCoupon, m, count);
            memberService.minusScore(m, sourceCoupon.getScore() * count, reason, ScoreHistoryEnum.COUPONEXCHANGE);
        } else {
            throw new NotValidException();
        }
    }

    private boolean outOfStorage(ParkingCoupon coupon, int count) {
        Long total = sumTotalGroupByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingCouponMember> findByMember(Member member) {
        List<ParkingCouponMember> lists = repository.findByMember(member);
        return lists.stream().filter(i ->
                couponService.isValidCoupon(i.getCoupon()) && i.getTotal() > 0
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponTemplateJson> findByMemberJson(Member member) {
        return findByMember(member).stream().map(i -> {
            CouponTemplateJson json = new CouponTemplateJson();
            json.setId(i.getCoupon().getId());
            json.setName(i.getCoupon().getName());
            return json;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CouponJson> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member, pageable)
                .getContent()
                .stream()
                .filter(i ->
                        couponService.isValidCoupon(i.getCoupon())
                ).map(i -> {
                    CouponJson json = new CouponJson(i.getCoupon());
                    json.setTotal(i.getTotal());
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
