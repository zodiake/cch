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
@DiscriminatorValue("g")
public class GiftCoupon extends Coupon {
    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private List<GiftCouponMember> members;

    public GiftCoupon() {
        super();
    }

    public GiftCoupon(Long id) {
        setId(id);
    }

    public List<GiftCouponMember> getMembers() {
        return members;
    }

    public void setMembers(List<GiftCouponMember> members) {
        this.members = members;
    }
}
