package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.service.MemberHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yagamai on 15-12-18.
 */
@Controller
@RequestMapping("/api/help")
public class MemberHelpController {
    @Autowired
    private MemberHelperService service;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Status list() {
        return new Success<>(service.findAllJson());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Status detail(@PathVariable("id") int id) {
        return new Success<>(service.findOneJson(id));
    }
}
