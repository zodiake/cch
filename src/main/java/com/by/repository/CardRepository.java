package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Card;
import com.by.model.Member;

public interface CardRepository extends PagingAndSortingRepository<Card, Long> {
	public Card findByMember(Member member);
	
	public Optional<Card> findById(Long id);
	
	public Long countByName(String name);
}
