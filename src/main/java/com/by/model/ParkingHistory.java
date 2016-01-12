package com.by.model;

import com.by.form.ParkingForm;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by yagamai on 15-11-24.
 */
@Entity
@Table(name = "by_parking_history")
public class ParkingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String license;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Calendar startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Calendar endTIme;
    
    private float amount;

    public ParkingHistory() {
    }

    public ParkingHistory(ParkingForm form) {
        this.license = form.getLicense();
        this.startTime = form.getStartTime();
        this.endTIme = form.getEndTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTIme() {
        return endTIme;
    }

    public void setEndTIme(Calendar endTIme) {
        this.endTIme = endTIme;
    }
    
    public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingHistory that = (ParkingHistory) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
