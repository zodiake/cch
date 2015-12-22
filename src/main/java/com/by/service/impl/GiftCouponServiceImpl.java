package com.by.service.impl;

import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.GiftCoupon;
import com.by.repository.GiftCouponRepository;
import com.by.service.CouponService;
import com.by.service.GiftCouponService;
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
public class GiftCouponServiceImpl implements GiftCouponService {
    @Autowired
    private GiftCouponRepository repository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CouponService couponService;

    @Override
    public GiftCoupon save(GiftCoupon coupon) {
        return repository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCoupon findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("coupon")
    public GiftCoupon findOneCache(Long id) {
        return null;
    }

    @Override
    public GiftCoupon findByIdAndValid(Long id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GiftCoupon> findByValid(ValidEnum valid, Pageable pageable) {
        return repository.findByValid(valid, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponTemplateJson> findAll(CouponQueryForm form, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GiftCoupon> c = cb.createQuery(GiftCoupon.class);
        Root<GiftCoupon> root = c.from(GiftCoupon.class);
        c.select(root);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(GiftCoupon.class)));
        if (form != null) {
            Predicate[] predicates = couponService.getPredicateList(form, root, cb);
            c.where(predicates);
            cq.where(predicates);
        }

        List<GiftCoupon> lists = em.createQuery(c)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery(cq).getSingleResult();
        List<CouponTemplateJson> results = lists.stream().map(CouponTemplateJson::new)
                .collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }
}
