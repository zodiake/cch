package com.by.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.model.Member;
import com.by.repository.CouponRepository;
import com.by.service.CouponService;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository repository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ShaPasswordEncoder encoder;

    @Override
    public Coupon bindMember(CouponSummary summary, Member member) {
        Member source = memberService.findOne(member.getId());
        if (source.getValid().equals(ValidEnum.INVALID))
            throw new MemberNotValidException();
        if (source.getPassword() != null && !encoder.encodePassword(member.getPassword(), null).equals(source.getPassword()))
            throw new PasswordNotMatchException();
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
    @Transactional(readOnly = true)
    public Long count() {
        return repository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Coupon findById(Long id) {
        Coupon coupon = repository.findOne(id);
        coupon.getSummary();
        return coupon;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countBySummaryWhereMemberIsNull(CouponSummary summary) {
        return repository.countBySummaryWhereMemberIsNull(summary);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countBySummary(CouponSummary summary) {
        return repository.countBySummary(summary);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countBySummaryAndMember(CouponSummary summary, Member member) {
        return repository.countBySummaryAndMember(summary, member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Coupon> findByMember(Member member) {
        return repository.findValidCouponByMember(ValidEnum.VALID, member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponJson> findByMemberJson(Member member) {
        List<Coupon> coupons = findByMember(member);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<CouponJson> temp = coupons.stream()
                .filter(i -> {
                    Calendar beginTime = i.getSummary().getBeginTime();
                    Calendar endTime = i.getSummary().getEndTime();
                    if (beginTime == null && endTime == null) {
                        return true;
                    }
                    if (beginTime != null && endTime != null) {
                        endTime.add(1, Calendar.DATE);
                        Calendar today = Calendar.getInstance();
                        if (beginTime.before(today) && endTime.after(today)) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                })
                .map(i -> {
                    CouponJson json = new CouponJson();
                    json.setCode(i.getCode());
                    json.setId(i.getId());
                    json.setName(i.getSummary().getName());
                    json.setBeginTime(format.format(i.getSummary().getBeginTime().getTime()));
                    json.setEndTime(format.format(i.getSummary().getEndTime().getTime()));
                    json.setSummary(i.getSummary().getSummary());
                    json.setSummaryId(i.getSummary().getId());
                    return json;
                }).collect(Collectors.toList());
        Map<String,Long> maps=temp.stream().collect(Collectors.groupingBy(CouponJson::getSummary,Collectors.counting()));
        temp.forEach(i->{
        	Long total=maps.get(i.getSummary());
        	i.setTotal(total.intValue());
        });
        return results;
    }
}
