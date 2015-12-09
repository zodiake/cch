package com.by.controller;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CardJson;
import com.by.model.Card;
import com.by.service.CardService;
import com.by.typeEnum.ValidEnum;
import com.by.utils.FailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/card")
public class AdminCardController {
    @Autowired
    private CardService service;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel,
                       @PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Card> validLists = service.findByValid(ValidEnum.VALID, pageable);
        List<Card> inValidLists = service.findByValid(ValidEnum.INVALID, pageable);
        uiModel.addAttribute("valid", validLists);
        uiModel.addAttribute("inValid", inValidLists);
        return "card";
    }

    @RequestMapping(params = "valid", method = RequestMethod.GET)
    @ResponseBody
    public List<CardJson> list(@RequestParam("valid") String valid,
                               @PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        ValidEnum validEnum = ValidEnum.fromString(valid);
        List<CardJson> cards = service.findByValid(validEnum, pageable)
                .stream()
                .map(i -> {
                    return new CardJson(i);
                })
                .collect(Collectors.toList());
        return cards;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Success<CardJson> get(@PathVariable("id") Long id) {
        return service.findOne(id).map(i -> {
            return new Success<>(new CardJson(i));
        }).orElseThrow(() -> new NotFoundException());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Status update(@PathVariable("id") Long id) {
        Card c = new Card();
        c.setId(id);
        c.setName("haha");
        service.update(c);
        return new Success<>("success");
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Status save(@Valid Card card, BindingResult result) {
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        Card c = new Card();
        c.setName("low");
        service.save(c);
        return new Status("success");
    }
}
