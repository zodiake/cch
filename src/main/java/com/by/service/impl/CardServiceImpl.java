package com.by.service.impl;

import com.by.model.Card;
import com.by.repository.CardRepository;
import com.by.service.CardService;
import com.by.typeEnum.ValidEnum;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository repository;
    @Autowired
    private EntityManager em;

    @Override
    @Cacheable("card")
    @Transactional(readOnly = true)
    public Optional<Card> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Card> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @CacheEvict("card")
    public Optional<Card> update(Card card) {
        return repository.findById(card.getId()).map(i -> {
            i.setName(card.getName());
            return repository.save(i);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Card findByIdAndValid(Long id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }

    @Override
    @CachePut("card")
    public Card save(Card card) {
        return repository.save(card);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllAuditRevision(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(em);
        return auditReader.createQuery().forRevisionsOfEntity(Card.class, true, true)
                .add(AuditEntity.id().eq(id)).addOrder(AuditEntity.revisionNumber().asc()).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findByValid(ValidEnum valid, Pageable pageable) {
        Page<Card> cards = repository.findByValid(valid, pageable);
        return cards.getContent();
    }
}
