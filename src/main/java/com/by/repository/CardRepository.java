package com.by.repository;

import com.by.model.Card;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CardRepository extends PagingAndSortingRepository<Card, Integer> {
	Optional<Card> findById(int id);

	Card findByName(String name);
	
	Long countByName(String name);

	Card findByIdAndValid(int id, ValidEnum valid);

	Page<Card> findByValid(ValidEnum valid, Pageable pageable);
}
