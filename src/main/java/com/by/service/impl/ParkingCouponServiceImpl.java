package com.by.service.impl;

import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.ParkingCoupon;
import com.by.repository.ParkingCouponRepository;
import com.by.service.CouponService;
import com.by.service.ParkingCouponService;
import com.by.typeEnum.ValidEnum;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParkingCouponServiceImpl implements ParkingCouponService {
    @Autowired
    private ParkingCouponRepository repository;
    @Autowired
    private CouponService couponService;
    @Autowired
    private EntityManager em;

    @Override
    public ParkingCoupon save(ParkingCoupon coupon) {
        Calendar time = coupon.getEndTime();
        time.set(Calendar.HOUR, 23);
        time.set(Calendar.MINUTE, 59);
        time.set(Calendar.SECOND, 59);
        return repository.save(coupon);
    }

    @Override
    public Optional<ParkingCoupon> update(ParkingCoupon coupon) {
        return repository.findById(coupon.getId()).map(i -> {
            i.setAmount(coupon.getAmount());
            i.setName(coupon.getName());
            i.setScore(coupon.getScore());
            return i;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingCoupon> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParkingCoupon> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingCoupon findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Cacheable(value = "coupon")
    @Transactional(readOnly = true)
    public Page<ParkingCoupon> findByValid(ValidEnum valid, Pageable pageable) {
        return repository.findByValid(valid, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingCoupon> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingCoupon> findFirstPage(int size) {
        return repository.findAll(new PageRequest(0, size, Sort.Direction.DESC, "createdTime"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponTemplateJson> findAll(CouponQueryForm form, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ParkingCoupon> c = cb.createQuery(ParkingCoupon.class);
        Root<ParkingCoupon> root = c.from(ParkingCoupon.class);
        c.select(root);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(ParkingCoupon.class)));
        Predicate[] predicates = couponService.getPredicateList(form, root, cb);
        c.where(predicates);
        cq.where(predicates);

        List<ParkingCoupon> lists = em.createQuery(c)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery(cq).getSingleResult();
        List<CouponTemplateJson> results = lists.stream().map(i -> new CouponTemplateJson(i))
                .collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public ParkingCoupon validOrInValid(ParkingCoupon coupon) {
        ParkingCoupon c = repository.findOne(coupon.getId());
        if (c.getValid().equals(ValidEnum.VALID))
            c.setValid(ValidEnum.INVALID);
        else
            c.setValid(ValidEnum.VALID);
        return c;
    }

}
