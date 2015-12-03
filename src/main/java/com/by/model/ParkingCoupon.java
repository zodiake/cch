package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("p")
public class ParkingCoupon extends Coupon {
    public ParkingCoupon() {
        super();
    }

    public ParkingCoupon(Long id) {
        setId(id);
    }
}
