package com.by.service;

import com.by.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-12-1.
 */
public interface ShopService {
    Page<Shop> findByCode(String code, Pageable pageable);
}
