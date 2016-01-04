package com.by.json;

import com.by.model.Shop;
import com.by.model.ShopCoupon;

/**
 * Created by yagamai on 16-1-4.
 */
public class ShopCouponJson extends CouponTemplateJson {
    private Shop shop;

    public ShopCouponJson(ShopCoupon coupon) {
        super(coupon);
        this.shop = coupon.getShop();
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
