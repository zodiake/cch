package com.by.model;

import com.by.typeEnum.PermanentEnum;
import com.by.typeEnum.ValidEnum;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "by_rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private RuleCategory ruleCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    private String summary;

    @Enumerated
    private ValidEnum valid;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar beginTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endTime;

    private int Score;

    @Enumerated
    private PermanentEnum permanent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
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

    public PermanentEnum getPermanent() {
        return permanent;
    }

    public void setPermanent(PermanentEnum permanent) {
        this.permanent = permanent;
    }

    public RuleCategory getRuleCategory() {
        return ruleCategory;
    }

    public void setRuleCategory(RuleCategory ruleCategory) {
        this.ruleCategory = ruleCategory;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
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
        Rule other = (Rule) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
