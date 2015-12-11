package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Status;
import com.by.json.TradingRequestJson;
import com.by.service.TradingService;

/**
 * Created by yagamai on 15-12-10.
 */
@Controller
@RequestMapping(value = "/trading")
public class TradingController {
	@Autowired
	private TradingService service;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Status create(@RequestBody TradingRequestJson json) {
		service.save(json);
		return new Status("success");
	}
}
