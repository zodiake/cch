package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.model.Member;
import com.by.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yagamai on 15-12-14.
 */
@Controller
@RequestMapping(value = "/api/license")
public class LicenseController {
    @Autowired
    private LicenseService service;

    // 该用户的所有车牌
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Status memberLicense(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        return new Success(service.findByMember(member));
    }
}
