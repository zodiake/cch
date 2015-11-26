package com.by.repository;

import com.by.model.Card;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CardRepository extends PagingAndSortingRepository<Card, Long> {
    public Optional<Card> findById(Long id);

    public Long countByName(String name);

    Card findByIdAndValid(Long id, ValidEnum valid);
}
