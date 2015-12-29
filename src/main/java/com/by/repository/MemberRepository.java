package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Card;
import com.by.model.Member;

public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {
	Optional<Member> findById(Long id);

	Optional<Member> findByName(String name);

	Long countByName(String name);

	Long countByCard(Card card);
}
