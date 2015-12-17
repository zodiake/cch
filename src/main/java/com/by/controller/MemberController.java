package com.by.controller;

import com.by.exception.Fail;
import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.MemberJson;
import com.by.json.MemberRequestJson;
import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.service.MemberService;
import com.by.utils.FailBuilder;
import com.by.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/member")
public class MemberController {
    @Autowired
    private MemberService service;

    @Autowired
    private ShaPasswordEncoder encoder;

    // 用户注册
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public Status signIn(@Valid @RequestBody MemberRequestJson json, BindingResult result) {
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        if (!StringUtils.isEmpty(json.getPassword()))
            json.setPassword(encoder.encodePassword(json.getPassword(), null));
        Member member = new Member(json);
        member.setMemberDetail(new MemberDetail());
        Member m = service.save(member);
        return new Success<>(JWTUtils.encode(m));
    }

    // 用户是否存在 存在返回status fail 不存在返回success
    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    @ResponseBody
    public Status count(@RequestParam("mobile") String mobile) {
        Long count = service.countByName(mobile);
        if (count > 0)
            return new Fail("use already exist");
        return new Success<>("");
    }

}
