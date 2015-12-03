package com.by.controller;

import com.by.exception.Success;
import com.by.json.CouponJson;
import com.by.model.Member;
import com.by.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/api/coupons")
public class CouponController {
    @Autowired
    private CouponService service;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Success<List<CouponJson>> list(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        return new Success<List<CouponJson>>(service.findByMemberJson(member));
    }
}
