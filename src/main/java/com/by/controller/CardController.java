package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.model.Card;
import com.by.service.CardService;

@Controller
@RequestMapping(value = "/card")
public class CardController extends UntilController<Card> {
	@Autowired
	private CardService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Card get(@PathVariable("id") Long id) {
		return lift(() -> service.findOne(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Card update(@PathVariable("id") Long id) {
		Card c=new Card();
		c.setId(id);
		c.setName("haha");
		return lift(() -> service.save(c));
	}
}
