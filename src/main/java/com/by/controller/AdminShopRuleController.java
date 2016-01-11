package com.by.controller;

import com.by.exception.Success;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/shopRules")
public class AdminShopRuleController extends BaseController {
    private final Menu subMenu = new Menu(5);
    private final String LISTS = "admin/shopRules/lists";
    private final String EDIT = "admin/shopRules/edit";
    private final String CREATE = "admin/shopRules/create";
    private final String REDIRECT = "redirect:/admin/shopRules/";
    @Autowired
    private ShopRuleService service;
    @Autowired
    private ShopService shopService;

    @ModelAttribute("shops")
    public List<Shop> shops() {
        List<Shop> shops = shopService.findAll(new Sort(Sort.Direction.ASC, "name"));
        return shops;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(BaseCouponForm form, Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RuleJson> lists = service.findAll(form, pageable);
        uiModel.addAttribute("lists", lists);
        uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
        uiModel.addAttribute("form", form);
        addMenu(uiModel);
        return LISTS;
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String form(Model uiModel) {
        uiModel.addAttribute("rule", new ShopRule());
        addMenu(uiModel);
        return CREATE;
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("rule") ShopRule rule, BindingResult result, Model uiModel) {
        if (result.hasErrors()) {
            uiModel.addAttribute("rule", rule);
            addMenu(uiModel);
            return CREATE;
        }
        rule.setCreatedBy(userContext.getCurrentUser().getName());
        ShopRule r = service.save(rule);
        return REDIRECT + r.getId() + "?edit";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model uiModel) {
        ShopRule rule = service.findOne(id);
        uiModel.addAttribute("rule", rule);
        addClass(rule, uiModel);
        addMenu(uiModel);
        return EDIT;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") int id, @Valid @ModelAttribute("rule") ShopRule rule, BindingResult result,
                         Model uiModel) {
        rule.setId(id);
        if (result.hasErrors()) {
            uiModel.addAttribute("rule", rule);
            addMenu(uiModel);
            return EDIT;
        }
        rule.setUpdatedBy(userContext.getCurrentUser().getName());
        ShopRule r = service.update(rule);
        return REDIRECT + r.getId() + "?edit";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Success<Page<RuleJson>> list(BaseCouponForm form,
                                        @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RuleJson> lists = service.findAll(form, pageable);
        return new Success<>(lists);
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
