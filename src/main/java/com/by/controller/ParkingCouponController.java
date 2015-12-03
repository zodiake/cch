package com.by.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.by.exception.Status;
import com.by.message.ParkingCouponMessage;
import com.by.service.ParkingCouponService;
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

/**
 * Created by yagamai on 15-12-3.
 */
@Controller
@RequestMapping(value = "/api/parkingCoupons")
public class ParkingCouponController {
    private ParkingCouponService service;
    private ApplicationContext ctx;

    private ActorSystem system;
    private ActorRef ref;

    @Autowired
    public ParkingCouponController(ParkingCouponService service, ApplicationContext ctx) {
        this.service = service;
        this.ctx = ctx;
        system = ctx.getBean(ActorSystem.class);
        ref = system.actorOf(SpringExtProvider.get(system).props("CouponActor"), "parkingCouponActor");

    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Status exchangeParkingCoupon(HttpServletRequest request, @Valid @RequestBody ParkingCouponMessage message, BindingResult result) {
        return null;
    }
}
