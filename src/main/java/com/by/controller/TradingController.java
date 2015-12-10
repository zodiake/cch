package com.by.controller;

import com.by.json.TradingJson;
import com.by.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yagamai on 15-12-10.
 */
@Controller
@RequestMapping(value = "/trading")
public class TradingController {
    @Autowired
    private TradingService service;

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody TradingJson json){

    }
}
