package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.model.Card;
import com.by.service.CardService;

@Controller
@RequestMapping(value = "/card")
public class CardController {
	@Autowired
	private CardService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Success<Card> get(@PathVariable("id") Long id) {
		return service.findOne(id).map(i -> new Success<Card>(i)).orElseThrow(() -> new NotFoundException());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Status update(@PathVariable("id") Long id) {
		Card c = new Card();
		c.setId(id);
		c.setName("haha");
		service.update(c);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Status save(@Valid Card card,BindingResult result) {
		if(result.hasErrors()){
		}
		Card c = new Card();
		c.setName("low");
		service.save(c);
		return new Status("success");
	}
}
