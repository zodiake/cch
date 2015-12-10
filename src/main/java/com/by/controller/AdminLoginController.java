package com.by.controller;

import com.by.model.User;
import com.by.security.UserContext;
import com.by.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminLoginController {
    @Autowired
    private UserContext userContext;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
    public String login(Model uiModel) {
        uiModel.addAttribute("user", new User());
        return "admin/login";
    }
}
