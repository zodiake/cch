package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.json.CouponJson;
import com.by.model.Coupon;
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
        return coupon;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Coupon> findByMember(Member member) {
        //todo
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponJson> findByMemberJson(Member member) {
    	return null;
    }
}
