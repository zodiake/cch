package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.ScoreJson;
import com.by.model.Member;
import com.by.service.MemberService;
import com.by.service.ScoreAddHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yagamai on 15-12-7.
 */
@Controller
@RequestMapping("/api/score")
public class ScoreController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ScoreAddHistoryService scoreAddHistoryService;

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    @ResponseBody
    public Status scoreAvailable(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        Member source = memberService.findOne(member.getId());
        return new Success<>(source.getScore());
    }

    @RequestMapping(value = "/sum", method = RequestMethod.GET)
    @ResponseBody
    public Status scoreSum(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        Long total = scoreAddHistoryService.sumByMember(member);
        return new Success<>(total);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Status scoreSumAndAvailable(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        int available = memberService.findOne(member.getId()).getScore();
        Long total = scoreAddHistoryService.sumByMember(member);
        return new Success<>(new ScoreJson(available, total));
    }
}
