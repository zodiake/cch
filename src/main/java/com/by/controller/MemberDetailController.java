package com.by.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.service.MemberDetailService;

@Controller
@RequestMapping(value = "/api/member")
public class MemberDetailController {
	@Autowired
	private MemberDetailService service;

	@RequestMapping(value = "/details", method = RequestMethod.PUT)
	@ResponseBody
	public Status update(MemberDetail detail, @PathVariable("id") Long id) {
		detail.setMember(new Member(id));
		return service.update(detail).map(i -> new Status("success")).orElseThrow(() -> new NotFoundException());
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	@ResponseBody
	public MemberDetail get(HttpServletRequest request) {
		Member m = (Member) request.getAttribute("member");
		Optional<MemberDetail> details = service.findByMember(m);
		return details.get();
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST)
	@ResponseBody
	public Status save(@Valid MemberDetail detail, BindingResult result) {
		if (result.hasErrors()) {
		}
		service.save(detail);
		return new Status("success");
	}
}
