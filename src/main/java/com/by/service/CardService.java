package com.by.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.json.CardJson;
import com.by.model.Card;
import com.by.typeEnum.ValidEnum;

public interface CardService {
    Card findOne(int id);

    Page<Card> findAll(Pageable pageable);

    List<Card> findAll();

    Card save(Card card);

    Card save(CardJson json);

    Card update(Card card);

    Card findByIdAndValid(int id, ValidEnum valid);

    List<Card> findAllAuditRevision(Long id);

    Page<Card> findByValid(ValidEnum valid, Pageable pageable);

    List<Card> findAllCache();
    
    Long countByName(String name);
    
    Card findByName(String name);
}
