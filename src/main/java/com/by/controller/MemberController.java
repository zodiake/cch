package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.MemberRequestJson;
import com.by.model.Member;
import com.by.service.MemberService;
import com.by.utils.FailBuilder;
import com.by.utils.JWTUtils;

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
    public Status signIn(@Valid @RequestBody MemberRequestJson member, BindingResult result) {
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        if (!StringUtils.isEmpty(member.getPassword()))
            member.setPassword(encoder.encodePassword(member.getPassword(), null));
        Member m = service.save(new Member(member));
        return new Success<String>(JWTUtils.encode(m));
    }

    // 用户是否存在 存在返回status fail 不存在返回success
    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    @ResponseBody
    public Status count(@RequestParam("mobile") String mobile) {
        return service.findByName(mobile).map(i -> new Status("fail")).orElseGet(() -> new Status("success"));
    }

    // 修改用户密码
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    @ResponseBody
    public Status changePassword(@RequestBody Member member) {
        return service.update(member).map(i -> new Status("success")).orElseThrow(() -> new NotFoundException());
    }

}
