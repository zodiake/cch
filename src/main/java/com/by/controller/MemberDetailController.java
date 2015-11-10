package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/member")
public class MemberDetailController {
	@Autowired
	private MemberDetailService service;

	@RequestMapping(value = "/{id}/details", method = RequestMethod.PUT)
	@ResponseBody
	public Status update(MemberDetail detail,@PathVariable("id")Long id) {
		detail.setMember(new Member(id));
		return service.update(detail).map(i -> new Status("success")).orElseThrow(() -> new NotFoundException());
	}
}
