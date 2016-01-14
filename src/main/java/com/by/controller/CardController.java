package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.NotFoundException;
import com.by.model.Card;
import com.by.service.CardService;

@Controller
@RequestMapping("/api/cards")
public class CardController {
	@Autowired
	private CardService service;

	@RequestMapping(value = "/{id}/img", method = RequestMethod.GET)
	@ResponseBody
	public String img(@PathVariable("id") int id) {
		Card card = service.findOne(id);
		if (card == null)
			throw new NotFoundException();
		return card.getImgHref();
	}
}
