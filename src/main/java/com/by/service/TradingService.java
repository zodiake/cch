package com.by.service;

import com.by.json.TradingJson;
import com.by.json.TradingRequestJson;
import com.by.model.Member;
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

    Trading save(TradingRequestJson trading);

    int tradeToScore(Trading trading);

    Trading bindMember(Trading trading, String mobile);

    Page<TradingJson> findByMember(Member member, Pageable pageable);

    Long sumAmountByMember(Member m);

    Long countByMember(Member m);
}
