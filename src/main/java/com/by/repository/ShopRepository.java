package com.by.repository;

import com.by.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yagamai on 15-12-1.
 */
public interface ShopRepository extends PagingAndSortingRepository<Shop, Long> {
    Shop findByShopKey(String code);
}
