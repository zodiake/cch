package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Fail;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.model.Member;
import com.by.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController extends UntilController<Member>{
	@Autowired
	public MemberService service;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Status save(@Valid Member member, BindingResult result) {
		if (result.hasErrors()) {
			Fail fail = new Fail(result.getAllErrors());
			return fail;
		}
		return new Success();
	}

	@RequestMapping(value = "/exist",method=RequestMethod.GET)
	@ResponseBody
	public Status count(@RequestParam("mobile") String mobile) {
		return fold(service.findByName(mobile), () -> new Fail("user exist"), () -> new Success());
	}
}
