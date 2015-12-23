package com.by.service.impl;

import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.ShopCoupon;
import com.by.repository.ShopCouponRepository;
import com.by.service.CouponService;
import com.by.service.ShopCouponService;
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
 * Created by yagamai on 15-12-8.
 */
@Service
@Transactional
public class ShopCouponServiceImpl implements ShopCouponService {
    @Autowired
    private ShopCouponRepository repository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CouponService couponService;

    @Override
    public ShopCoupon save(ShopCoupon coupon) {
        return repository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public ShopCoupon findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("coupon")
    public ShopCoupon findOneCache(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable) {
        Page<ShopCoupon> coupons = repository.findByValid(valid, pageable);
        coupons.getContent().forEach(i -> i.getShop());
        return coupons;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponTemplateJson> findAll(CouponQueryForm form, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ShopCoupon> c = cb.createQuery(ShopCoupon.class);
        Root<ShopCoupon> root = c.from(ShopCoupon.class);
        c.select(root);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(ShopCoupon.class)));
        Predicate[] predicates = couponService.getPredicateList(form, root, cb);
        c.where(predicates);
        cq.where(predicates);

        List<ShopCoupon> lists = em.createQuery(c)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery(cq).getSingleResult();
        List<CouponTemplateJson> results = lists.stream().map(i -> new CouponTemplateJson(i))
                .collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }
}
