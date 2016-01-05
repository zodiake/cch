package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.form.AdminMemberForm;
import com.by.json.MemberJson;
import com.by.model.Menu;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 16-1-5.
 */
@Controller
@RequestMapping("/admin/blackList")
public class AdminBlackListController extends BaseController {
	private final String LIST="admin/blacklist/lists";
	private final Menu subMenu = new Menu(3);
	@Autowired
	private MemberService service;

	@RequestMapping(method = RequestMethod.GET)
	public String list(AdminMemberForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MemberJson> list=service.findAll(form, pageable, ValidEnum.INVALID);
		uiModel.addAttribute("form",form);
		uiModel.addAttribute("lists",list);
		uiModel.addAttribute("last",computeLastPage(list.getTotalPages()));
		addMenu(uiModel);
		return LIST;
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
