package com.by.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CardJson;
import com.by.json.CardTemplateJson;
import com.by.message.SuccessMessage;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.Menu;
import com.by.model.RuleCategory;
import com.by.service.CardRuleService;
import com.by.service.CardService;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;

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
	@Qualifier("cardNameValidator")
	private Validator cardNameValidator;
	@Autowired
	@Qualifier("cardNameUniqueValidator")
	private Validator cardNameUniqueValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		List<Card> validLists = service.findByValid(ValidEnum.VALID, pageable);
		List<Card> inValidLists = service.findByValid(ValidEnum.INVALID, pageable);
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
		List<CardJson> cards = service.findByValid(validEnum, pageable).stream().map(CardJson::new)
				.collect(Collectors.toList());
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
		addMenu(uiModel);
		return DETAIL;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") Integer id, Model uiModel, @Valid @ModelAttribute Card card,
			BindingResult result, RedirectAttributes redirectAttributes) {
		card.setId(id);
		cardNameValidator.validate(card, result);
		if (result.hasErrors()) {
			uiModel.addAttribute("card", card);
			addMenu(uiModel);
			return DETAIL;
		}
		redirectAttributes.addFlashAttribute("status", new SuccessMessage(SUCCESS));
		Card source = service.update(card);
		return REDIRECT + source.getId();
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("card") Card card, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		cardNameUniqueValidator.validate(card, result);
		if (result.hasErrors()) {
			uiModel.addAttribute("card", card);
			addMenu(uiModel);
			return CREATE;
		}
		Card source = service.save(card);
		redirectAttributes.addFlashAttribute("status", new SuccessMessage(SUCCESS));
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
