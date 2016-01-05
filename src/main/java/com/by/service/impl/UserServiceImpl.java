package com.by.service.impl;

import com.by.form.UserQueryForm;
import com.by.model.Menu;
import com.by.model.User;
import com.by.repository.UserRepository;
import com.by.service.UserService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;
	@Autowired
	private EntityManager em;

	@Transactional(readOnly = true)
	public User findByName(String name) {
		User user = repository.findByName(name);
		user.getUserAuthorities().size();
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Menu> getMenus(User user) {
		Set<Menu> menus = new HashSet<>();
		User u = repository.findOne(user.getId());
		if (user.getShop() != null) {
			menus.addAll(u.getShop().getMenus());
		} else {
			u.getUserAuthorities().stream().filter(a -> a.getValid().equals(ValidEnum.VALID)).forEach(i -> {
				menus.addAll(i.getMenus());
			});
		}
		return menus;
	}

	@Override
	public Page<User> findAll(UserQueryForm form, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> c = cb.createQuery(User.class);
		Root<User> root = c.from(User.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(User.class)));
		List<Predicate> criteria = new ArrayList<>();
		if (form != null) {
			if (form.getName() != null)
				criteria.add(cb.equal(root.get("name"), form.getName()));
			if (form.getValid() != null)
				criteria.add(cb.equal(root.get("valid"), form.getValid()));
		}

		c.where(criteria.toArray(new Predicate[0]));
		cq.where(criteria.toArray(new Predicate[0]));

		List<User> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();

		return new PageImpl<>(lists, pageable, count);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findId(int id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public User save(User user){
		return repository.save(user);
	}

	@Override
	public Long countByName(String name) {
		return repository.countByName(name);
	}
}
