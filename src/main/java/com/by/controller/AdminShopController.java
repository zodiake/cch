package com.by.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.ShopBindUserForm;
import com.by.model.Menu;
import com.by.model.Shop;
import com.by.service.MenuService;
import com.by.service.ShopService;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/shop")
public class AdminShopController {
	@Autowired
	private ShopService service;
	@Autowired
	private MenuService menuService;

	@ModelAttribute("menus")
	public List<Menu> menus() {
		return menuService.findAll();
	}

	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Status all(){
		return new Success<>(service.findAll());
	}

	// 店铺列表json
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(@RequestParam("name") String name,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Shop> pages = service.findAll(name, pageable);
		return new Success<>(pages);
	}

	// 店铺列表
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		Page<Shop> pages = service.findFirstPage(15);
		uiModel.addAttribute("lists", pages);
		return "admin/shop/list";
	}

	// 新增店铺页面
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		Shop shop = new Shop();
		uiModel.addAttribute("shop", shop);
		return "admin/shop/create";
	}

	// 增加一个店铺
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String add(Shop shop) {
		Shop s = service.save(shop);
		return "redirect:/admin/shop/" + s.getId();
	}

	// 查看店铺详情
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String view(Model uiModel, @PathVariable("id") Long id) {
		Shop shop = service.findOne(id);
		if (shop == null)
			throw new NotFoundException();
		uiModel.addAttribute("shop", shop);
		return "admin/shop/detail";
	}

	// 修改店铺信息
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String edit(Shop shop, @PathVariable("id") Long id) {
		shop.setId(id);
		service.update(shop);
		return "redirect:/admin/shop/" + id;
	}

	// 绑定用户
	@RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
	@ResponseBody
	public Status bindUser(@PathVariable("id") Long id, @RequestBody ShopBindUserForm form) {
		form.setId(id);
		return new Success<>(service.bindUser(form));
	}
}
