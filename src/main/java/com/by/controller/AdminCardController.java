package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CardJson;
import com.by.json.CardTemplateJson;
import com.by.json.RuleJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.Menu;
import com.by.model.RuleCategory;
import com.by.service.CardRuleService;
import com.by.service.CardService;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/cards")
public class AdminCardController extends BaseController {
    private final String CREATE = "admin/card/create";
    private final String REDIRECT = "redirect:/admin/cards/";
    private final String EDIT = "admin/card/edit";
    private final String LISTS = "admin/card/lists";
    private final RuleCategory SIGNUP_CATEGORY = new RuleCategory(1);
    private final RuleCategory TRADING_CATEGORY = new RuleCategory(2);
    private final Menu subMenu = new Menu(1);
    @Autowired
    private CardService service;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CardRuleService cardRuleService;
    @Autowired
    @Qualifier("cardNameValidator")
    private Validator cardNameValidator;
    @Autowired
    @Qualifier("cardNameUniqueValidator")
    private Validator cardNameUniqueValidator;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel,
                       @PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Card> validLists = service.findByValid(ValidEnum.VALID, pageable);
        Page<Card> inValidLists = service.findByValid(ValidEnum.INVALID, pageable);
        uiModel.addAttribute("valid", validLists);
        uiModel.addAttribute("inValid", inValidLists);
        addMenu(uiModel);
        return LISTS;
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public List<CardJson> list(@RequestParam("valid") String valid,
                               @PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        ValidEnum validEnum = ValidEnum.fromString(valid);
        List<CardJson> cards = service.findByValid(validEnum, pageable).getContent().stream().map(CardJson::new)
                .collect(Collectors.toList());
        return cards;
    }

    @RequestMapping(value = "/{id}/json", method = RequestMethod.GET)
    @ResponseBody
    public Status get(@PathVariable("id") Integer id) {
        Card card = service.findOne(id);
        Long count = memberService.countByCard(new Card(id));
        List<CardRule> signUpRules = cardRuleService.findByRuleCategoryAndCard(SIGNUP_CATEGORY, card);
        List<CardRule> tradingRules = cardRuleService.findByRuleCategoryAndCard(TRADING_CATEGORY, card);
        CardTemplateJson json = new CardTemplateJson(card, count.intValue());
        json.setRegister(signUpRules.stream().map(r -> new RuleJson(r)).collect(Collectors.toList()));
        json.setTrading(tradingRules.stream().map(r -> new RuleJson(r)).collect(Collectors.toList()));
        return new Success<>(json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable("id") Integer id, Model uiModel) {
        Card card = service.findOne(id);
        uiModel.addAttribute("card", card);
        addMenu(uiModel);
        return EDIT;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") Integer id, Model uiModel, @Valid @ModelAttribute Card card,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        card.setId(id);
        cardNameValidator.validate(card, result);
        if (result.hasErrors()) {
            uiModel.addAttribute("card", card);
            uiModel.addAttribute("message", failMessage(messageSource));
            addMenu(uiModel);
            return EDIT;
        }
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        card.setUpdatedBy(userContext.getCurrentUser().getUpdatedBy());
        Card source = service.update(card);
        return REDIRECT + source.getId();
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("card") Card card, BindingResult result, Model uiModel,
                       RedirectAttributes redirectAttributes) {
        cardNameUniqueValidator.validate(card, result);
        if (result.hasErrors()) {
            uiModel.addAttribute("card", card);
            uiModel.addAttribute("message", failMessage(messageSource));
            addMenu(uiModel);
            return CREATE;
        }
        card.setCreatedBy(userContext.getCurrentUser().getCreatedBy());
        Card source = service.save(card);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return REDIRECT + source.getId();
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String save(Model uiModel) {
        uiModel.addAttribute("card", new Card());
        addMenu(uiModel);
        return CREATE;
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
