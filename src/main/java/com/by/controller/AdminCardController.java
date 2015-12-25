package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CardJson;
import com.by.json.CardTemplateJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.Menu;
import com.by.model.RuleCategory;
import com.by.security.UserContext;
import com.by.service.CardRuleService;
import com.by.service.CardService;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;
import com.by.validator.CardNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/cards")
public class AdminCardController extends BaseController {
	private final String CREATE = "admin/card/create";
	private final String REDIRECT = "redirect:/admin/cards/";
	private final String DETAIL = "admin/card/detail";
	private final RuleCategory SIGNUP_CATEGORY = new RuleCategory(1);
	private final RuleCategory TRADING_CATEGORY = new RuleCategory(2);
	private final String LISTS = "admin/card/lists";
	private final Menu subMenu = new Menu(1);
	@Autowired
	private CardService service;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CardRuleService cardRuleService;
	@Autowired
	private UserContext userContext;
	@Autowired
	@Qualifier("cardNameValidator")
	private CardNameValidator validator;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		List<Card> validLists = service.findByValid(ValidEnum.VALID, pageable);
		List<Card> inValidLists = service.findByValid(ValidEnum.INVALID, pageable);
		uiModel.addAttribute("valid", validLists);
		uiModel.addAttribute("inValid", inValidLists);
		uiModel.addAttribute("menus", menus(userContext.getCurrentUser()));
		uiModel.addAttribute("subMenu", subMenu);
		return LISTS;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public List<CardJson> list(@RequestParam("valid") String valid,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		ValidEnum validEnum = ValidEnum.fromString(valid);
		List<CardJson> cards = service.findByValid(validEnum, pageable).stream().map(i -> {
			return new CardJson(i);
		}).collect(Collectors.toList());
		return cards;
	}

	@RequestMapping(value = "/{id}/json", method = RequestMethod.GET)
	@ResponseBody
	public Status get(@PathVariable("id") Integer id, Model uiModel) {
		Card card = service.findOne(id);
		Long count = memberService.countByCard(new Card(id));
		List<CardRule> signUpRules = cardRuleService.findByRuleCategoryAndCard(SIGNUP_CATEGORY, card);
		List<CardRule> tradingRules = cardRuleService.findByRuleCategoryAndCard(TRADING_CATEGORY, card);
		return new Success<>(new CardTemplateJson(card, count.intValue(), signUpRules, tradingRules));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getOne(@PathVariable("id") Integer id, Model uiModel) {
		Card card = service.findOne(id);
		uiModel.addAttribute("card", card);
		uiModel.addAttribute("menus", menus(userContext.getCurrentUser()));
		uiModel.addAttribute("subMenu", subMenu);
		return DETAIL;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute Card card, BindingResult result) {
		Card source = service.update(card);
		return REDIRECT + source.getId();
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute Card card, BindingResult result, Model uiModel) {
		validator.validate(card, result);
		if (result.hasErrors()) {
			uiModel.addAttribute("card", card);
			return CREATE;
		}
		Card source = service.save(card);
		return REDIRECT + source.getId();
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		uiModel.addAttribute("card", new Card());
		uiModel.addAttribute("menus", menus(userContext.getCurrentUser()));
		uiModel.addAttribute("subMenu", subMenu);
		return CREATE;
	}
}
