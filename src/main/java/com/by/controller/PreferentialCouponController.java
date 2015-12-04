package com.by.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.by.exception.Fail;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.ExchangeCouponJson;
import com.by.message.PreferentialCouponMessage;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.service.MemberService;
import com.by.service.PreferentialCouponService;
import com.by.utils.FailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.by.SpringExtension.SpringExtProvider;

@Controller
@RequestMapping(value = "/api/preferentialCoupons")
public class PreferentialCouponController {
    private ApplicationContext ctx;
    private ActorSystem system;
    private ActorRef ref;
    private PreferentialCouponService preferentialCouponService;
    private MemberService memberService;

    @Autowired
    public PreferentialCouponController(ApplicationContext ctx, PreferentialCouponService couponService, MemberService memberService) {
        this.ctx = ctx;
        this.system = ctx.getBean(ActorSystem.class);
        this.ref = system.actorOf(SpringExtProvider.get(system).props("PreferentialCouponActor"), "couponActor");
        this.preferentialCouponService = couponService;
        this.memberService = memberService;
    }

    // 可以兑换的卡券列表
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Status list() {
        return null;
    }

    // 兑换卡券
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Status exchangeCoupon(HttpServletRequest request, @Valid @RequestBody ExchangeCouponJson json, BindingResult result) {
        if (result.hasErrors()) {
            FailBuilder.buildFail(result);
        }
        Member m = (Member) request.getAttribute("member");
        if (!StringUtils.isEmpty(json.getPassword()))
            m.setPassword(json.getPassword());
        Member member = memberService.findOne(m.getId());
        PreferentialCoupon coupon = preferentialCouponService.findOne(json.getId());
        PreferentialCouponMessage message = new PreferentialCouponMessage(coupon, member, json.getTotal());
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
}
