package com.by.converter;

import com.by.model.Card;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by yagamai on 15-12-22.
 */
public class StringToCardConverter implements Converter<String,Card>{
    @Override
    public Card convert(String source) {
        return new Card(Integer.parseInt(source));
    }
}
