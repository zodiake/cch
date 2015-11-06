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
public class MemberController extends UntilController<Member> {
	@Autowired
	private MemberService service;

	@Autowired
	private ShaPasswordEncoder encoder;

	//用户注册
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Status save(@Valid Member member, BindingResult result) {
		if (result.hasErrors()) {
			Fail fail = new Fail(result.getAllErrors());
			return fail;
		}
		member.setPassword(encoder.encodePassword(member.getPassword(), null));
		service.save(member);
		return new Success();
	}

	//用户是否存在 存在返回status fail 不存在返回success
	@RequestMapping(value = "/exist", method = RequestMethod.GET)
	@ResponseBody
	public Status count(@RequestParam("mobile") String mobile) {
		return service.findByName(mobile).map(i -> new Status("fail")).orElseGet(() -> new Success());
	}

	//修改用户密码
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	@ResponseBody
	public Status changePassword(@RequestBody Member member) {
		return service.update(member).map(i -> new Status("success")).orElseThrow(() -> new NotFoundException());
	}

	//用户详情
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Member get(@PathVariable("id") Long id) {
		return service.findById(id).orElseThrow(() -> new NotFoundException());
	}
}
