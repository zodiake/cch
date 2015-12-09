package com.by.controller;

import com.by.exception.NotFoundException;
import com.by.json.MemberJson;
import com.by.json.TradingJson;
import com.by.model.Member;
import com.by.service.MemberService;
import com.by.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel,
                       @PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberJson> results = memberService.findAll(pageable);
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
        model.addAttribute("member", member);
        model.addAttribute("trade", json);
        return "admin/member/detail";
    }
}
