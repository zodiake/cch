package com.by.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Fail;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.model.Member;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<? extends Status> save(@Valid Member member, BindingResult result) {
		if (result.hasErrors()) {
			Fail fail=new Fail(result.getAllErrors());
			return new ResponseEntity<Fail>(fail,HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<Success>(new Success(),HttpStatus.OK);
	}
}
