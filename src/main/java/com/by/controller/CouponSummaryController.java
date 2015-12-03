package com.by.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.by.exception.Status;
import com.by.json.ExchangeCouponJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.by.SpringExtension.SpringExtProvider;

@Controller
@RequestMapping(value = "/api/coupons")
public class CouponSummaryController {
    private ApplicationContext ctx;

    private ActorSystem system;
    private ActorRef ref;

    @Autowired
    public CouponSummaryController(ApplicationContext ctx) {
        this.ctx = ctx;
        system = ctx.getBean(ActorSystem.class);
        ref = system.actorOf(SpringExtProvider.get(system).props("PreferentialCouponActor"), "couponActor");

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
        /*
        if (result.hasErrors()) {
            FailBuilder.buildFail(result);
        }
        Member member = (Member) request.getAttribute("member");
        if (!StringUtils.isEmpty(json.getPassword()))
            member.setPassword(json.getPassword());
        CouponSummary summary = service.findOne(json.getId());
        PreferentialCouponMessage message = new PreferentialCouponMessage(member, summary);
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
        */
        //todo
        return null;
    }
}
