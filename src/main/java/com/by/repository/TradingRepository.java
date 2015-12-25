package com.by.repository;

import com.by.model.Member;
import com.by.model.Shop;
import com.by.model.Trading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;

/**
 * Created by yagamai on 15-11-27.
 */
public interface TradingRepository extends PagingAndSortingRepository<Trading, Long> {
    Page<Trading> findByShopAndCreatedTimeBetween(Shop shop, Calendar startTime, Calendar endTime, Pageable pageable);

    Trading findByCode(String code);

    Page<Trading> findByMember(Member member, Pageable pageable);

    @Query("select sum(amount) from Trading t where t.member=:member")
    Long sumAmountByMember(@Param("member") Member member);

    Long countByMember(Member member);
}
