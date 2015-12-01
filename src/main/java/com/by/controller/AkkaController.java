package com.by.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.by.form.ParkingCouponMemberJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.by.SpringExtension.SpringExtProvider;

@Controller
@RequestMapping("/akka")
public class AkkaController {
    @Autowired
    private ApplicationContext ctx;

    @RequestMapping(value = "/parkingCoupon", method = RequestMethod.POST)
    @ResponseBody
    public String parkingCouponExchange(@RequestBody ParkingCouponMemberJson json) {
        ParkingCouponMember pcm = new ParkingCouponMember();
        pcm.setMember(new Member(json.getMember()));
        pcm.setCoupon(new ParkingCoupon(json.getCoupon()));
        pcm.setTotal(1);
        ActorSystem system = ctx.getBean(ActorSystem.class);
        ActorRef counter = system.actorOf(
                SpringExtProvider.get(system).props("ParkingCouponActor"), "counter");
        final Inbox inbox = Inbox.create(system);
        inbox.send(counter, pcm);
        try {
            return (String) inbox.receive(Duration.create(1, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return "haha";
    }
}
