package com.by.model;

import com.by.typeEnum.ParkingPayType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

/**
 * Created by yagamai on 15-11-25.
 */
@Entity
@DiscriminatorValue("p")
public class ParkingPay extends Pay {
    @Enumerated
    private ParkingPayType parkingPayType;

    private String license;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public ParkingPayType getParkingPayType() {
        return parkingPayType;
    }

    public void setParkingPayType(ParkingPayType parkingPayType) {
        this.parkingPayType = parkingPayType;
    }
}
