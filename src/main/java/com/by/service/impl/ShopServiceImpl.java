package com.by.service.impl;

import com.by.model.Shop;
import com.by.repository.ShopRepository;
import com.by.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by yagamai on 15-12-1.
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopRepository repository;

    @Override
    public Page<Shop> findByCode(String code, Pageable pageable) {
        return repository.findByKey(code, pageable);
    }
}
