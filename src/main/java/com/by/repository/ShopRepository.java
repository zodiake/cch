package com.by.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Shop;

/**
 * Created by yagamai on 15-12-1.
 */
public interface ShopRepository extends PagingAndSortingRepository<Shop, Integer> {
    Shop findByShopKey(String code);
    Long countByShopKey(String code);
}
