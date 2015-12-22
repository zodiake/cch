package com.by.service.impl;

import com.by.exception.NotValidException;
import com.by.form.CouponQueryForm;
import com.by.json.RuleJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.GiftCoupon;
import com.by.model.RuleCategory;
import com.by.repository.CardRuleRepository;
import com.by.service.CardRuleService;
import com.by.service.RuleService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-15.
 */
@Service
@Transactional
public class CardRuleServiceImpl implements CardRuleService {
    @Autowired
    private CardRuleRepository repository;
    @Autowired
    private EntityManager em;
    @Autowired
    private RuleService ruleService;

    @Override
    @Cacheable(value = "cardRule", key = "#category.id")
    public List<CardRule> findByRuleCategoryAndCardAndValid(RuleCategory category, Card card, ValidEnum valid) {
        return repository.findByRuleCategoryAndCardAndValid(category, card, ValidEnum.VALID);
    }

    @Override
    public List<CardRule> findByRuleCategoryAndCard(RuleCategory category, Card card) {
        return repository.findByRuleCategoryAndCard(category, card);
    }

    @Override
    public CardRule save(CardRule rule) {
        return repository.save(rule);
    }

    @Override
    public CardRule findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    @CachePut(value = "rule", key = "#cardRule.id")
    public CardRule update(CardRule cardRule) {
        CardRule rule = repository.findOne(cardRule.getId());
        if (!ruleService.withinValidDate(rule)) {
            throw new NotValidException();
        }
        rule.setCard(cardRule.getCard());
        rule.setName(cardRule.getName());
        rule.setRuleCategory(cardRule.getRuleCategory());
        rule.setBeginTime(cardRule.getBeginTime());
        rule.setEndTime(cardRule.getEndTime());
        return rule;
    }

    @Override
    public Page<RuleJson> findAll(CouponQueryForm form, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CardRule> c = cb.createQuery(CardRule.class);
        Root<CardRule> root = c.from(CardRule.class);
        c.select(root);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(GiftCoupon.class)));
        if (form != null) {
            Predicate[] predicates = ruleService.getPredicateList(form, root, cb);
            c.where(predicates);
            cq.where(predicates);
        }

        List<CardRule> lists = em.createQuery(c)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery(cq).getSingleResult();
        List<RuleJson> results = lists.stream().map(RuleJson::new)
                .collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }

}
