package com.by.service.impl;

import com.by.exception.MemberNotFoundException;
import com.by.exception.NotFoundException;
import com.by.exception.TradingAlreadyBindException;
import com.by.exception.TradingNotExistException;
import com.by.json.TradingJson;
import com.by.json.TradingRequestJson;
import com.by.model.*;
import com.by.repository.TradingRepository;
import com.by.service.*;
import com.by.typeEnum.ScoreHistoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private ShopService shopService;
    @Autowired
    private CardRuleService cardRuleService;
    private RuleCategory tradingRuleCategory = new RuleCategory(2l);

    @Override
    @Transactional(readOnly = true)
    public Page<Trading> findByShopAndCreatedTimeBetween(Shop shop, Calendar startTime, Calendar endTime,
                                                         Pageable pageable) {
        return repository.findByShopAndCreatedTimeBetween(shop, startTime, endTime, pageable);
    }

    @Override
    public Trading save(Trading trading) {
        if (trading.getMember() != null) {
            Optional<Member> memberOptional = memberService.findByName(trading.getMember().getName());
            if (!memberOptional.isPresent())
                throw new MemberNotFoundException();
            Member member = memberOptional.get();
            memberService.addScore(member, tradeToScore(trading), reason, ScoreHistoryEnum.TRADE);
        }
        return repository.save(trading);
    }

    @Override
    public Trading save(TradingRequestJson trading) {
        Optional<Member> member = memberService.findByName(trading.getMobile());
        if (!member.isPresent())
            throw new MemberNotFoundException();
        Shop shop = shopService.findByKey(trading.getShopKey());
        if (shop == null)
            throw new NotFoundException();
        Trading t = new Trading();
        t.setAmount(trading.getAmount());
        t.setMember(member.get());
        t.setShop(shop);
        t.setCreatedTime(trading.getCreatedTime());
        return repository.save(t);
    }

    @Override
    @Transactional(readOnly = true)
    public int tradeToScore(Trading trading) {
        Optional<Member> m = memberService.findById(trading.getMember().getId());
        if (!m.isPresent())
            throw new MemberNotFoundException();
        List<CardRule> rules = cardRuleService.findByCategory(tradingRuleCategory)
                .stream()
                .filter(i -> ruleService.withValidDate(i))
                .collect(Collectors.toList());
        List<ShopRule> shopRules = trading.getShop().getRules()
                .stream()
                .filter(i -> ruleService.withValidDate(i))
                .collect(Collectors.toList());
        double maxRate = Math.max(ruleService.getMaxRate(rules), ruleService.getMaxRate(shopRules));
        double maxScore = Math.max(ruleService.getMaxRate(rules), ruleService.getMaxScore(shopRules));
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

    @Override
    @Transactional(readOnly = true)
    public Page<TradingJson> findByMember(Member member, Pageable pageable) {
        Page<Trading> results = repository.findByMember(member, pageable);
        List<TradingJson> json = results.getContent().stream().map(i -> new TradingJson(i))
                .collect(Collectors.toList());
        return new PageImpl<>(json, pageable, results.getTotalElements());
    }
}
