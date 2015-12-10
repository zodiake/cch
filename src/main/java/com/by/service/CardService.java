package com.by.service;

import com.by.json.CardJson;
import com.by.model.Card;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Card findOne(Long id);
    
    Card findOneAndRule(Long id);

    Page<Card> findAll(Pageable pageable);

    Card save(Card card);

    Card save(CardJson json);

    Optional<Card> update(Card card);

    Card findByIdAndValid(Long id, ValidEnum valid);

    List<Card> findAllAuditRevision(Long id);

    List<Card> findByValid(ValidEnum valid, Pageable pageable);
}
