package com.by.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.by.typeEnum.ValidEnum;


@Entity
@Table(name = "by_rule")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public abstract class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private double rate;

    private String summary;

    @NotEmpty(message="{NotEmpty.rule.name}")
    private String name;

    @Enumerated
    private ValidEnum valid;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar beginTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_time")
    private Calendar createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_time")
    private Calendar updatedTime;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="updated_by")
    private String updatedBy;

    private int Score;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ValidEnum getValid() {
        return valid;
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
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

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public Calendar getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Calendar updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Rule other = (Rule) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
