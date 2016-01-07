package com.by.controller;

import com.by.model.MemberHelp;
import com.by.model.Menu;
import com.by.service.MemberHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/memberHelps")
public class AdminMemberHelpController extends BaseController {
    private final Menu subMenu = new Menu(11);
    private final String EDIT = "admin/help/edit";
    private final String REDIRECT = "redirect:/admin/memberHelps/";
    @Autowired
    private MemberHelperService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, Model uiModel) {
        MemberHelp help = service.findOne(id);
        addMenu(uiModel);
        uiModel.addAttribute("help", help);
        return EDIT;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") int id, @ModelAttribute("help") MemberHelp help) {
        help.setId(id);
        service.update(help);
        return REDIRECT + id;
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
