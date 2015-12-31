package com.by.form;

import com.by.model.Card;

/**
 * Created by yagamai on 15-12-11.
 */
public class CouponQueryForm extends BaseCouponForm {
    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
