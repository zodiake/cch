package com.by.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.model.Card;

public interface CardService {
	public Optional<Card> findOne(Long id);
	
	public Page<Card> findAll(Pageable pageable);
	
	public Optional<Card> save(Card card);
}
