package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("p")
public class ParkingCoupon extends Coupon {
    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private List<ParkingCouponMember> members;

    public ParkingCoupon() {
        super();
    }

    public ParkingCoupon(Long id) {
        setId(id);
    }

    public List<ParkingCouponMember> getMembers() {
        return members;
    }

    public void setMembers(List<ParkingCouponMember> members) {
        this.members = members;
    }
}
