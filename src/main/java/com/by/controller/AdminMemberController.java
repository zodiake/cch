package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.by.json.ScoreHistoryJson;
import com.by.json.TradingJson;
import com.by.json.UpdateScoreJson;
import com.by.model.Member;
import com.by.model.User;
import com.by.security.UserContext;
import com.by.service.MemberService;
import com.by.service.ScoreHistoryService;
import com.by.service.TradingService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.utils.PageUtils;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private TradingService tradingService;
	@Autowired
	private ScoreHistoryService scoreHistoryService;
	@Autowired
	private UserContext userContext;

	@RequestMapping(method = RequestMethod.GET)
	public String firstPage(Model uiModel) {
		Page<Member> members = memberService.findFirstPage(10);
		uiModel.addAttribute("lists", members);
		uiModel.addAttribute("last", PageUtils.computeLastPage(members.getTotalPages()));
		return "admin/member/lists";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(AdminMemberForm form, Model uiModel,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MemberJson> results = memberService.findAll(form, pageable);
		uiModel.addAttribute("results", results);
		return new Success<Page<MemberJson>>(results);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model model) {
		Member member = memberService.findOne(id);
		if (member == null)
			throw new NotFoundException();
		Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC, "createdTime");
		Page<TradingJson> json = tradingService.findByMember(member, pageable);
		Page<ScoreHistoryJson> histories = scoreHistoryService.findByMemberJson(member, pageable);
		model.addAttribute("member", member);
		model.addAttribute("trade", json);
		model.addAttribute("histories", histories);
		return "admin/member/detail";
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
}
