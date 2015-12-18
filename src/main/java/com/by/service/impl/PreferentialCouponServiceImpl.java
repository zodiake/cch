package com.by.service.impl;

import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.PreferentialCoupon;
import com.by.repository.PreferentialCouponRepository;
import com.by.service.CouponService;
import com.by.service.PreferentialCouponService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by yagamai on 15-12-4.
 */
@Service
@Transactional
public class PreferentialCouponServiceImpl implements PreferentialCouponService {
    @Autowired
    private PreferentialCouponRepository repository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CouponService couponService;

    @Override
    public PreferentialCoupon save(PreferentialCoupon coupon) {
        return repository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public PreferentialCoupon findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("coupon")
    public PreferentialCoupon findOneCache(Long id) {
        return null;
    }

    @Override
    public PreferentialCoupon findByIdAndValid(Long id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("preferentialCoupon")
    public Page<PreferentialCoupon> findByValid(ValidEnum valid, Pageable pageable) {
        return repository.findByValid(valid, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponTemplateJson> findAll(CouponQueryForm form, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PreferentialCoupon> c = cb.createQuery(PreferentialCoupon.class);
        Root<PreferentialCoupon> root = c.from(PreferentialCoupon.class);
        c.select(root);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(PreferentialCoupon.class)));
        Predicate[] predicates = couponService.getPredicateList(form, root, cb);
        c.where(predicates);
        cq.where(predicates);

        List<PreferentialCoupon> lists = em.createQuery(c)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery(cq).getSingleResult();
        List<CouponTemplateJson> results = lists.stream().map(i -> new CouponTemplateJson(i))
                .collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }
}
