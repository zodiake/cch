package com.by.controller;

import com.by.json.RuleJson;
import com.by.service.ShopRuleService;
import com.by.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/shopRules")
public class AdminShopRuleController {
    @Autowired
    private ShopRuleService service;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        Page<RuleJson> lists = service.findAll(null, new PageRequest(0, 10, Sort.Direction.DESC, "beginTime"));
        uiModel.addAttribute("lists", lists);
        uiModel.addAttribute("pages", PageUtils.computeLastPage(lists.getTotalPages()));
        return "admin/shopRule/lists";
    }
}
