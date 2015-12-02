package com.by.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.MemberDetailJson;
import com.by.model.Member;
import com.by.service.MemberDetailService;
import com.by.utils.FailBuilder;

@Controller
@RequestMapping(value = "/api/member")
public class MemberDetailController {
	@Autowired
	private MemberDetailService service;

	@RequestMapping(value = "/details", method = RequestMethod.PUT)
	@ResponseBody
	public Status update(@Valid @RequestBody MemberDetailJson detail, BindingResult result,
			HttpServletRequest request) {
		if(result.hasErrors()){
			return FailBuilder.buildFail(result);
		}
		Member m = (Member) request.getAttribute("member");
		return service.update(m, detail).map(i -> new Status("success")).orElseThrow(() -> new NotFoundException());
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	@ResponseBody
	public Status get(HttpServletRequest request) {
		Member m = (Member) request.getAttribute("member");
		return service.findByMember(m).map(i -> new Success<MemberDetailJson>(new MemberDetailJson(i)))
				.orElseThrow(() -> new NotFoundException());
	}
}
