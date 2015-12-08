package com.by.service.impl;

import com.by.model.Sequence;
import com.by.repository.SequenceRepository;
import com.by.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 15-12-8.
 */
@Service
@Transactional
public class SequenceServiceImpl implements SequenceService {
    @Autowired
    private SequenceRepository repository;

    @Override
    public Sequence save(Sequence sequence) {
        return repository.save(sequence);
    }
}
