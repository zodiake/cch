package com.by.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.form.ShopCouponForm;
import com.by.json.ShopCouponJson;
import com.by.model.ShopCoupon;
import com.by.repository.ShopCouponRepository;
import com.by.service.CouponService;
import com.by.service.ShopCouponService;
import com.by.typeEnum.ValidEnum;

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
		List<ShopCouponJson> results = lists.stream().map(i -> new ShopCouponJson(i))
				.collect(Collectors.toList());
		return new PageImpl<>(results, pageable, count);
	}

	@Override
	public Page<ShopCoupon> findAllByValidAndDateBetween(ValidEnum valid, Calendar calendar, Pageable pageable) {
		return repository.findAllByValidAndDateBetween(ValidEnum.VALID, Calendar.getInstance(), pageable);
	}
}
