package com.by.service;

import com.by.model.Member;
import com.by.model.Rule;
import com.by.model.ScoreAddHistory;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    Optional<Member> countByName(String name);

    Member save(Member member);

    Optional<Member> update(Member member);

    Optional<Member> findByNameAndPassword(Member member);

    Member useScore(Member member, int total);

    Member getScore(Member member, int total);

    List<ScoreAddHistory> extractScoreHistory(List<ScoreAddHistory> allList, int total);

}
