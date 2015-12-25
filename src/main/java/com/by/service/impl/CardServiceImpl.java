package com.by.service.impl;

import com.by.json.CardJson;
import com.by.model.Card;
import com.by.repository.CardRepository;
import com.by.service.CardService;
import com.by.typeEnum.ValidEnum;
import com.google.common.collect.Lists;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository repository;
    @Autowired
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Card findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Card> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Card update(Card card) {
        Card source = repository.findOne(card.getId());
        source.setUpdatedBy(card.getUpdatedBy());
        source.setName(card.getName());
        source.setImgHref(card.getImgHref());
        return source;
    }

    @Override
    @Transactional(readOnly = true)
    public Card findByIdAndValid(int id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }

    @Override
    public Card save(Card card) {
        card.setValid(ValidEnum.VALID);
        return repository.save(card);
    }

    @Override
    public Card save(CardJson json) {
        Card card = new Card();
        card.setName(json.getName());
        card.setInitScore(json.getInitScore());
        return save(card);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllAuditRevision(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(em);
        return auditReader.createQuery().forRevisionsOfEntity(Card.class, true, true).add(AuditEntity.id().eq(id))
                .addOrder(AuditEntity.revisionNumber().asc()).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findByValid(ValidEnum valid, Pageable pageable) {
        Page<Card> cards;
        if (valid != null)
            cards = repository.findByValid(valid, pageable);
        cards = repository.findAll(pageable);
        return cards.getContent();
    }

}
