package com.by.controller;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.AdminMemberForm;
import com.by.json.MemberJson;
import com.by.json.ScoreHistoryJson;
import com.by.json.TradingJson;
import com.by.json.UpdateScoreJson;
import com.by.model.Member;
import com.by.model.Menu;
import com.by.model.User;
import com.by.security.UserContext;
import com.by.service.MemberService;
import com.by.service.ScoreHistoryService;
import com.by.service.TradingService;
import com.by.typeEnum.ScoreHistoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/members")
public class AdminMemberController extends BaseController {
    private final int PAGE_SIZE = 10;
    private final int INIT_PAGE = 0;
    private final Menu subMenu = new Menu(2);
    @Autowired
    private MemberService memberService;
    @Autowired
    private TradingService tradingService;
    @Autowired
    private ScoreHistoryService scoreHistoryService;
    @Autowired
    private UserContext userContext;

    @RequestMapping(method = RequestMethod.GET)
    public String firstPage(AdminMemberForm form, Model uiModel,
                            @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberJson> members = memberService.findAll(form, pageable);
        uiModel.addAttribute("lists", members);
        uiModel.addAttribute("last", 7);
        uiModel.addAttribute("menus", menus(userContext.getCurrentUser()));
        uiModel.addAttribute("subMenu", subMenu);
        return "admin/member/lists";
    }

    @RequestMapping(value = "/{card}", method = RequestMethod.POST)
    public String create(@PathVariable("card") int card, @Valid @ModelAttribute("member") Member member, BindingResult result) {
        //// TODO: 15-12-24  
        return null;
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list(AdminMemberForm form, Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberJson> results = memberService.findAll(form, pageable);
        uiModel.addAttribute("results", results);
        return new Success<>(results);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);
        if (member == null)
            throw new NotFoundException();
        Pageable pageable = new PageRequest(INIT_PAGE, PAGE_SIZE, Sort.Direction.DESC, "createdTime");
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
