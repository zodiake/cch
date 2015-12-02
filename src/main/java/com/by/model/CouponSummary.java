package com.by.model;

import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "by_coupon_summary")
public class CouponSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 需要使用多少分数兑换
    private int score;

    @OneToMany(mappedBy = "summary", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<Coupon> coupons;

    // 生成的兑换券数量
    private int total;

    @Enumerated
    private ValidEnum valid;

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

    // 有效期开始
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_time")
    private Calendar beginTime;

    // 有效期结束
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Calendar endTime;

    //兑换的券的结束时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "coupon_end_time")
    private Calendar couponEndTime;

    @Enumerated
    private DuplicateEnum duplicate;

    private String summary;

    public CouponSummary() {
    }

    public CouponSummary(Long id) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ValidEnum getValid() {
        return valid;
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Calendar getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(Calendar couponEndTime) {
        this.couponEndTime = couponEndTime;
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
        CouponSummary other = (CouponSummary) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}