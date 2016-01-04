package com.by.service.impl;

import com.by.form.BaseCouponForm;
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
import java.util.Calendar;
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
    public GiftCoupon findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("coupon")
    public GiftCoupon findOneCache(int id) {
        return repository.findOne(id);
    }

    @Override
    public GiftCoupon findByIdAndValid(int id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GiftCoupon> findByValid(ValidEnum valid, Pageable pageable) {
        return repository.findByValid(valid, pageable);
    }

    @Transactional(readOnly = true)
    public Page<GiftCoupon> findAllByValidAndDateBetween(ValidEnum VALID, Calendar calendar, Pageable pageable) {
        return repository.findAllByValidAndDateBetween(ValidEnum.VALID, Calendar.getInstance(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponTemplateJson> findAll(BaseCouponForm form, Pageable pageable) {
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
        List<CouponTemplateJson> results = lists.stream().map(i->new CouponTemplateJson(i))
                .collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }
}
