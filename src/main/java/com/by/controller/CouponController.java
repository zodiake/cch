package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CouponJson;
import com.by.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yagamai on 15-12-16.
 */
@Controller
@RequestMapping(value = "/api/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Status list(@PageableDefault(page = 0, size = 10, sort = "couponEndTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<Page<CouponJson>>(couponService.findAll(pageable));
    }
}
