package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by yagamai on 15-12-3.
 */
@Entity
@DiscriminatorValue("c")
public class PreferentialCoupon extends Coupon {
    public PreferentialCoupon() {
        super();
    }

    public PreferentialCoupon(Long id) {
        setId(id);
    }

}
