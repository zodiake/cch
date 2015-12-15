package com.by.model;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("s")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class ShopRule extends Rule {
	@ManyToMany(mappedBy = "rules")
	private List<Shop> shops;

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
}
