package com.by.service;

import com.by.form.ShopBindUserForm;
import com.by.json.ShopJson;
import com.by.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by yagamai on 15-12-1.
 */
public interface ShopService {
    Shop findByKey(String code);

    Page<Shop> findAll(String name, Pageable pageable);
    
    Page<ShopJson> findAll(Pageable pageable);

    Page<Shop> findFirstPage(int size);

    Shop save(Shop shop);

    Shop save(ShopJson shop);

    Shop findOne(int id);

    Shop update(ShopJson shop);

    Shop update(Shop shop);

    Shop bindUser(ShopBindUserForm form);

    List<ShopJson> findAllJson();

    List<Shop> findAll(Sort sort);
    
    Shop findByPos(String code);
}
