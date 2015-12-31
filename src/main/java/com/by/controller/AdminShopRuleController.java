package com.by.controller;

import com.by.form.BaseCouponForm;
import com.by.json.RuleJson;
import com.by.model.Menu;
import com.by.model.Shop;
import com.by.model.ShopRule;
import com.by.service.ShopRuleService;
import com.by.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin/shopRules")
public class AdminShopRuleController extends BaseController {
    private final int INIT_PAGE = 0;
    private final int PAGE_SIZE = 10;
    private final Menu subMenu = new Menu(5);
    private final String LISTS = "admin/shopRules/lists";
    private final String EDIT = "admin/shopRules/edit";
    private final String REDIRECT = "redirect:/admin/shopRules/";
    @Autowired
    private ShopRuleService service;
    @Autowired
    private ShopService shopService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(BaseCouponForm form, Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RuleJson> lists = service.findAll(form, pageable);
        uiModel.addAttribute("lists", lists);
        uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
        uiModel.addAttribute("form", form);
        return LISTS;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model uiModel) {
        ShopRule rule = service.findOne(id);
        List<Shop> shops = shopService.findAll(new Sort(Sort.Direction.ASC, "name"));
        uiModel.addAttribute("rule", rule);
        uiModel.addAttribute("shops", shops);
        addClass(rule, uiModel);
        return EDIT;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") int id, @ModelAttribute("rule") ShopRule rule, BindingResult result, Model uiModel) {
        rule.setId(id);
        if (result.hasErrors()) {
            uiModel.addAttribute("rule", rule);
            return EDIT;
        }
        ShopRule r = service.update(rule);
        return REDIRECT + r.getId();
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
