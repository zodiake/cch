package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.CouponQueryForm;
import com.by.json.RuleJson;
import com.by.model.*;
import com.by.service.CardRuleService;
import com.by.service.CardService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * Created by yagamai on 15-12-21.
 */
@Controller
@RequestMapping("/admin/cardRules")
public class AdminCardRuleController extends BaseController {
    private final Menu subMenu = new Menu(4);
    private final String CREATE = "admin/cardRules/create";
    private final String EDIT = "admin/cardRules/edit";
    private final String REDIRECT = "redirect:/admin/cardRules/";
    private final String LIST = "admin/cardRules/lists";
    @Autowired
    private CardRuleService service;
    @Autowired
    private CardService cardService;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("cards")
    public List<Card> findAllCard() {
        return cardService.findAll().stream().filter(i -> i.getValid().equals(ValidEnum.VALID))
                .collect(Collectors.toList());
    }

    @ModelAttribute("category")
    public List<RuleCategory> allCategory() {
        List<RuleCategory> results = new ArrayList<>();
        RuleCategory register = new RuleCategory(1);
        register.setName("注册");
        RuleCategory trading = new RuleCategory(2);
        trading.setName("交易");
        results.add(register);
        results.add(trading);
        return results;
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String create(Model uiModel) {
        CardRule cardRule = new CardRule();
        uiModel.addAttribute("rule", cardRule);
        addMenu(uiModel);
        return CREATE;
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String form(@Valid @ModelAttribute("rule") CardRule rule, BindingResult result, Model uiModel, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            uiModel.addAttribute("rule", rule);
            addMenu(uiModel);
            uiModel.addAttribute("message", new Message("fail", messageSource.getMessage("save.fail", new Object[]{}, Locale.CHINESE)));
            return CREATE;
        }
        rule.setCreatedBy(userContext.getCurrentUser().getName());
        CardRule r = service.save(rule);
        redirectAttributes.addFlashAttribute("message", new Message("fail", messageSource.getMessage("save.fail", new Object[]{}, Locale.CHINESE)));
        return REDIRECT + r.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model uiModel) {
        CardRule rule = service.findOne(id);
        uiModel.addAttribute("rule", rule);
        addClass(rule, uiModel);
        addMenu(uiModel);
        return EDIT;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") int id, @Valid @ModelAttribute("rule") CardRule rule,
                         BindingResult result, Model uiModel) {
        rule.setId(id);
        if (result.hasErrors()) {
            uiModel.addAttribute("rule", rule);
            return EDIT;
        }
        rule.setUpdatedBy(userContext.getCurrentUser().getName());
        CardRule r = service.update(rule);
        return REDIRECT + r.getId();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(CouponQueryForm form, Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RuleJson> lists = service.findAll(form, pageable);
        int pages = computeLastPage(lists.getTotalPages());
        uiModel.addAttribute("lists", lists);
        uiModel.addAttribute("last", pages);
        uiModel.addAttribute("form", form);
        addMenu(uiModel);
        return LIST;
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Success<Page<RuleJson>> list(CouponQueryForm form,
                                        @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RuleJson> lists = service.findAll(form, pageable);
        return new Success<>(lists);
    }

    @RequestMapping(value = "/{id}/valid", method = RequestMethod.PUT)
    @ResponseBody
    public Status valid(@PathVariable("id") int id) {
        return service.valid(id);
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
