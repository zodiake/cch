package com.by.message;

import com.by.model.Member;
import com.by.model.ParkingCoupon;

/**
 * Created by yagamai on 15-12-3.
 */
public class ParkingCouponMessage {
    private ParkingCoupon parkingCoupon;
    private Member member;
    private int total;
    
    public ParkingCouponMessage(){}
    
    public ParkingCouponMessage(ParkingCoupon parkingCoupon, Member member, int total) {
		super();
		this.parkingCoupon = parkingCoupon;
		this.member = member;
		this.total = total;
	}

	public ParkingCoupon getParkingCoupon() {
        return parkingCoupon;
    }

    public void setParkingCoupon(ParkingCoupon parkingCoupon) {
        this.parkingCoupon = parkingCoupon;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
