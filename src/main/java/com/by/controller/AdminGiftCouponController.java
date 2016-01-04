package com.by.controller;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.BaseCouponForm;
import com.by.form.CouponQueryForm;
import com.by.json.GiftCouponJson;
import com.by.model.GiftCoupon;
import com.by.model.Menu;
import com.by.model.Message;
import com.by.service.GiftCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by yagamai on 15-12-22.
 */
@Controller
@RequestMapping("/admin/giftCoupons")
public class AdminGiftCouponController extends BaseController {
    private final String CREATE = "admin/giftCoupons/create";
    private final String EDIT = "admin/giftCoupons/edit";
    private final String REDIRECT = "redirect:/admin/giftCoupons/";
    private final String LIST = "admin/giftCoupons/lists";
    private final Menu subMenu = new Menu(7);
    @Autowired
    private GiftCouponService service;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(BaseCouponForm form, Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GiftCouponJson> lists = service.findAll(form,
                new PageRequest(0, PAGE_SIZE, Sort.Direction.DESC, "beginTime"));
        uiModel.addAttribute("lists", lists);
        uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
        uiModel.addAttribute("form", form);
        addMenu(uiModel);
        return LIST;
    }

    // 新增页
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String form(Model uiModel) {
        GiftCoupon coupon = new GiftCoupon();
        uiModel.addAttribute("coupon", coupon);
        addMenu(uiModel);
        return CREATE;
    }

    // 处理新增逻辑
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("coupon") GiftCoupon coupon, BindingResult result,
                       Model uiModel, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            uiModel.addAttribute("coupon", coupon);
            uiModel.addAttribute("message", new Message("success", messageSource.getMessage("save.fail", new Object[]{}, Locale.CHINA)));
            addMenu(uiModel);
            return CREATE;
        }
        GiftCoupon source = service.save(coupon);
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("save.fail", new Object[]{}, Locale.CHINA)));
        return REDIRECT + source.getId();
    }

    // 获取一条记录
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model uiModel) {
        GiftCoupon coupon = service.findOne(id);
        if (coupon == null)
            throw new NotFoundException();
        uiModel.addAttribute("coupon", coupon);
        addMenu(uiModel);
        return EDIT;
    }

    //
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@Valid @ModelAttribute("coupon") GiftCoupon coupon,
                         BindingResult result, @PathVariable("id") int id, Model uiModel, RedirectAttributes redirectAttributes) {
        coupon.setId(id);
        if (result.hasErrors()) {
            uiModel.addAttribute("coupon", coupon);
            addMenu(uiModel);
            return EDIT;
        }
        GiftCoupon source = service.update(coupon);
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("save.success", new Object[]{}, Locale.CHINA)));
        return REDIRECT + source.getId();
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list(CouponQueryForm form,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<>(service.findAll(form, pageable));
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
