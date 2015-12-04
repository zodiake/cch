package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by yagamai on 15-12-3.
 */
@Entity
@DiscriminatorValue("c")
public class PreferentialCoupon extends Coupon {
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "coupon")
    private List<PreferentialCouponUseHistory> useHistoryList;

    public PreferentialCoupon() {
        super();
    }

    public PreferentialCoupon(Long id) {
        setId(id);
    }
}
