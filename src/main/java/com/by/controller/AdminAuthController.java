package com.by.controller;

import com.by.model.Authority;
import com.by.model.Menu;
import com.by.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yagamai on 16-1-4.
 */
@Controller
@RequestMapping(value = "/admin/authority")
public class AdminAuthController extends BaseController {
    private final Menu subMenu = new Menu(14);
    private final String LIST = "admin/authority/lists";

    @Autowired
    private AuthorityService service;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Authority> lists = service.findAll(pageable);
        uiModel.addAttribute("lists", lists);
        uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
        addMenu(uiModel);
        return LIST;
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
