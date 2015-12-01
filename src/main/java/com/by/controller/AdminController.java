package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.User;
import com.by.security.UserContext;
import com.by.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserContext userContext;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String adminIndex(Model uiModel) {
        User user = userContext.getCurrentUser();
        uiModel.addAttribute("menus", userService.getMenus(user));
        return "admin/index";
    }
}
