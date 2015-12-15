package com.by.model;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("c")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class CardRule extends Rule {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id")
	private Card card;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private RuleCategory ruleCategory;

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public RuleCategory getRuleCategory() {
		return ruleCategory;
	}

	public void setRuleCategory(RuleCategory ruleCategory) {
		this.ruleCategory = ruleCategory;
	}

}
