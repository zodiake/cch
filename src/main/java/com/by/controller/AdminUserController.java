package com.by.controller;

import com.by.model.User;
import com.by.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yagamai on 15-12-11.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class AdminUserController {
    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        List<User> users = service.findFirstPage(10);
        uiModel.addAttribute("uses", users);
        return "admin/user/list";
    }
}
