package com.by.service.impl;

import com.by.form.BaseCouponForm;
import com.by.json.RuleJson;
import com.by.model.GiftCoupon;
import com.by.model.ShopRule;
import com.by.repository.ShopRuleRepository;
import com.by.service.RuleService;
import com.by.service.ShopRuleService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by yagamai on 15-12-22.
 */
@Service
@Transactional
public class ShopRuleServiceImpl implements ShopRuleService {
    @Autowired
    private EntityManager em;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private ShopRuleRepository repository;

    @Override
    public Page<RuleJson> findAll(BaseCouponForm form, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ShopRule> c = cb.createQuery(ShopRule.class);
        Root<ShopRule> root = c.from(ShopRule.class);
        c.select(root);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(GiftCoupon.class)));
        List<Predicate> ls = ruleService.getPredicateList(form, root, cb);
        Predicate[] predicates = ls.toArray(new Predicate[0]);
        c.where(predicates);
        cq.where(predicates);

        List<ShopRule> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery(cq).getSingleResult();
        List<RuleJson> results = lists.stream().map(i -> new RuleJson(i)).collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }

    @Override
    @Transactional(readOnly = true)
    public ShopRule findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    public ShopRule update(ShopRule r) {
        ShopRule rule = repository.findOne(r.getId());
        rule.setName(r.getName());
        rule.setBeginTime(r.getBeginTime());
        rule.setShops(r.getShops());
        return null;
    }
}
