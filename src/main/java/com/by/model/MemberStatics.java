package com.by.model;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-25.
 */
public class MemberStatics {
    private String cardName;
    private Calendar createdTime;
    private double amount;
    private int count;
    private int sumScore;
    private int availableScore;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSumScore() {
        return sumScore;
    }

    public void setSumScore(int sumScore) {
        this.sumScore = sumScore;
    }

    public int getAvailableScore() {
        return availableScore;
    }

    public void setAvailableScore(int availableScore) {
        this.availableScore = availableScore;
    }
}
