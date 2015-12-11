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
import com.by.service.MemberService;
import com.by.service.ScoreHistoryService;
import com.by.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET)
    public String list(AdminMemberForm form, Model uiModel,
                       @PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberJson> results = memberService.findAll(form, pageable);
        uiModel.addAttribute("results", results);
        return "admin/member/members";
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
        Member member = memberService.addScore(new Member(json.getId()), json.getScore(), "admin");
        return new Success<>(member);
    }
}
