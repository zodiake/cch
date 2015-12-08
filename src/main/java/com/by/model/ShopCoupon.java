package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by yagamai on 15-12-8.
 */
@Entity
@DiscriminatorValue("s")
public class ShopCoupon extends Coupon{
    @ManyToOne
    private Shop shop;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
