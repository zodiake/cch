package com.by.model;

import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "by_parking_coupon")
// 停车券模板
public class ParkingCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int amount;

    private int score;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
    private List<ParkingCouponMember> members;

    private ValidEnum valid;

    //生效日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_time")
    private Calendar beginTime;

    //无效日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Calendar endTime;

    //生成的券的截止日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "coupon_end_time")
    private Calendar couponEndTime;

    //数量
    private Integer total;

    //用户是否只能兑换一次
    @Enumerated
    private DuplicateEnum duplicate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Calendar createdTime;

    @Column(name = "created_by")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Calendar updatedTime;

    @Column(name = "updated_by")
    private String updatedBy;

    public ParkingCoupon() {
    }

    public ParkingCoupon(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ValidEnum getValid() {
        return valid;
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Calendar updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Calendar getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(Calendar couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public DuplicateEnum getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(DuplicateEnum duplicate) {
        this.duplicate = duplicate;
    }

    @PrePersist
    private void prePersist() {
        this.createdTime = Calendar.getInstance();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedTime = Calendar.getInstance();
    }

    public List<ParkingCouponMember> getMembers() {
        return members;
    }

    public void setMembers(List<ParkingCouponMember> members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ParkingCoupon other = (ParkingCoupon) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
