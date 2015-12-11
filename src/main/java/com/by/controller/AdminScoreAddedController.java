package com.by.controller;

import com.by.service.ScoreAddHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/addedScore")
public class AdminScoreAddedController {
    @Autowired
    private ScoreAddHistoryService service;

    
}
