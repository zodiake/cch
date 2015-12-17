package com.by.json;

/**
 * Created by yagamai on 15-12-7.
 */
//可用积分，总积分集合
public class ScoreJson {
    // 可用积分
    private int available;
    // 总积分
    private int sum;

    public ScoreJson() {
    }

    public ScoreJson(int available, int sum) {
        this.available = available;
        this.sum = sum;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
