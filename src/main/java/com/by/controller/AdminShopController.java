package com.by.controller;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.ShopBindUserForm;
import com.by.json.ShopJson;
import com.by.model.Menu;
import com.by.model.Shop;
import com.by.model.User;
import com.by.service.MenuService;
import com.by.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/shops")
public class AdminShopController extends BaseController {
    private final Menu subMenu = new Menu(10);
    @Autowired
    private ShopService service;
    @Autowired
    private MenuService menuService;
    @Autowired
    @Qualifier("shopKeyUniqueValidator")
    private Validator shopKeyUniqueValidator;
    @Autowired
    @Qualifier("shopKeyValidator")
    private Validator shopKeyValidator;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("menus")
    public List<Menu> menus() {
        return menuService.findAll();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Status all() {
        return new Success<>(service.toJson(service.findAll(new Sort(Sort.Direction.ASC, "name"))));
    }

    // 店铺列表
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        Page<Shop> pages = service.findFirstPage(10);
        uiModel.addAttribute("lists", pages);
        uiModel.addAttribute("last", computeLastPage(pages.getTotalPages()));
        addMenu(uiModel);
        return "admin/shop/list";
    }

    // 新增店铺页面
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String form(Model uiModel) {
        Shop shop = new Shop();
        uiModel.addAttribute("shop", shop);
        addMenu(uiModel);
        return "admin/shop/create";
    }

    // 增加一个店铺
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("shop") Shop shop, BindingResult result, Model uiModel,
                      RedirectAttributes redirectAttributes) {
        shopKeyUniqueValidator.validate(shop, result);
        if (result.hasErrors()) {
            uiModel.addAttribute("shop", shop);
            uiModel.addAttribute("message", failMessage(messageSource));
            addMenu(uiModel);
            return "admin/shop/create";
        }
        User user = userContext.getCurrentUser();
        shop.setCreatedBy(user.getName());
        shop.setUpdatedBy(user.getName());
        Shop s = service.save(shop);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return "redirect:/admin/shops/" + s.getId();
    }

    // 查看店铺详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model uiModel, @PathVariable("id") int id) {
        Shop shop = service.findOne(id);
        if (shop == null)
            throw new NotFoundException();
        uiModel.addAttribute("shop", shop);
        addMenu(uiModel);
        return "admin/shop/edit";
    }

    // 店铺列表json
    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list(
            @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ShopJson> pages = service.findAll(pageable);
        return new Success<>(pages);
    }

    // 修改店铺信息
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String edit(@Valid @ModelAttribute("shop") Shop shop, BindingResult result, Model uiModel,
                       @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        shopKeyValidator.validate(shop, result);
        if (result.hasErrors()) {
            uiModel.addAttribute("shop", shop);
            uiModel.addAttribute("message", failMessage(messageSource));
            addMenu(uiModel);
            return "admin/shop/edit";
        }
        shop.setId(id);
        shop.setUpdatedBy(userContext.getCurrentUser().getName());
        service.update(shop);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return "redirect:/admin/shops/" + id;
    }

    // 绑定用户
    @RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
    @ResponseBody
    public Status bindUser(@PathVariable("id") int id, @RequestBody ShopBindUserForm form) {
        form.setId(id);
        return new Success<>(service.bindUser(form));
    }

    @RequestMapping(value = "/key/duplicate", method = RequestMethod.GET)
    @ResponseBody
    public boolean duplicate(@RequestParam("shopKey") String shopKey, @RequestParam(value = "id", required = false) Integer id) {
        long count;
        if (id == null) {
            count = service.countByShopKey(shopKey);
            return count > 0 ? false : true;
        } else {
            Shop shop = service.findByShopKey(shopKey);
            if (shop != null)
                return !id.equals(shop.getId()) ? false : true;
            return true;
        }
    }

    @RequestMapping(value = "/name/duplicate", method = RequestMethod.GET)
    @ResponseBody
    public boolean nameDuplicate(@RequestParam("name") String name, @RequestParam("id") Integer id) {
        Long count;
        if (id == null) {
            count = service.countByName(name);
            return count > 0 ? false : true;
        } else {
            Shop shop = service.findByName(name);
            if (shop != null)
                return !id.equals(shop.getId()) ? false : true;
            return true;
        }
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
