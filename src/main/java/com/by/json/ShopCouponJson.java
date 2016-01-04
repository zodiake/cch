package com.by.json;

import com.by.model.ShopCoupon;

/**
 * Created by yagamai on 16-1-4.
 */
public class ShopCouponJson extends CouponTemplateJson {
    private String shopName;

    public ShopCouponJson(ShopCoupon coupon) {
        super(coupon);
        this.shopName = coupon.getShop().getName();
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
