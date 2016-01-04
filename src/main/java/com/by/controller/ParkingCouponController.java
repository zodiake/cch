package com.by.controller;

import com.by.exception.MemberNotValidException;
import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CouponTemplateJson;
import com.by.json.ExchangeCouponJson;
import com.by.json.UseCouponJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.service.MemberService;
import com.by.service.ParkingCouponService;
import com.by.utils.FailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by yagamai on 15-12-3.
 */
@Controller
@RequestMapping(value = "/api/parkingCoupons")
public class ParkingCouponController implements UtilContoller {
    private final String PASSWORD_ERROR_MESSAGE = "密码错误";
    private ParkingCouponService service;
    private MemberService memberService;
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    public ParkingCouponController(ParkingCouponService parkingCouponService, ShaPasswordEncoder passwordEncoder, MemberService memberService) {
        this.service = parkingCouponService;
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    // 兑换停车券
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Status exchangeParkingCoupon(HttpServletRequest request, @Valid @RequestBody ExchangeCouponJson json,
                                        BindingResult result) {
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOneCache(m.getId());
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        if (!isValidMember(member)) {
            throw new MemberNotValidException();
        }
        if (!StringUtils.isEmpty(json.getPassword())) {
            if (!passwordEncoder.encodePassword(json.getPassword(), null)
                    .equals(member.getMemberDetail().getPassword())) {
                result.addError(new FieldError("member", "password", PASSWORD_ERROR_MESSAGE));
            }
        }
        ParkingCoupon coupon = new ParkingCoupon(json.getId());
        service.exchangeCoupon(member, coupon, json.getTotal());
        return new Status("success");
    }

    // 使用停车券
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Status useCoupon(HttpServletRequest request, @RequestBody UseCouponJson json, BindingResult result) {
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOneCache(m.getId());
        if (!isValidMember(member)) {
            throw new MemberNotValidException();
        }

        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        service.useCoupon(member, json.getTotal(), json.getLicense());
        return new Status("success");
    }

    // 可以兑换的停车券模板列表
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Status list(HttpServletRequest request) {
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOneCache(m.getId());
        if (!isValidMember(member)) {
            throw new MemberNotValidException();
        }
        ParkingCoupon parkingCoupon = service.findActivate();
        return new Success<>(parkingCoupon);
    }

    // 用户兑换到的停车券数量
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    @ResponseBody
    public Status couponList(HttpServletRequest request) {
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOneCache(m.getId());
        if (!isValidMember(member)) {
            throw new MemberNotValidException();
        }
        return new Success<>(member.getTotalParkingCoupon());
    }

    // 停车券详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Status detail(@PathVariable("id") int id) {
        ParkingCoupon coupon = service.findOneCache(id);
        if (coupon == null)
            throw new NotFoundException();
        return new Success<>(new CouponTemplateJson(coupon));
    }
}
