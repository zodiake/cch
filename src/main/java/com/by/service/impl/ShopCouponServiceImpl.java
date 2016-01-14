package com.by.service.impl;

import com.by.exception.Fail;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.ShopCouponForm;
import com.by.json.ShopCouponJson;
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
import java.util.Calendar;
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
    public ShopCoupon update(ShopCoupon coupon) {
        ShopCoupon s = findOne(coupon.getId());
        s.setName(coupon.getName());
        s.setShop(coupon.getShop());
        s.setAmount(coupon.getAmount());
        s.setContentImg(coupon.getContentImg());
        s.setCoverImg(coupon.getCoverImg());
        return s;
    }

    @Override
    @Transactional(readOnly = true)
    public ShopCoupon findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("coupon")
    public ShopCoupon findOneCache(int id) {
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
    public Page<ShopCouponJson> findAll(ShopCouponForm form, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ShopCoupon> c = cb.createQuery(ShopCoupon.class);
        Root<ShopCoupon> root = c.from(ShopCoupon.class);
        c.select(root);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(ShopCoupon.class)));
        List<Predicate> predicates = couponService.getPredicateList(form, root, cb);
        if (form.getShop() != null) {
            predicates.add(cb.equal(root.get("shop"), form.getShop()));
        }
        Predicate[] array = predicates.toArray(new Predicate[0]);
        c.where(array);
        cq.where(array);

        List<ShopCoupon> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery(cq).getSingleResult();
        List<ShopCouponJson> results = lists.stream().map(i -> new ShopCouponJson(i)).collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public Page<ShopCoupon> findAllByValidAndDateBetween(ValidEnum valid, Calendar calendar, Pageable pageable) {
        return repository.findAllByValidAndDateBetween(ValidEnum.VALID, Calendar.getInstance(), pageable);
    }

    @Override
    public Page<ShopCoupon> findAllByValidAndDateBetweenAndNameLike(ValidEnum valid, Calendar calendar, String name, Pageable pageable) {
        return repository.findAllByValidAndDateBetweenAndNameLike(ValidEnum.VALID, name, Calendar.getInstance(), pageable);
    }

    @Override
    public Status valid(int id) {
        ShopCoupon r = repository.findOne(id);
        Calendar today = Calendar.getInstance();
        if (r.getBeginTime() != null) {
            if (r.getEndTime().before(today))
                return new Fail("not within valid data");
        }
        if (r.getValid().equals(ValidEnum.INVALID)) {
            r.setValid(ValidEnum.VALID);
        } else {
            r.setValid(ValidEnum.INVALID);
        }
        return new Success<String>("success");
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByName(String name) {
        return repository.countByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public ShopCoupon findByName(String name) {
        return repository.findByName(name);
    }
}
