package com.by.model;

import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Table(name = "by_coupon")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //uuid
    private String code;

    //有效期开始
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_time")
    private Calendar beginTime;

    //有效期结束
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Calendar endTime;

    //需要的积分
    private Integer score;

    //生成的卡券的截止日期
    private Calendar couponEndTime;

    //对应的金额
    private Double amount;

    //名称
    private String name;

    //是否有效
    private ValidEnum valid;

    //总数
    private Integer total;

    //是否可重复
    private DuplicateEnum duplicate;

    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Calendar getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(Calendar couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ValidEnum getValid() {
        return valid;
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
    }

    public DuplicateEnum getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(DuplicateEnum duplicate) {
        this.duplicate = duplicate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @PrePersist
    private void prePersist() {
        this.code = UUID.randomUUID().toString().replace("-", "");
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
        Coupon other = (Coupon) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}