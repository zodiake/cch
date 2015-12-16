package com.by.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.by.exception.*;
import com.by.json.CouponJson;
import com.by.json.CouponTemplateJson;
import com.by.json.ExchangeCouponJson;
import com.by.message.ShopCouponMessage;
import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.service.CouponService;
import com.by.service.MemberService;
import com.by.service.ShopCouponMemberService;
import com.by.service.ShopCouponService;
import com.by.typeEnum.ValidEnum;
import com.by.utils.FailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import scala.concurrent.duration.Duration;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
    private ShaPasswordEncoder passwordEncoder;

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
                    CouponTemplateJson json = new CouponTemplateJson(i);
                    json.setShopName(i.getShop().getName());
                    return json;
                }).collect(Collectors.toList());
        return new Success<>(new PageImpl<>(results, pageable, coupons.getTotalElements()));
    }

    // 用户兑换到的优惠券列表
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    @ResponseBody
    public Status couponList(HttpServletRequest request,
                             @PageableDefault(page = 0, size = 10, sort = "exchangedTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Member member = (Member) request.getAttribute("member");
        List<CouponJson> result = shopCouponMemberService.findByMember(member, pageable);
        return new Success<>(result);
    }

    // 兑换卡券
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Status exchangeCoupon(HttpServletRequest request, @Valid @RequestBody ExchangeCouponJson json,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOne(m.getId());
        if (!StringUtils.isEmpty(json.getPassword())) {
            if (!passwordEncoder.encodePassword(json.getPassword(), null).equals(member.getPassword()))
                throw new PasswordNotMatchException();
        }
        ShopCoupon coupon = shopCouponService.findOne(json.getId());
        if (coupon == null)
            throw new NotFoundException();
        ShopCouponMessage message = new ShopCouponMessage(coupon, member, json.getTotal());

        validateCoupon(member, coupon, json.getTotal());

        final Inbox inbox = Inbox.create(system);
        inbox.send(ref, message);
        try {
            String code = (String) inbox.receive(Duration.create(2, TimeUnit.SECONDS));
            if (code.equals("success"))
                return new Success<String>("success");
            return new Fail(code);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return new Fail("system error");
    }

    private void validateCoupon(Member member, ShopCoupon coupon, int total) {
        if (member == null)
            throw new MemberNotFoundException();
        if (member.getValid().equals(ValidEnum.INVALID))
            throw new NotValidException();
        if (coupon.getScore() * total > member.getScore())
            throw new NotEnoughScoreException();
        if (!couponService.isWithinValidDate(coupon))
            throw new NotValidException();
    }
}
