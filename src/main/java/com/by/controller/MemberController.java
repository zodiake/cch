package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.by.exception.Fail;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.MemberRequestJson;
import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.service.MemberService;
import com.by.utils.FailBuilder;
import com.by.utils.JWTUtils;

@RestController
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private MemberService service;
	@Autowired
	@Qualifier("memberNameValidator")
	private Validator validator;

	@Autowired
	private ShaPasswordEncoder encoder;

	// 用户注册
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
	public Status signIn(@Valid @RequestBody MemberRequestJson json, BindingResult result) {
		validator.validate(json, result);
		if (result.hasErrors()) {
			return FailBuilder.buildFail(result);
		}
		MemberDetail detail = new MemberDetail();
		if (!StringUtils.isEmpty(json.getPassword())) {
			detail.setPassword(encoder.encodePassword(json.getPassword(), null));
		}
		Member member = new Member(json);
		member.setMemberDetail(detail);
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
