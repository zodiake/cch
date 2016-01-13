package com.by.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Shop;

/**
 * Created by yagamai on 15-12-1.
 */
public interface ShopRepository extends PagingAndSortingRepository<Shop, Integer> {
	Shop findByShopKey(String code);

	Long countByShopKey(String code);

	Long countByIdAndShopKey(int id, String code);

	Long countByName(String name);

	Shop findByName(String name);

	List<Shop> findAll();
}
