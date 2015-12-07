package com.by.service.impl;

import com.by.exception.MemberNotFoundException;
import com.by.exception.TradingAlreadyBindException;
import com.by.exception.TradingNotExistException;
import com.by.model.*;
import com.by.repository.TradingRepository;
import com.by.service.MemberService;
import com.by.service.RuleService;
import com.by.service.TradingService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Created by yagamai on 15-11-27.
 */
@Service
@Transactional
public class TradingServiceImpl implements TradingService {
    private final String reason = "";
    @Autowired
    private TradingRepository repository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private RuleService ruleService;
    private RuleCategory tradingRuleCategory = new RuleCategory(2l);

    @Override
    @Transactional(readOnly = true)
    public Page<Trading> findByShopAndCreatedTimeBetween(Shop shop, Calendar startTime, Calendar endTime, Pageable pageable) {
        return repository.findByShopAndCreatedTimeBetween(shop, startTime, endTime, pageable);
    }

    @Override
    public Trading save(Trading trading) {
        if (trading.getMember() != null) {
            Optional<Member> memberOptional = memberService.findByName(trading.getMember().getName());
            if (!memberOptional.isPresent())
                throw new MemberNotFoundException();
            Member member = memberOptional.get();
            memberService.addScore(member, tradeToScore(trading), reason);
        }
        return repository.save(trading);
    }

    @Transactional(readOnly = true)
    public int tradeToScore(Trading trading) {
        Optional<Member> m = memberService.findById(trading.getMember().getId());
        if (!m.isPresent())
            throw new MemberNotFoundException();
        List<Rule> rules = ruleService.findByRuleCategoryAndCardAndValid(tradingRuleCategory, m.get().getCard(), ValidEnum.VALID);
        double maxRate = ruleService.getMaxRate(rules);
        double maxScore = ruleService.getMaxScore(rules);
        return Long.valueOf(Math.round(trading.getAmount() * maxRate + maxScore)).intValue();
    }

    @Override
    public Trading bindMember(Trading trading, String mobile) {
        Optional<Member> member = memberService.findByName(mobile);
        if (!member.isPresent())
            throw new MemberNotFoundException();
        Trading t = repository.findByCode(trading.getCode());
        if (t == null)
            throw new TradingNotExistException();
        if (t.getMember() != null)
            throw new TradingAlreadyBindException();
        t.setMember(member.get());
        return save(t);
    }
}
