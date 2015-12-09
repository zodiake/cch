package com.by.service;

import com.by.model.Card;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Optional<Card> findOne(Long id);

    Page<Card> findAll(Pageable pageable);

    Card save(Card card);

    Optional<Card> update(Card card);

    Card findByIdAndValid(Long id, ValidEnum valid);

    List<Card> findAllAuditRevision(Long id);

    List<Card> findByValid(ValidEnum valid, Pageable pageable);
}
