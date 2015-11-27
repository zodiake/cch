package com.by.service.impl;

import com.by.model.Card;
import com.by.repository.CardRepository;
import com.by.service.CardService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository repository;

    @Override
    @CachePut({"card"})
    public Optional<Card> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Card> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Card> update(Card card) {
        return repository.findById(card.getId()).map(i -> {
            i.setName(card.getName());
            return repository.save(i);
        });
    }

    @Override
    public Card findByIdAndValid(Long id, ValidEnum valid) {
        return null;
    }

    @Override
    public Card save(Card card) {
        return repository.save(card);
    }

}
