package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Fail;
import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
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
	public Status update(MemberDetail detail, @PathVariable("id") Long id) {
		detail.setMember(new Member(id));
		return service.update(detail).map(i -> new Status("success")).orElseThrow(() -> new NotFoundException());
	}

	@RequestMapping(value = "/{id}/details", method = RequestMethod.GET)
	@ResponseBody
	public Success<MemberDetail> get(@PathVariable("id") Long id) {
		Member m = new Member(id);
		return service.findByMember(m).map(i -> new Success<MemberDetail>(i))
				.orElseThrow(() -> new NotFoundException());
	}

	@RequestMapping(value = "/{id}/details", method = RequestMethod.POST)
	@ResponseBody
	public Status save(@Valid MemberDetail detail, BindingResult result) {
		if (result.hasErrors()) {
			return new Fail(result.getAllErrors());
		}
		service.save(detail);
		return new Status("success");
	}
}
