package com.by.service;

import com.by.model.Shop;
import com.by.model.Trading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;

/**
 * Created by yagamai on 15-11-27.
 */
public interface TradingService {
    Page<Trading> findByShopAndCreatedTimeBetween(Shop shop, Calendar startTime, Calendar endTime, Pageable pageable);

    Trading save(Trading trading);

    int tradeToScore(Trading trading);
}
