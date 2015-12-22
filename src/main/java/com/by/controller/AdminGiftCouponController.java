package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.service.GiftCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yagamai on 15-12-22.
 */
@Controller
@RequestMapping("/admin/giftCoupons")
public class AdminGiftCouponController {
    private final int INIT_SIZE = 10;
    private final int INIT_PAGE = 0;
    private final String CREATE = "admin/giftCoupons/create";
    private final String EDIT = "admin/giftCoupons/edit";
    private final String REDIRECT = "redirect:/admin/giftCoupons/";
    private final String LIST = "admin/giftCoupons/list";
    @Autowired
    private GiftCouponService service;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        Page<CouponTemplateJson> lists = service.findAll(null,
                new PageRequest(0, INIT_SIZE, Sort.Direction.DESC, "beginTime"));
        uiModel.addAttribute("lists", lists);
        return LIST;
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list(CouponQueryForm form,
                       @PageableDefault(page = INIT_PAGE, size = INIT_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<>(service.findAll(form, pageable));
    }
}
