package com.by.controller;

import com.by.json.RuleJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.service.CardRuleService;
import com.by.service.CardService;
import com.by.typeEnum.ValidEnum;
import com.by.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-21.
 */
@Controller
@RequestMapping("/admin/cardRules")
public class AdminCardRuleController {
    private final int PAGE_SIZE = 10;
    @Autowired
    private CardRuleService service;
    @Autowired
    private CardService cardService;

    @ModelAttribute("cards")
    public List<Card> findAllCard() {
        return cardService.findAll().stream().filter(i -> i.getValid().equals(ValidEnum.VALID))
                .collect(Collectors.toList());
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String create(Model uiModel) {
        CardRule cardRule = new CardRule();
        uiModel.addAttribute("rule", cardRule);
        return "admin/cardRule/create";
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String form(@Valid @ModelAttribute("rule") CardRule rule, BindingResult result, Model uiModel) {
        if (result.hasErrors()) {
            uiModel.addAttribute("rule", rule);
            return "admin/cardRule/create";
        }
        CardRule r = service.save(rule);
        return "redirect:/admin/cardRules/" + r.getId();
    }

    @RequestMapping(params = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model uiModel) {
        CardRule rule = service.findOne(id);
        uiModel.addAttribute("rule", rule);
        return "admin/cardRule/detail";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        Page<RuleJson> lists = service.findAll(null, new PageRequest(0, PAGE_SIZE, Sort.Direction.DESC, "beginTime"));
        int pages = PageUtils.computeLastPage(lists.getTotalPages());
        uiModel.addAttribute("lists",lists);
        uiModel.addAttribute("pages",pages);
        return "admin/carRule/lists";
    }

}
