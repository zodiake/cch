package com.by.service;

import java.util.List;
import java.util.Optional;

import com.by.model.Member;
import com.by.model.ScoreAddHistory;

public interface MemberService {
	Optional<Member> findById(Long id);

	Optional<Member> findByName(String name);

	Long countByName(String name);

	Member save(Member member);

	Optional<Member> update(Member member);

	Optional<Member> findByNameAndPassword(Member member);

	Member useScore(Member member, int total);

	Member updateScore(Member member, int total, String reason);

	List<ScoreAddHistory> extractScoreHistory(List<ScoreAddHistory> allList, int total);

	Member findOne(Long id);
}
