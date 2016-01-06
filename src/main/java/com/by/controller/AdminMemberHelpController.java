package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.MemberHelp;
import com.by.model.Menu;
import com.by.service.MemberHelperService;

@Controller
@RequestMapping(value = "/admin/memberHelps")
public class AdminMemberHelpController extends BaseController{
	private final Menu subMenu = new Menu(11);
	private final String DETAIL = "admin/help/detail";
	private final String REDIRECTDETAIL = "redirect:/admin/memberHelps/";
	@Autowired
	private MemberHelperService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id")int id, Model uiModel){
		MemberHelp help = service.findOne(id);
		addMenu(uiModel);
		uiModel.addAttribute("help", help);
		return DETAIL;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id")int id,@ModelAttribute("help")MemberHelp help){
		help.setId(id);
		service.update(help);
		return REDIRECTDETAIL + id;
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
