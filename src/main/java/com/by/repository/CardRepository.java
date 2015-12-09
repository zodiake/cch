package com.by.repository;

import com.by.model.Card;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CardRepository extends PagingAndSortingRepository<Card, Long> {
    Optional<Card> findById(Long id);

    Long countByName(String name);

    Card findByIdAndValid(Long id, ValidEnum valid);

    Page<Card> findByValid(ValidEnum valid, Pageable pageable);
}
