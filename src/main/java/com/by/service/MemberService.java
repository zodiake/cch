package com.by.service;

import com.by.model.Member;
import com.by.model.ScoreAddHistory;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    public Optional<Member> findById(Long id);

    public Optional<Member> findByName(String name);

    public Optional<Member> countByName(String name);

    public Member save(Member member);

    public Optional<Member> update(Member member);

    public Optional<Member> findByNameAndPassword(Member member);

    public Member useScore(Member member, int total);

    public Member getScore(Member member, int total);

    public List<ScoreAddHistory> extractScoreHistory(List<ScoreAddHistory> allList, int total);
}
