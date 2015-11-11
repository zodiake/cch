package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Fail;
import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.model.Member;
import com.by.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private MemberService service;
	
	@Autowired
	private ShaPasswordEncoder encoder;

	// 用户注册
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
	public Status signIn(@Valid Member member, BindingResult result) {
		if (result.hasErrors()) {
			Fail fail = new Fail(result.getAllErrors());
			return fail;
		}
		member.setPassword(encoder.encodePassword(member.getPassword(), null));
		Member m = service.save(member);
		return new Success<Member>(m);
	}

	// 用户登入
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ResponseBody
	public Status signUp(@Valid Member member, BindingResult result) {
		member.setPassword(encoder.encodePassword(member.getPassword(), null));
		return service.findByNameAndPassword(member).map(i -> new Status("success"))
				.orElseThrow(() -> new NotFoundException());
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

	// 用户详情
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Success<Member> get(@PathVariable("id") Long id) {
		return service.findById(id).map(i -> new Success<Member>(i)).orElseThrow(() -> new NotFoundException());
	}
}
