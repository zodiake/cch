package com.by.service;

import java.util.List;

import com.by.form.CouponQueryForm;
import com.by.json.RuleJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.RuleCategory;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-12-15.
 */
public interface CardRuleService {
    List<CardRule> findByRuleCategoryAndCardAndValid(RuleCategory category, Card card, ValidEnum valid);

    List<CardRule> findByRuleCategoryAndCard(RuleCategory category, Card card);
    
    CardRule save(CardRule rule);
    
    CardRule findOne(int id);

    Page<RuleJson> findAll(CouponQueryForm form, Pageable pageable);
}
