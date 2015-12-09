package com.by.controller;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.ShopJson;
import com.by.model.Shop;
import com.by.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/shop")
public class AdminShopController {
    @Autowired
    private ShopService service;

    //店铺列表
    @RequestMapping(method = RequestMethod.GET)
    public String list(
            @RequestParam("name") String name,
            Model uiModel,
            @PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Shop> pages = service.findAll(name, pageable);
        uiModel.addAttribute("list", pages);
        return "admin/shop/list";
    }

    // 增加一个店铺
    @RequestMapping(method = RequestMethod.POST)
    public String add(ShopJson json) {
        Shop shop = service.save(json);
        return "redirect:/admin/shop/" + shop.getId();
    }

    //查看店铺详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model uiModel, @PathVariable("id") Long id) {
        Shop shop = service.findOne(id);
        if (shop == null)
            throw new NotFoundException();
        uiModel.addAttribute("shop", shop);
        return "admin/shop/detail";
    }

    //修改店铺信息
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Status edit(@RequestBody ShopJson json, @PathVariable("id") Long id) {
        json.setId(id);
        return new Success<>(new ShopJson(service.update(json)));
    }
}
