package com.by.service;

import java.util.Optional;

import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.model.Card;

public interface CardService {
	Optional<Card> findOne(Long id);

	Page<Card> findAll(Pageable pageable);

	Card save(Card card);

	Optional<Card> update(Card card);

	Card findByIdAndValid(Long id, ValidEnum valid);
}
