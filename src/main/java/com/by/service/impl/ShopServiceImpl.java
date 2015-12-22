package com.by.service.impl;

import com.by.exception.NotFoundException;
import com.by.form.ShopBindUserForm;
import com.by.json.ShopJson;
import com.by.model.Menu;
import com.by.model.Shop;
import com.by.model.User;
import com.by.repository.ShopRepository;
import com.by.service.ShopService;
import com.by.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
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
	@Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "shop")
	public Shop findByKey(String code) {
		Shop shop = repository.findByShopKey(code);
		shop.getRules().size();
		return shop;
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
		return new PageImpl<>(lists, pageable, count);
	}

	public Page<Shop> findFirstPage(int size) {
		return repository.findAll(new PageRequest(0, size, Sort.Direction.DESC, "createdTime"));
	}

	@Override
	public Shop save(Shop shop) {
		return repository.save(shop);
	}

	@Override
	public Shop save(ShopJson shop) {
		Shop s = new Shop();
		s.setName(shop.getName());
		s.setShopKey(shop.getKey());
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
		source.setShopKey(shop.getKey());
		source.setMenus(menus);
		return source;
	}

	@Override
	public Shop bindUser(ShopBindUserForm form) {
		Shop shop = findOne(form.getId());
		User user = userService.findByName(form.getUseName());
		if (shop == null)
			throw new NotFoundException();
		if (user == null)
			throw new NotFoundException();
		shop.setUser(user);
		return shop;
	}

	@Override
	@Cacheable("shop")
	public List<ShopJson> findAll() {
		return Lists.newArrayList(repository.findAll()).stream().map(ShopJson::new).collect(Collectors.toList());
	}

	@Override
	public Shop update(Shop shop) {
		Shop source = findOne(shop.getId());
		source.setMenus(shop.getMenus());
		source.setName(shop.getName());
		return source;
	}
}
