package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.MemberDetailJson;
import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.service.MemberDetailService;
import com.by.service.MemberService;
import com.by.utils.FailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/member")
public class MemberDetailController {
    @Autowired
    private MemberDetailService service;
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/details", method = RequestMethod.PUT)
    @ResponseBody
    public Status update(@Valid @RequestBody MemberDetailJson detail, BindingResult result,
                         HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        return new Success<>(new MemberDetailJson(service.update(member.getId(), detail)));
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ResponseBody
    public Status get(HttpServletRequest request) {
        Member m = (Member) request.getAttribute("member");
        MemberDetail detail = memberService.findOneCache(m.getId()).getMemberDetail();
        return new Success<>(new MemberDetailJson(detail));
    }
}
