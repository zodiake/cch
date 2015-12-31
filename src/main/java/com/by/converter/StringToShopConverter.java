package com.by.converter;

import com.by.model.Shop;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by yagamai on 15-12-31.
 */
public class StringToShopConverter implements Converter<String, Shop> {
    @Override
    public Shop convert(String source) {
        return new Shop(Integer.parseInt(source));
    }
}
