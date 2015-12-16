package com.by.controller;

import com.by.exception.NotValidException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CouponJson;
import com.by.model.Member;
import com.by.service.CouponService;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yagamai on 15-12-16.
 */
@Controller
@RequestMapping(value = "/api/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberService memberService;

    // 可以兑换的优惠券
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Status couponnList(HttpServletRequest request,
                              @PageableDefault(page = 0, size = 10, sort = "couponEndTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Member member = (Member) request.getAttribute("member");
        Member m = memberService.findOne(member.getId());
        if (m.getValid().equals(ValidEnum.INVALID))
            throw new NotValidException();
        return new Success<Page<CouponJson>>(couponService.findAll(pageable));
    }

    // 兑换到的优惠券
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    @ResponseBody
    public Status list(HttpServletRequest request,
                       @PageableDefault(page = 0, size = 10, sort = "couponEndTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Member member = (Member) request.getAttribute("member");
        Member m = memberService.findOne(member.getId());
        if (m.getValid().equals(ValidEnum.INVALID))
            throw new NotValidException();
        return new Success<>(couponService.findByMember(m, pageable));
    }
}
