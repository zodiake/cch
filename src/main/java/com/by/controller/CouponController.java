package com.by.controller;

import com.by.model.CouponSummary;
import com.by.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/coupon")
public class CouponController {
    @Autowired
    private CouponService service;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<CouponSummary> list() {
        return null;
    }
}
