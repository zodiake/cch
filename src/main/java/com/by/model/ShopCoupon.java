package com.by.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yagamai on 15-12-8.
 */
@Entity
@DiscriminatorValue("s")
public class ShopCoupon extends Coupon {
    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private List<ShopCouponMember> members;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    @NotNull(message="{NotNull.shopCoupon.shop}")
    private Shop shop;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ShopCouponMember> getMembers() {
        return members;
    }

    public void setMembers(List<ShopCouponMember> members) {
        this.members = members;
    }
}
