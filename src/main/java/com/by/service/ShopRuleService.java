package com.by.service;

import com.by.form.BaseCouponForm;
import com.by.json.RuleJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-12-22.
 */
public interface ShopRuleService {
    Page<RuleJson> findAll(BaseCouponForm form, Pageable pageable);
}
