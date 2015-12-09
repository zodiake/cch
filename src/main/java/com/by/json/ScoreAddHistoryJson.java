package com.by.json;

import com.by.model.ScoreAddHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by yagamai on 15-12-9.
 */
public class ScoreAddHistoryJson {
    private Long id;
    private String createdTime;
    private int total;
    private String reason;

    public ScoreAddHistoryJson() {
    }

    public ScoreAddHistoryJson(ScoreAddHistory history) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.id = history.getId();
        this.createdTime = format.format(history.getCreatedTime().getTime());
        this.total = history.getTotal();
        this.reason = history.getReason();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
