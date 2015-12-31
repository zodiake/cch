package com.by.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.by.form.BaseCouponForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.form.CouponQueryForm;
import com.by.model.Rule;
import com.by.repository.RuleRepository;
import com.by.service.RuleService;
import com.by.typeEnum.CouponAdminStateEnum;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-11-26.
 */
@Service
@Transactional
public class RuleServiceImpl implements RuleService {
	@Autowired
	private RuleRepository repository;

	@Override
	@CachePut({ "ruleCard" })
	public Rule save(Rule rule) {
		return repository.save(rule);
	}

	@Override
	@CacheEvict({ "ruleCard" })
	public Rule update(Rule rule) {
		Rule source = repository.findOne(rule.getId());
		source.setBeginTime(rule.getBeginTime());
		return source;
	}

	@Override
	public int getMaxScore(List<? extends Rule> rules) {
		Calendar today = Calendar.getInstance();
		List<Integer> scoreList = rules.stream().filter(i -> {
			if (i.getBeginTime() != null && i.getEndTime() != null)
				return i.getBeginTime().before(today) && i.getEndTime().after(today);
			return true;
		}).map(Rule::getScore).collect(Collectors.toList());
		return Collections.max(scoreList);
	}

	@Override
	public double getMaxRate(List<? extends Rule> rules) {
		Calendar today = Calendar.getInstance();
		List<Double> scoreList = rules.stream().filter(i -> {
			if (i.getBeginTime() != null && i.getEndTime() != null)
				return i.getBeginTime().before(today) && i.getEndTime().after(today);
			return true;
		}).map(Rule::getRate).collect(Collectors.toList());
		return Collections.max(scoreList);
	}

	@Override
	public boolean withinValidDate(Rule rule) {
		Calendar today = Calendar.getInstance();
		return rule.getValid().equals(ValidEnum.VALID) && rule.getBeginTime().before(today)
				&& rule.getEndTime().after(today);
	}

	@Override
	public <T extends Rule> List<Predicate> getPredicateList(BaseCouponForm form, Root<T> root, CriteriaBuilder cb) {
		List<Predicate> criteria = new ArrayList<>();
		if (form.getState() != null) {
			Calendar today = Calendar.getInstance();
			if (form.getState().equals(CouponAdminStateEnum.CLOSED)) {
				criteria.add(cb.equal(root.get("valid"), ValidEnum.INVALID));
			} else if (form.getState().equals(CouponAdminStateEnum.NOEXPIRE)) {
				criteria.add(cb.greaterThan(root.get("beginTime"), today));
			} else if (form.getState().equals(CouponAdminStateEnum.USING)) {
				criteria.add(cb.or(
						cb.and(cb.lessThanOrEqualTo(root.get("beginTime"), today),
								cb.greaterThanOrEqualTo(root.get("endTime"), today)),
						cb.and(cb.isNull(root.get("beginTime"))), cb.isNull(root.get("endTime"))));
			} else if (form.getState().equals(CouponAdminStateEnum.EXPIRE)) {
				criteria.add(cb.lessThan(root.get("endTime"), today));
			}
		}
		if (form.getBeginTime() != null) {
			criteria.add(cb.or(cb.lessThanOrEqualTo(root.get("beginTime"), form.getBeginTime()),
					cb.and(cb.isNull(root.get("beginTime"))), cb.isNull(root.get("endTime"))));
		}
		if (form.getEndTime() != null) {
			criteria.add(cb.or(cb.greaterThanOrEqualTo(root.get("endTime"), form.getEndTime()),
					cb.and(cb.isNull(root.get("beginTime"))), cb.isNull(root.get("endTime"))));
		}
		return criteria;
	}
}
