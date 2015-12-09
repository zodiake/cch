package com.by.service;

import com.by.json.ShopJson;
import com.by.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-12-1.
 */
public interface ShopService {
    Page<Shop> findByKey(String code, Pageable pageable);

    Page<Shop> findAll(String name,Pageable pageable);

    Shop save(Shop shop);

    Shop save(ShopJson shop);

    Shop findOne(Long id);

    Shop update(ShopJson shop);
}
