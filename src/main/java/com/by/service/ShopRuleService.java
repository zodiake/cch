package com.by.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.exception.Status;
import com.by.form.BaseCouponForm;
import com.by.json.RuleJson;
import com.by.model.ShopRule;

/**
 * Created by yagamai on 15-12-22.
 */
public interface ShopRuleService {
	Page<RuleJson> findAll(BaseCouponForm form, Pageable pageable);

	ShopRule findOne(int id);

	ShopRule update(ShopRule r);

	ShopRule save(ShopRule r);

	Status valid(int id);
}
