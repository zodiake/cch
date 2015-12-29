package com.by.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Success;
import com.by.json.LicenseJson;
import com.by.model.Member;
import com.by.service.LicenseService;

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
    public Success<List<LicenseJson>> memberLicense(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        return new Success<>(service.findByMember(member));
    }
}
