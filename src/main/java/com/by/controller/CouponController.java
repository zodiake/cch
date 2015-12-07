package com.by.controller;

import com.by.exception.Success;
import com.by.json.CouponTemplateJson;
import com.by.model.Member;
import com.by.service.ParkingCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/api/coupon")
public class CouponController {
    @Autowired
    private ParkingCouponService service;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Success<List<CouponTemplateJson>> list(HttpServletRequest request) {
        //todo
        Member member = (Member) request.getAttribute("member");
        return null;
    }
}
