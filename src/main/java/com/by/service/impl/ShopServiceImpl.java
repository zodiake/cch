package com.by.service.impl;

import com.by.json.ShopJson;
import com.by.model.Menu;
import com.by.model.Shop;
import com.by.repository.ShopRepository;
import com.by.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-1.
 */
@Service
@Transactional
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopRepository repository;
	@Autowired
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public Page<Shop> findByKey(String code, Pageable pageable) {
		return repository.findByKey(code, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Shop> findAll(String name, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Shop> c = cb.createQuery(Shop.class);
		Root<Shop> root = c.from(Shop.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Shop.class)));
		List<Predicate> criteria = new ArrayList<>();
		if (!StringUtils.isEmpty(name))
			criteria.add(cb.like(root.get("name"), name));
		c.where(criteria.toArray(new Predicate[0]));
		cq.where(criteria.toArray(new Predicate[0]));

		List<Shop> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();
		return new PageImpl<Shop>(lists, pageable, count);
	}

	@Override
	public Shop save(Shop shop) {
		return repository.save(shop);
	}

	@Override
	public Shop save(ShopJson shop) {
		Shop s = new Shop();
		s.setName(shop.getName());
		s.setKey(shop.getKey());
		return save(s);
	}

	@Override
	@Transactional(readOnly = true)
	public Shop findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Shop update(ShopJson shop) {
		Shop source = repository.findOne(shop.getId());
		List<Menu> menus = Arrays.stream(shop.getMenus()).map(i -> new Menu(i)).collect(Collectors.toList());
		source.setName(shop.getName());
		source.setKey(shop.getKey());
		source.setMenus(menus);
		return source;
	}
}
