package com.by.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.AdminMemberForm;
import com.by.json.MemberJson;
import com.by.json.UpdateScoreJson;
import com.by.model.Card;
import com.by.model.Member;
import com.by.model.MemberStatics;
import com.by.model.Menu;
import com.by.model.User;
import com.by.security.UserContext;
import com.by.service.CardService;
import com.by.service.MemberService;
import com.by.service.MemberStaticsService;
import com.by.service.ParkingHistoryService;
import com.by.service.ScoreHistoryService;
import com.by.service.TradingService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/members")
public class AdminMemberController extends BaseController {
	private final String DETAIL = "admin/member/detail";
	private final Menu subMenu = new Menu(2);
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberStaticsService staticsService;
	@Autowired
	private UserContext userContext;
	@Autowired
	private CardService cardService;
	@Autowired
	private TradingService tradingService;
	@Autowired
	private ScoreHistoryService scoreHisotryService;
	@Autowired
	private ParkingHistoryService parkingHistoryService;

	@ModelAttribute("cards")
	public List<Card> findAllCard() {
		return cardService.findAllCache().stream().filter(i -> i.getValid().equals(ValidEnum.VALID))
				.collect(Collectors.toList());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String firstPage(AdminMemberForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MemberJson> members = memberService.findAll(form, pageable, ValidEnum.VALID);
		uiModel.addAttribute("lists", members);
		uiModel.addAttribute("last", computeLastPage(members.getTotalPages()));
		uiModel.addAttribute("form", form);
		addMenu(uiModel);
		return "admin/member/lists";
	}

	@RequestMapping(value = "/{card}", method = RequestMethod.POST)
	public String create(@PathVariable("card") int card, @Valid @ModelAttribute("member") Member member,
			BindingResult result) {
		//// TODO: 15-12-24
		return null;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(AdminMemberForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MemberJson> results = memberService.findAll(form, pageable, ValidEnum.VALID);
		uiModel.addAttribute("results", results);
		return new Success<>(results);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model uiModel) {
		Member member = memberService.findOne(id);
		if (member == null)
			throw new NotFoundException();
		uiModel.addAttribute("member", member);
		addMenu(uiModel);
		return DETAIL;
	}

	@RequestMapping(value = "/score", method = RequestMethod.PUT)
	@ResponseBody
	public Status updateScore(@RequestBody UpdateScoreJson json) {
		Member member = memberService.addScore(new Member(json.getId()), json.getScore(), "admin",
				ScoreHistoryEnum.ADMIN);
		return new Success<>(member);
	}

	@RequestMapping(value = "/{id}/validate", method = RequestMethod.PUT)
	@ResponseBody
	public Status validate(@PathVariable("id") Long id) {
		User user = userContext.getCurrentUser();
		Member member = memberService.validateOrInValidate(new Member(id), user.getName());
		return new Success<MemberJson>(new MemberJson(member));
	}

	@RequestMapping(value = "/{id}/use", method = RequestMethod.GET)
	@ResponseBody
	public Success<MemberStatics> consume(@PathVariable("id") Long id) {
		Member member = new Member(id);
		return new Success<>(staticsService.findOne(member));
	}

	@RequestMapping(value = "/{id}/trading", method = RequestMethod.GET)
	@ResponseBody
	public Status trading(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(tradingService.findByMember(new Member(id), pageable));
	}

	@RequestMapping(value = "/{id}/score", method = RequestMethod.GET)
	@ResponseBody
	public Status score(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(scoreHisotryService.findByMemberJson(new Member(id), pageable));
	}

	@RequestMapping(value = "/{id}/parking", method = RequestMethod.GET)
	@ResponseBody
	public Status parking(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(parkingHistoryService.findByMember(new Member(id), pageable));
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
