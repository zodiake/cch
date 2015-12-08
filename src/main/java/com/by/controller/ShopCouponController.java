package com.by.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CouponJson;
import com.by.json.CouponTemplateJson;
import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.service.CouponService;
import com.by.service.MemberService;
import com.by.service.ShopCouponMemberService;
import com.by.service.ShopCouponService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.by.SpringExtension.SpringExtProvider;

/**
 * Created by yagamai on 15-12-8.
 */
@Controller
@RequestMapping(value = "/api/shopCoupons")
public class ShopCouponController {
    private ApplicationContext ctx;
    private ActorSystem system;
    private ActorRef ref;
    private ShopCouponService shopCouponService;
    private ShopCouponMemberService shopCouponMemberService;
    private MemberService memberService;
    private CouponService couponService;

    @Autowired
    public ShopCouponController(ApplicationContext ctx, ActorSystem system, ShopCouponService shopCouponService,
                                ShopCouponMemberService shopCouponMemberService,
                                MemberService memberService, CouponService couponService) {
        this.ctx = ctx;
        this.system = system;
        this.ref = system.actorOf(SpringExtProvider.get(system).props("ShopCouponActor"), "shopCouponActor");
        this.shopCouponService = shopCouponService;
        this.shopCouponMemberService = shopCouponMemberService;
        this.memberService = memberService;
        this.couponService = couponService;
    }

    // 可以兑换的卡券列表
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Success<Page<CouponTemplateJson>> list(
            @PageableDefault(page = 0, size = 10, sort = "sortOrder", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ShopCoupon> coupons = shopCouponService.findByValid(ValidEnum.VALID, pageable);
        List<CouponTemplateJson> results = coupons.getContent()
                .stream()
                .filter(i -> {
                    return couponService.isWithinValidDate(i);
                })
                .map(i -> {
                    return new CouponTemplateJson(i.getId(), i.getName(), i.getCouponEndTime(), i.getScore(), i.getBeginTime(),
                            i.getEndTime(), i.getSummary(),i.getShop().getName());
                }).collect(Collectors.toList());
        return new Success<>(new PageImpl<>(results, pageable, coupons.getTotalElements()));
    }

    // 用户兑换到的优惠券列表
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    @ResponseBody
    public Status couponList(HttpServletRequest request, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Member member = (Member) request.getAttribute("member");
        List<CouponJson> result = shopCouponMemberService.findByMember(member, pageable);
        return new Success<>(result);
    }
}
