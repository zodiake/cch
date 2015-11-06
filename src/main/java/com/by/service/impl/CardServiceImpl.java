package com.by.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Card;
import com.by.repository.CardRepository;
import com.by.service.CardService;

@Service
@Transactional
public class CardServiceImpl implements CardService {
	@Autowired
	private CardRepository repository;

	@Override
	@Cacheable("card")
	public Optional<Card> findOne(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<Card> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@CacheEvict(value = "card", key = "#card.id")
	public Optional<Card> save(Card card) {
		return repository.findById(card.getId()).map(i -> {
			i.setName(card.getName());
			return repository.save(i);
		});
	}

}
