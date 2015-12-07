package com.by.repository;

import com.by.model.Member;
import com.by.model.ScoreAddHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ScoreAddHistoryRepository extends PagingAndSortingRepository<ScoreAddHistory, Long> {
    List<ScoreAddHistory> findByMember(Member member, Sort sort);

    @Query("select sum(s.total) from ScoreAddHistory s where s.member=:member")
    Long sumByMember(@Param("member") Member member);
}
